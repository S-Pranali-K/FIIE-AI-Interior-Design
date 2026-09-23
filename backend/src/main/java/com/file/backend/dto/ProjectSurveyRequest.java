package com.file.backend.dto;

public class ProjectSurveyRequest {

    private Double roomLength;
    private Double roomWidth;
    private Double ceilingHeight;

    private Integer doors;
    private Integer windows;

    private String furniture;
    private String furnitureAction;

    private String style;
    private String color;
    private String material;
    private String lighting;

    private String specialRequirement;

    private Boolean vastuEnabled;

    private String doorDirection;
    private String bedDirection;
    private String kitchenDirection;
    private String poojaDirection;

    private Double budget;
    private String budgetPriority;
    private String completion;

    public ProjectSurveyRequest() {
    }

    public Double getRoomLength() {
        return roomLength;
    }

    public void setRoomLength(Double roomLength) {
        this.roomLength = roomLength;
    }

    public Double getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(Double roomWidth) {
        this.roomWidth = roomWidth;
    }

    public Double getCeilingHeight() {
        return ceilingHeight;
    }

    public void setCeilingHeight(Double ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getWindows() {
        return windows;
    }

    public void setWindows(Integer windows) {
        this.windows = windows;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public String getFurnitureAction() {
        return furnitureAction;
    }

    public void setFurnitureAction(String furnitureAction) {
        this.furnitureAction = furnitureAction;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public Boolean getVastuEnabled() {
        return vastuEnabled;
    }

    public void setVastuEnabled(Boolean vastuEnabled) {
        this.vastuEnabled = vastuEnabled;
    }

    public String getDoorDirection() {
        return doorDirection;
    }

    public void setDoorDirection(String doorDirection) {
        this.doorDirection = doorDirection;
    }

    public String getBedDirection() {
        return bedDirection;
    }

    public void setBedDirection(String bedDirection) {
        this.bedDirection = bedDirection;
    }

    public String getKitchenDirection() {
        return kitchenDirection;
    }

    public void setKitchenDirection(String kitchenDirection) {
        this.kitchenDirection = kitchenDirection;
    }

    public String getPoojaDirection() {
        return poojaDirection;
    }

    public void setPoojaDirection(String poojaDirection) {
        this.poojaDirection = poojaDirection;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getBudgetPriority() {
        return budgetPriority;
    }

    public void setBudgetPriority(String budgetPriority) {
        this.budgetPriority = budgetPriority;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }
}