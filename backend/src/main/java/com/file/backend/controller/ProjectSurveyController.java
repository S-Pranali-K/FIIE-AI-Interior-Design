package com.file.backend.controller;

import com.file.backend.dto.ProjectSurveyRequest;
import com.file.backend.entity.ProjectSurvey;
import com.file.backend.service.ProjectSurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectSurveyController {

    private final ProjectSurveyService projectSurveyService;

    public ProjectSurveyController(
            ProjectSurveyService projectSurveyService) {

        this.projectSurveyService = projectSurveyService;
    }

    @PostMapping("/{projectId}/survey")
    public ResponseEntity<ProjectSurvey> saveSurvey(
            @PathVariable Long projectId,
            @RequestBody ProjectSurveyRequest request) {

        ProjectSurvey survey =
                projectSurveyService.saveSurvey(
                        projectId,
                        request
                );

        return ResponseEntity.ok(survey);
    }

    @GetMapping("/{projectId}/survey")
    public ResponseEntity<ProjectSurvey> getSurvey(
            @PathVariable Long projectId) {

        ProjectSurvey survey =
                projectSurveyService.getSurvey(projectId);

        return ResponseEntity.ok(survey);
    }
}