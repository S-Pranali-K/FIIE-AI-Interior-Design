package com.file.backend.repository;

import com.file.backend.entity.ProjectSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectSurveyRepository
        extends JpaRepository<ProjectSurvey, Long> {

    Optional<ProjectSurvey> findByProjectId(Long projectId);
}