package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecommendationsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private LinearLayout recommendationsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recommendations);

        initializeViews();
        setupBackButton();
        loadRecommendations();
    }

    // =========================================================
    // INITIALIZE
    // =========================================================

    private void initializeViews() {

        btnBack = findViewById(R.id.btnBack);

        recommendationsContainer =
                findViewById(R.id.recommendationsContainer);
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
    // LOAD DYNAMIC DATA
    // =========================================================

    private void loadRecommendations() {

        Intent intent = getIntent();

        ArrayList<RecommendationData> recommendations =
                (ArrayList<RecommendationData>)
                        intent.getSerializableExtra(
                                "RECOMMENDATIONS"
                        );

        if (recommendations == null ||
                recommendations.isEmpty()) {

            showEmptyState();

            return;
        }

        displayRecommendations(recommendations);
    }

    // =========================================================
    // DISPLAY DYNAMIC RECOMMENDATIONS
    // =========================================================

    private void displayRecommendations(
            ArrayList<RecommendationData> recommendations) {

        recommendationsContainer.removeAllViews();

        for (RecommendationData recommendation :
                recommendations) {

            addRecommendationCard(recommendation);
        }
    }

    // =========================================================
    // CREATE CARD DYNAMICALLY
    // =========================================================

    private void addRecommendationCard(
            RecommendationData data) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        card.setBackgroundResource(
                R.drawable.glass_dashboard_card
        );

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(
                0,
                dp(10),
                0,
                dp(8)
        );

        card.setLayoutParams(cardParams);

        // -----------------------------------------------------
        // CATEGORY
        // -----------------------------------------------------

        TextView category =
                createTextView(
                        data.getCategory(),
                        10,
                        true
                );

        category.setTextColor(
                getColor(R.color.fiie_primary)
        );

        category.setLetterSpacing(0.12f);

        card.addView(category);

        // -----------------------------------------------------
        // RECOMMENDATION
        // -----------------------------------------------------

        TextView recommendation =
                createTextView(
                        data.getRecommendation(),
                        19,
                        true
                );

        recommendation.setTextColor(
                getColor(R.color.text_primary_light)
        );

        LinearLayout.LayoutParams recommendationParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        recommendationParams.setMargins(
                0,
                dp(8),
                0,
                0
        );

        recommendation.setLayoutParams(
                recommendationParams
        );

        card.addView(recommendation);

        // -----------------------------------------------------
        // WHY
        // -----------------------------------------------------

        TextView whyLabel =
                createTextView(
                        "WHY",
                        10,
                        true
                );

        whyLabel.setTextColor(
                getColor(R.color.fiie_primary)
        );

        LinearLayout.LayoutParams whyLabelParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        whyLabelParams.setMargins(
                0,
                dp(16),
                0,
                0
        );

        whyLabel.setLayoutParams(
                whyLabelParams
        );

        card.addView(whyLabel);

        TextView why =
                createTextView(
                        data.getWhy(),
                        14,
                        false
                );

        why.setTextColor(
                getColor(R.color.text_secondary_light)
        );

        why.setLineSpacing(
                dp(1),
                1.05f
        );

        LinearLayout.LayoutParams whyParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        whyParams.setMargins(
                0,
                dp(5),
                0,
                0
        );

        why.setLayoutParams(whyParams);

        card.addView(why);

        recommendationsContainer.addView(card);
    }

    // =========================================================
    // EMPTY STATE
    // =========================================================

    private void showEmptyState() {

        recommendationsContainer.removeAllViews();

        TextView emptyState =
                createTextView(
                        "Recommendations will appear here once FIIE has analyzed the project.",
                        14,
                        false
                );

        emptyState.setTextColor(
                getColor(R.color.text_secondary_light)
        );

        emptyState.setGravity(Gravity.CENTER);

        emptyState.setPadding(
                dp(20),
                dp(40),
                dp(20),
                dp(40)
        );

        recommendationsContainer.addView(
                emptyState
        );
    }

    // =========================================================
    // TEXT VIEW HELPER
    // =========================================================

    private TextView createTextView(
            String text,
            int textSize,
            boolean bold) {

        TextView textView =
                new TextView(this);

        textView.setText(
                text == null || text.trim().isEmpty()
                        ? "—"
                        : text
        );

        textView.setTextSize(textSize);

        textView.setFontFeatureSettings(
                "kern"
        );

        if (bold) {
            textView.setTypeface(
                    textView.getTypeface(),
                    android.graphics.Typeface.BOLD
            );
        }

        return textView;
    }

    // =========================================================
    // DP HELPER
    // =========================================================

    private int dp(int value) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
        );
    }
}