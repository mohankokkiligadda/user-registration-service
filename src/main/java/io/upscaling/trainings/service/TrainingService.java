package io.upscaling.trainings.service;

import io.upscaling.trainings.document.IdGenerator;
import io.upscaling.trainings.document.Training;
import io.upscaling.trainings.entity.MyTrainingCourseOutline;
import io.upscaling.trainings.exceptions.ObjectNotFoundException;
import io.upscaling.trainings.repository.TrainingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;


    @Autowired
    public TrainingService(TrainingRepository trainingRepository){
            this.trainingRepository = trainingRepository;
    }

    public List<Training> getAllTrainings() {

        return trainingRepository.findAll();
    }

    public void createTraining(Training training) {
            // Generate unique IDs for courseOutline objects
        List<MyTrainingCourseOutline> courseOutline = training.getCourseOutline();
        if (courseOutline != null) {
                courseOutline.forEach(outline -> outline.setId(IdGenerator.generateId()));
        }
        // Save the training to the database
        trainingRepository.save(training);
    }

    public List<Training> getFutureTrainings() {

        return trainingRepository.findByStartDateAfter(Instant.now());
    }


    public List<Training> getOngoingTrainings() {
        return trainingRepository
                .findByStartDateBetweenAndEndDateAfter(
                            Instant.now().minus(7, ChronoUnit.DAYS),
                            Instant.now(),
                            Instant.now());
    }


    public Training partialUpdate(String trainingId, Training training) {
        Optional<Training> existingTraining = trainingRepository.findById(trainingId);
        if (existingTraining.isPresent()) {
            Training dbRecord = existingTraining.get();
            if (StringUtils.isNotEmpty(training.getName())) {
                dbRecord.setName(training.getName());
            }
            //TODO: set fields like above for remaining fields
            if (CollectionUtils.isNotEmpty(training.getCourseOutline())) {
                dbRecord.setCourseOutline(training.getCourseOutline());
            }
            return trainingRepository.save(dbRecord);
        } else {
            throw new ObjectNotFoundException("There is not record with the given trainingId: " + trainingId);
        }

    }



    public Training fullUpdate(String trainingId, Training updatedTraining) {
        Optional<Training> existingTraining = trainingRepository.findById(trainingId);
        if (existingTraining.isPresent()) {
            Training dbRecord = existingTraining.get();

            // Update the id field
            dbRecord.setId(updatedTraining.getId());

            // Update the course outlines
            List<MyTrainingCourseOutline> existingOutlines = dbRecord.getCourseOutline();
            List<MyTrainingCourseOutline> updatedOutlines = updatedTraining.getCourseOutline();

            // If updatedOutlines is not null, iterate through each updated outline
            if (updatedOutlines != null) {
                for (MyTrainingCourseOutline updatedOutline : updatedOutlines) {
                    // If the outline has no ID, generate a new one
                    if (updatedOutline.getId() == null) {
                        updatedOutline.setId(IdGenerator.generateId());
                    }

                    boolean found = false;

                    // Iterate through existing outlines to find a matching topicName
                    for (MyTrainingCourseOutline existingOutline : existingOutlines) {
                        if (existingOutline.getTopicName().equals(updatedOutline.getTopicName())) {
                            // Update the existing course outline
                            existingOutline.setDetails(updatedOutline.getDetails());
                            found = true;
                            break;
                        }
                    }

                    // If no matching topicName is found, add the new course outline
                    if (!found) {
                        existingOutlines.add(updatedOutline);
                    }
                }
            }

            // Save the updated Training entity
            return trainingRepository.save(dbRecord);
        } else {
            throw new ObjectNotFoundException("There is no record with the given trainingId: " + trainingId);
        }
    }





    /*----------------------------------------------------*/

    public Training updateCourseOutlineVideoUrl(String trainingId, MyTrainingCourseOutline updatedOutline) {
        Optional<Training> optionalTraining = trainingRepository.findById(trainingId);
        if (optionalTraining.isEmpty()) {
            throw new ObjectNotFoundException("Training with ID " + trainingId + " not found.");
        }

        Training training = optionalTraining.get();
        List<MyTrainingCourseOutline> existingCourseOutlines = training.getCourseOutline();
        if (existingCourseOutlines != null) {
            for (MyTrainingCourseOutline existingOutline : existingCourseOutlines) {
                if (existingOutline.getId().equals(updatedOutline.getId())) {
                    existingOutline.setVideoUrl(updatedOutline.getVideoUrl());
                }
            }
        }

        return trainingRepository.save(training);
    }


    public MyTrainingCourseOutline getOutlineById(String trainingId, String outlineId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ObjectNotFoundException("Training with ID " + trainingId + " not found."));

        List<MyTrainingCourseOutline> existingCourseOutlines = training.getCourseOutline();
        if (existingCourseOutlines != null) {
            for (MyTrainingCourseOutline existingOutline : existingCourseOutlines) {
                if (existingOutline.getId().equals(outlineId)) {
                    return existingOutline;
                }
            }
        }

        throw new ObjectNotFoundException("Course Outline with ID " + outlineId + " not found in Training with ID " + trainingId);
    }


}