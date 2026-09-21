package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DesignDetailsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private TextView tvSelectedDesign;
    private TextView tvSelectedDesignStatus;

    private TextView tvWhyDesign;
    private TextView tvSpaceUtilization;
    private TextView tvFurniturePlacement;
    private TextView tvLighting;
    private TextView tvMaterials;

    private TextView tvFunctionalScore;
    private TextView tvFunctionalScoreStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design_details);

        initializeViews();
        setupBackButton();
        loadDesignData();
    }

    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initializeViews() {

        btnBack = findViewById(R.id.btnBack);

        tvSelectedDesign =
                findViewById(R.id.tvSelectedDesign);

        tvSelectedDesignStatus =
                findViewById(R.id.tvSelectedDesignStatus);

        tvWhyDesign =
                findViewById(R.id.tvWhyDesign);

        tvSpaceUtilization =
                findViewById(R.id.tvSpaceUtilization);

        tvFurniturePlacement =
                findViewById(R.id.tvFurniturePlacement);

        tvLighting =
                findViewById(R.id.tvLighting);

        tvMaterials =
                findViewById(R.id.tvMaterials);

        tvFunctionalScore =
                findViewById(R.id.tvFunctionalScore);

        tvFunctionalScoreStatus =
                findViewById(R.id.tvFunctionalScoreStatus);
    }

    // =========================================================
    // BACK
    // =========================================================

    private void setupBackButton() {

        if (btnBack != null) {

            btnBack.setOnClickListener(v -> finish());
        }
    }

    // =========================================================
    // LOAD DYNAMIC DESIGN DATA
    // =========================================================

    private void loadDesignData() {

        Intent intent = getIntent();

        DesignDetailData designData =
                (DesignDetailData)
                        intent.getSerializableExtra("DESIGN_DETAILS");

        if (designData == null) {

            showEmptyState();

            return;
        }

        displayDesignData(designData);
    }

    // =========================================================
    // DISPLAY DATA
    // =========================================================

    private void displayDesignData(
            DesignDetailData data) {

        setTextIfAvailable(
                tvSelectedDesign,
                data.getSelectedDesign(),
                "Design information unavailable"
        );

        setTextIfAvailable(
                tvSelectedDesignStatus,
                "Selected design details"
        );

        setTextIfAvailable(
                tvWhyDesign,
                data.getWhySuitable(),
                "No explanation available yet."
        );

        setTextIfAvailable(
                tvSpaceUtilization,
                data.getSpaceUtilization(),
                "Space utilization information unavailable."
        );

        setTextIfAvailable(
                tvFurniturePlacement,
                data.getFurniturePlacement(),
                "Furniture placement information unavailable."
        );

        setTextIfAvailable(
                tvLighting,
                data.getLighting(),
                "Lighting information unavailable."
        );

        setTextIfAvailable(
                tvMaterials,
                data.getMaterials(),
                "Material information unavailable."
        );

        setTextIfAvailable(
                tvFunctionalScore,
                data.getFunctionalScore(),
                "—"
        );

        setTextIfAvailable(
                tvFunctionalScoreStatus,
                "Functional score generated from project analysis."
        );
    }

    // =========================================================
    // EMPTY STATE
    // =========================================================

    private void showEmptyState() {

        setTextIfAvailable(
                tvSelectedDesign,
                "No design selected"
        );

        setTextIfAvailable(
                tvSelectedDesignStatus,
                "Select a design to view its details."
        );

        setTextIfAvailable(
                tvWhyDesign,
                "Design suitability information will appear here."
        );

        setTextIfAvailable(
                tvSpaceUtilization,
                "Space utilization information will appear here."
        );

        setTextIfAvailable(
                tvFurniturePlacement,
                "Furniture placement information will appear here."
        );

        setTextIfAvailable(
                tvLighting,
                "Lighting information will appear here."
        );

        setTextIfAvailable(
                tvMaterials,
                "Material information will appear here."
        );

        setTextIfAvailable(
                tvFunctionalScore,
                "—"
        );

        setTextIfAvailable(
                tvFunctionalScoreStatus,
                "No functional analysis is available."
        );
    }

    // =========================================================
    // SAFE TEXT UPDATE
    // =========================================================

    private void setTextIfAvailable(
            TextView view,
            String value) {

        if (view == null) {
            return;
        }

        if (value == null || value.trim().isEmpty()) {
            view.setText("—");
        } else {
            view.setText(value);
        }
    }

    private void setTextIfAvailable(
            TextView view,
            String value,
            String fallback) {

        if (view == null) {
            return;
        }

        if (value == null || value.trim().isEmpty()) {
            view.setText(fallback);
        } else {
            view.setText(value);
        }
    }
}