package com.fiie.app;

import java.io.Serializable;

public class DesignDetailData implements Serializable {

    private String selectedDesign;
    private String whySuitable;
    private String spaceUtilization;
    private String furniturePlacement;
    private String lighting;
    private String materials;
    private String functionalScore;

    public DesignDetailData() {
    }

    public DesignDetailData(
            String selectedDesign,
            String whySuitable,
            String spaceUtilization,
            String furniturePlacement,
            String lighting,
            String materials,
            String functionalScore) {

        this.selectedDesign = selectedDesign;
        this.whySuitable = whySuitable;
        this.spaceUtilization = spaceUtilization;
        this.furniturePlacement = furniturePlacement;
        this.lighting = lighting;
        this.materials = materials;
        this.functionalScore = functionalScore;
    }

    public String getSelectedDesign() {
        return selectedDesign;
    }

    public void setSelectedDesign(String selectedDesign) {
        this.selectedDesign = selectedDesign;
    }

    public String getWhySuitable() {
        return whySuitable;
    }

    public void setWhySuitable(String whySuitable) {
        this.whySuitable = whySuitable;
    }

    public String getSpaceUtilization() {
        return spaceUtilization;
    }

    public void setSpaceUtilization(String spaceUtilization) {
        this.spaceUtilization = spaceUtilization;
    }

    public String getFurniturePlacement() {
        return furniturePlacement;
    }

    public void setFurniturePlacement(String furniturePlacement) {
        this.furniturePlacement = furniturePlacement;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getFunctionalScore() {
        return functionalScore;
    }

    public void setFunctionalScore(String functionalScore) {
        this.functionalScore = functionalScore;
    }
}