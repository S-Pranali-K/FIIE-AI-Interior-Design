package com.file.backend.dto;

public class ProjectResponse {

    private Long id;
    private String projectName;
    private String roomType;
    private Long userId;

    public ProjectResponse() {
    }

    public ProjectResponse(
            Long id,
            String projectName,
            String roomType,
            Long userId) {

        this.id = id;
        this.projectName = projectName;
        this.roomType = roomType;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRoomType() {
        return roomType;
    }

    public Long getUserId() {
        return userId;
    }
}