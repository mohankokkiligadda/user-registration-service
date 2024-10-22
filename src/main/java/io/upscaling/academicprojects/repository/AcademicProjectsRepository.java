package io.upscaling.academicprojects.repository;

import io.upscaling.academicprojects.model.AcademicProjects;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AcademicProjectsRepository extends MongoRepository<AcademicProjects, String> {

}
