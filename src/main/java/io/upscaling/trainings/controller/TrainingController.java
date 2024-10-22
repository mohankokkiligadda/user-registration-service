package io.upscaling.trainings.controller;

import io.upscaling.academicprojects.exceptions.ObjectNotFoundException;
import io.upscaling.trainings.document.Training;
import io.upscaling.trainings.entity.MyTrainingCourseOutline;
import io.upscaling.trainings.model.TrainingResponse;
import io.upscaling.trainings.repository.TrainingRepository;
import io.upscaling.trainings.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingController(TrainingService trainingService, TrainingRepository trainingRepository){

        this.trainingService = trainingService;
        this.trainingRepository = trainingRepository;
    }


    @GetMapping("/trainings")
    public ResponseEntity<TrainingResponse> getTrainings() {

        TrainingResponse trainingResponse = new TrainingResponse();
        trainingResponse.setFutureTrainings(trainingService.getFutureTrainings());
        trainingResponse.setOngoingTrainings(trainingService.getOngoingTrainings());

        return ResponseEntity.ok(trainingResponse);
    }



    @PostMapping("/trainings")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        trainingService.createTraining(training);
        return ResponseEntity.ok(training);
    }


    @PatchMapping("/trainings/{trainingId}")
    public ResponseEntity<?> partialUpdateTraining(@PathVariable String trainingId, @RequestBody Training training) {
        try {
            Training trainingResult = trainingService.partialUpdate(trainingId, training);
            return ResponseEntity.ok(trainingResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/trainings/{trainingId}")
    public ResponseEntity<Training> completeUpdateTraining(@PathVariable String trainingId, @RequestBody Training updatedTraining) {
        try {
            Training updatedTrainingResult = trainingService.fullUpdate(trainingId, updatedTraining);
            return ResponseEntity.ok(updatedTrainingResult);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isValidTrainingId(String trainingId) {
        // Implement your validation logic here, for example, checking for the correct format of trainingId
        // You can use regular expressions or any other validation method as per your requirement.
        return true; // Return true if the trainingId is valid, false otherwise.
    }
    @PatchMapping("/trainings/{trainingId}/courseOutlines/{courseOutlineId}")
    public ResponseEntity<Training> updateCourseOutlineVideoUrl(
            @PathVariable String trainingId,
            @PathVariable String courseOutlineId,
            @RequestBody MyTrainingCourseOutline updatedOutline) {

        if (!isValidTrainingId(trainingId)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Training> optionalTraining = trainingRepository.findById(trainingId);
        if (optionalTraining.isEmpty()) {
            throw new ObjectNotFoundException("Training with ID " + trainingId + " not found.");
        }

        Training training = optionalTraining.get();
        List<MyTrainingCourseOutline> existingCourseOutlines = training.getCourseOutline();
        if (existingCourseOutlines != null) {
            for (MyTrainingCourseOutline existingOutline : existingCourseOutlines) {
                if (existingOutline.getId().equals(courseOutlineId)) {
                    existingOutline.setVideoUrl(updatedOutline.getVideoUrl());
                }
            }
        }

        Training updatedTraining = trainingRepository.save(training);
        return ResponseEntity.ok(updatedTraining);
    }


    @GetMapping("/trainings/{trainingId}/courseOutlines/{outlineId}")
    public ResponseEntity<MyTrainingCourseOutline> getCourseOutlineById(
            @PathVariable String trainingId,
            @PathVariable String outlineId) {
        try {
            MyTrainingCourseOutline outline = trainingService.getOutlineById(trainingId, outlineId);
            return ResponseEntity.ok(outline);
        } catch (ObjectNotFoundException e  ) {
            return ResponseEntity.notFound().build();
        }
    }



}