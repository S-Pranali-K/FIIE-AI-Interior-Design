package com.file.backend.service;

import com.file.backend.dto.ProjectSurveyRequest;
import com.file.backend.entity.Project;
import com.file.backend.entity.ProjectSurvey;
import com.file.backend.repository.ProjectRepository;
import com.file.backend.repository.ProjectSurveyRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectSurveyService {

    private final ProjectRepository projectRepository;
    private final ProjectSurveyRepository projectSurveyRepository;

    public ProjectSurveyService(
            ProjectRepository projectRepository,
            ProjectSurveyRepository projectSurveyRepository) {

        this.projectRepository = projectRepository;
        this.projectSurveyRepository = projectSurveyRepository;
    }

    public ProjectSurvey saveSurvey(
            Long projectId,
            ProjectSurveyRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        ProjectSurvey survey =
                projectSurveyRepository
                        .findByProjectId(projectId)
                        .orElse(new ProjectSurvey());

        survey.setProject(project);

        survey.setRoomLength(request.getRoomLength());
        survey.setRoomWidth(request.getRoomWidth());
        survey.setCeilingHeight(request.getCeilingHeight());

        survey.setDoors(request.getDoors());
        survey.setWindows(request.getWindows());

        survey.setFurniture(request.getFurniture());
        survey.setFurnitureAction(request.getFurnitureAction());

        survey.setStyle(request.getStyle());
        survey.setColor(request.getColor());
        survey.setMaterial(request.getMaterial());
        survey.setLighting(request.getLighting());

        survey.setSpecialRequirement(
                request.getSpecialRequirement()
        );

        survey.setVastuEnabled(request.getVastuEnabled());

        survey.setDoorDirection(request.getDoorDirection());
        survey.setBedDirection(request.getBedDirection());
        survey.setKitchenDirection(
                request.getKitchenDirection()
        );
        survey.setPoojaDirection(
                request.getPoojaDirection()
        );

        survey.setBudget(request.getBudget());
        survey.setBudgetPriority(
                request.getBudgetPriority()
        );
        survey.setCompletion(request.getCompletion());

        return projectSurveyRepository.save(survey);
    }

    public ProjectSurvey getSurvey(Long projectId) {

        return projectSurveyRepository
                .findByProjectId(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Survey not found"));
    }
}