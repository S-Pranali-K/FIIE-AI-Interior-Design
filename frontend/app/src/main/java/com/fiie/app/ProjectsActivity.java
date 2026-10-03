package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProjectsActivity extends AppCompatActivity {

    private LinearLayout projectsContainer;
    private TextView tvEmptyProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_projects);

        projectsContainer = findViewById(R.id.projectsContainer);
        tvEmptyProjects = findViewById(R.id.tvEmptyProjects);

        loadProjects();
    }

    private void loadProjects() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "FIIE_PREFS",
                        MODE_PRIVATE
                );

        String projectsJson =
                preferences.getString(
                        "FIIE_PROJECTS",
                        ""
                );

        if (projectsJson.isEmpty()) {
            showEmptyState();
            return;
        }

        try {

            JSONArray projects = new JSONArray(projectsJson);

            if (projects.length() == 0) {
                showEmptyState();
                return;
            }

            tvEmptyProjects.setVisibility(View.GONE);

            for (int i = projects.length() - 1; i >= 0; i--) {

                JSONObject project = projects.getJSONObject(i);

                addProjectCard(project);
            }

        } catch (Exception e) {
            showEmptyState();
        }
    }

    private void showEmptyState() {

        tvEmptyProjects.setVisibility(View.VISIBLE);
    }

    private void addProjectCard(JSONObject project) {

        LinearLayout card = new LinearLayout(this);

        card.setOrientation(LinearLayout.VERTICAL);

        card.setPadding(
                22,
                20,
                22,
                20
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
                0,
                0,
                14
        );

        card.setLayoutParams(cardParams);

        TextView title = new TextView(this);

        title.setText(
                getProjectValue(
                        project,
                        "project_name",
                        "My Interior Design"
                )
        );

        title.setTextColor(
                android.graphics.Color.parseColor(
                        "#2D2621"
                )
        );

        title.setTextSize(19);

        title.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        card.addView(title);

        TextView room =
                createProjectText(
                        "Room: " +
                                getProjectValue(
                                        project,
                                        "room_type",
                                        "Room"
                                )
                );

        card.addView(room);

        TextView style =
                createProjectText(
                        "Style: " +
                                getProjectValue(
                                        project,
                                        "style",
                                        "Not specified"
                                )
                );

        card.addView(style);

        TextView color =
                createProjectText(
                        "Color: " +
                                getProjectValue(
                                        project,
                                        "color",
                                        "Not specified"
                                )
                );

        card.addView(color);

        TextView budget =
                createProjectText(
                        "Budget: " +
                                getProjectValue(
                                        project,
                                        "budget",
                                        "Not specified"
                                )
                );

        card.addView(budget);

        TextView date =
                createProjectText(
                        getProjectValue(
                                project,
                                "date",
                                ""
                        )
                );

        if (!date.getText().toString().isEmpty()) {
            card.addView(date);
        }

        TextView viewProject = new TextView(this);

        viewProject.setText(
                "View Project   →"
        );

        viewProject.setTextColor(
                android.graphics.Color.WHITE
        );

        viewProject.setTextSize(13);

        viewProject.setGravity(Gravity.CENTER);

        viewProject.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        viewProject.setPadding(
                12,
                8,
                12,
                8
        );

        viewProject.setBackgroundResource(
                R.drawable.glass_btn_10
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) (50 * getResources().getDisplayMetrics().density)
                );

        buttonParams.setMargins(
                0,
                14,
                0,
                0
        );

        viewProject.setLayoutParams(buttonParams);

        card.addView(viewProject);

        viewProject.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProjectsActivity.this,
                            DesignResultsActivity.class
                    );

            putProjectData(
                    intent,
                    project
            );

            startActivity(intent);
        });

        projectsContainer.addView(card);
    }

    private TextView createProjectText(String text) {

        TextView view = new TextView(this);

        view.setText(text);

        view.setTextColor(
                android.graphics.Color.parseColor(
                        "#675C54"
                )
        );

        view.setTextSize(13);

        view.setPadding(
                0,
                5,
                0,
                0
        );

        return view;
    }

    private String getProjectValue(
            JSONObject project,
            String key,
            String defaultValue
    ) {

        try {

            String value =
                    project.optString(
                            key,
                            ""
                    );

            if (value == null ||
                    value.trim().isEmpty()) {

                return defaultValue;
            }

            return value;

        } catch (Exception e) {

            return defaultValue;
        }
    }

    private void putProjectData(
            Intent intent,
            JSONObject project
    ) {

        intent.putExtra(
                "PROJECT_ID",
                project.optLong(
                        "project_id",
                        1
                )
        );

        intent.putExtra(
                "USER_ID",
                project.optLong(
                        "user_id",
                        1
                )
        );

        intent.putExtra(
                "room_image_uri",
                getProjectValue(
                        project,
                        "room_image_uri",
                        ""
                )
        );

        intent.putExtra(
                "room_type",
                getProjectValue(
                        project,
                        "room_type",
                        ""
                )
        );

        intent.putExtra(
                "room_length",
                getProjectValue(
                        project,
                        "room_length",
                        ""
                )
        );

        intent.putExtra(
                "room_width",
                getProjectValue(
                        project,
                        "room_width",
                        ""
                )
        );

        intent.putExtra(
                "ceiling_height",
                getProjectValue(
                        project,
                        "ceiling_height",
                        ""
                )
        );

        intent.putExtra(
                "style",
                getProjectValue(
                        project,
                        "style",
                        ""
                )
        );

        intent.putExtra(
                "color",
                getProjectValue(
                        project,
                        "color",
                        ""
                )
        );

        intent.putExtra(
                "material",
                getProjectValue(
                        project,
                        "material",
                        ""
                )
        );

        intent.putExtra(
                "lighting",
                getProjectValue(
                        project,
                        "lighting",
                        ""
                )
        );

        intent.putExtra(
                "special_requirement",
                getProjectValue(
                        project,
                        "special_requirement",
                        ""
                )
        );

        intent.putExtra(
                "budget",
                getProjectValue(
                        project,
                        "budget",
                        ""
                )
        );

        intent.putExtra(
                "budget_priority",
                getProjectValue(
                        project,
                        "budget_priority",
                        ""
                )
        );

        intent.putExtra(
                "completion",
                getProjectValue(
                        project,
                        "completion",
                        ""
                )
        );

        intent.putExtra(
                "furniture_action",
                getProjectValue(
                        project,
                        "furniture_action",
                        ""
                )
        );

        intent.putExtra(
                "vastu_enabled",
                project.optBoolean(
                        "vastu_enabled",
                        false
                )
        );

        intent.putExtra(
                "door_direction",
                getProjectValue(
                        project,
                        "door_direction",
                        ""
                )
        );

        intent.putExtra(
                "bed_direction",
                getProjectValue(
                        project,
                        "bed_direction",
                        ""
                )
        );

        intent.putExtra(
                "kitchen_direction",
                getProjectValue(
                        project,
                        "kitchen_direction",
                        ""
                )
        );

        intent.putExtra(
                "pooja_direction",
                getProjectValue(
                        project,
                        "pooja_direction",
                        ""
                )
        );
    }
}