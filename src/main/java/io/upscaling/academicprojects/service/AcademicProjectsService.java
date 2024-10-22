package io.upscaling.academicprojects.service;

import io.upscaling.academicprojects.exceptions.ObjectNotFoundException;
import io.upscaling.academicprojects.model.AcademicProjects;
import io.upscaling.academicprojects.model.CourseOutline;
import io.upscaling.academicprojects.repository.AcademicProjectsRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicProjectsService {

    private final AcademicProjectsRepository academicProjectsRepository;

    @Autowired
    public AcademicProjectsService(AcademicProjectsRepository academicProjectsRepository) {
        this.academicProjectsRepository = academicProjectsRepository;
    }

    public List<AcademicProjects> getAllAcademicProjects() {
        return academicProjectsRepository.findAll();
    }

    public void createAcademicProject(AcademicProjects academicProject) {
        academicProjectsRepository.save(academicProject);
    }

    public AcademicProjects partialUpdate(String academicProjectId, AcademicProjects academicProject) {
        Optional<AcademicProjects> existingAcademicProject = academicProjectsRepository.findById(academicProjectId);
        if (existingAcademicProject.isPresent()) {
            AcademicProjects dbRecord = existingAcademicProject.get();
            if (StringUtils.isNotEmpty(academicProject.getName())) {
                dbRecord.setName(academicProject.getName());
            }
            // TODO: set fields like above for remaining fields
            if (CollectionUtils.isNotEmpty(academicProject.getCourseOutline())) {
                dbRecord.setCourseOutline(academicProject.getCourseOutline());
            }
            return academicProjectsRepository.save(dbRecord);
        } else {
            throw new ObjectNotFoundException("There is no record with the given academicProjectId: " + academicProjectId);
        }
    }

    public AcademicProjects fullUpdate(String academicProjectId, AcademicProjects updatedAcademicProject) {
        Optional<AcademicProjects> existingAcademicProject = academicProjectsRepository.findById(academicProjectId);
        if (existingAcademicProject.isPresent()) {
            AcademicProjects dbRecord = existingAcademicProject.get();
            dbRecord.setName(updatedAcademicProject.getName());

            // Update the course outlines
            List<CourseOutline> existingOutlines = dbRecord.getCourseOutline();
            List<CourseOutline> updatedOutlines = updatedAcademicProject.getCourseOutline();
            if (updatedOutlines != null) {
                for (CourseOutline updatedOutline : updatedOutlines) {
                    boolean found = false;
                    for (CourseOutline existingOutline : existingOutlines) {
                        if (existingOutline.getTopicName().equals(updatedOutline.getTopicName())) {
                            // Update the existing course outline
                            existingOutline.setDetailedDescription(updatedOutline.getDetailedDescription());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // Add the new course outline
                        existingOutlines.add(updatedOutline);
                    }
                }
            }

            return academicProjectsRepository.save(dbRecord);
        } else {
            throw new ObjectNotFoundException("There is no record with the given academicProjectId: " + academicProjectId);
        }
    }
}
