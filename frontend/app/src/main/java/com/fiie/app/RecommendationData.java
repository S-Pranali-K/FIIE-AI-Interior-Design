package com.fiie.app;

import java.io.Serializable;

public class RecommendationData implements Serializable {

    private String category;
    private String recommendation;
    private String why;

    public RecommendationData() {
    }

    public RecommendationData(
            String category,
            String recommendation,
            String why) {

        this.category = category;
        this.recommendation = recommendation;
        this.why = why;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }
}