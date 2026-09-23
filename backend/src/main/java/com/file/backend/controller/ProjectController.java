package com.file.backend.controller;

import com.file.backend.dto.ProjectResponse;
import com.file.backend.entity.Project;
import com.file.backend.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestParam Long userId,
            @RequestParam String projectName,
            @RequestParam String roomType) {

        Project project = projectService.createProject(
                userId,
                projectName,
                roomType
        );

        ProjectResponse response = new ProjectResponse(
                project.getId(),
                project.getProjectName(),
                project.getRoomType(),
                project.getUser().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUser(
            @PathVariable Long userId) {

        List<Project> projects =
                projectService.getProjectsByUser(userId);

        List<ProjectResponse> responses = projects.stream()
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getProjectName(),
                        project.getRoomType(),
                        project.getUser().getId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}