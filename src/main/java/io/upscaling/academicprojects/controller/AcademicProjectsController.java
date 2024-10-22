package io.upscaling.academicprojects.controller;

import io.upscaling.academicprojects.model.AcademicProjects;
import io.upscaling.academicprojects.service.AcademicProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/academic-projects")
public class AcademicProjectsController {

    private final AcademicProjectsService academicProjectsService;

    @Autowired
    public AcademicProjectsController(AcademicProjectsService academicProjectsService) {
        this.academicProjectsService = academicProjectsService;
    }

    @GetMapping
    public ResponseEntity<List<AcademicProjects>> getAllAcademicProjects() {
        List<AcademicProjects> academicProjects = academicProjectsService.getAllAcademicProjects();
        return ResponseEntity.ok(academicProjects);
    }

    @PostMapping
    public ResponseEntity<String> createAcademicProject(@RequestBody AcademicProjects academicProject) {
        academicProjectsService.createAcademicProject(academicProject);
        return ResponseEntity.status(HttpStatus.CREATED).body("Academic Project created successfully");
    }

    @PatchMapping("/{academicProjectId}")
    public ResponseEntity<AcademicProjects> partialUpdateAcademicProject(
            @PathVariable String academicProjectId,
            @RequestBody AcademicProjects academicProject
    ) {
        AcademicProjects updatedAcademicProject = academicProjectsService.partialUpdate(academicProjectId, academicProject);
        return ResponseEntity.ok(updatedAcademicProject);
    }

    @PutMapping("/{academicProjectId}")
    public ResponseEntity<AcademicProjects> completeUpdateAcademicProject(
            @PathVariable String academicProjectId,
            @RequestBody AcademicProjects academicProject
    ) {
        AcademicProjects updatedAcademicProject = academicProjectsService.fullUpdate(academicProjectId, academicProject);
        return ResponseEntity.ok(updatedAcademicProject);
    }
}
