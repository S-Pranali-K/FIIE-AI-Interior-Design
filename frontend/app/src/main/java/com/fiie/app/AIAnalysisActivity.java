package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AIAnalysisActivity extends AppCompatActivity {

    private ProgressBar progressAnalysis;
    private TextView tvProgress;
    private TextView tvAnalysisMessage;

    private int progress = 0;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private long userId;

    private String roomImageUri;

    private String roomType;
    private String roomLength;
    private String roomWidth;
    private String ceilingHeight;
    private String doors;
    private String windows;

    private boolean hasBed;
    private boolean hasWardrobe;
    private boolean hasStudyTable;
    private boolean hasChair;
    private boolean hasSofa;
    private boolean hasTvUnit;
    private boolean hasOtherFurniture;

    private String furnitureAction;

    private String style;
    private String color;
    private String material;
    private String lighting;
    private String specialRequirement;

    private boolean vastuEnabled;
    private String doorDirection;
    private String bedDirection;
    private String kitchenDirection;
    private String poojaDirection;

    private String budget;
    private String budgetPriority;

    private String completion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ai_analysis);

        progressAnalysis =
                findViewById(R.id.progressAnalysis);

        tvProgress =
                findViewById(R.id.tvProgress);

        tvAnalysisMessage =
                findViewById(R.id.tvAnalysisMessage);

        receiveProjectData();

        startAnalysis();
    }

    private void receiveProjectData() {

        Intent intent = getIntent();

        userId =
                intent.getLongExtra(
                        "USER_ID",
                        -1
                );

        roomImageUri =
                intent.getStringExtra(
                        "room_image_uri"
                );

        roomType =
                intent.getStringExtra(
                        "room_type"
                );

        roomLength =
                intent.getStringExtra(
                        "room_length"
                );

        roomWidth =
                intent.getStringExtra(
                        "room_width"
                );

        ceilingHeight =
                intent.getStringExtra(
                        "ceiling_height"
                );

        doors =
                intent.getStringExtra(
                        "doors"
                );

        windows =
                intent.getStringExtra(
                        "windows"
                );

        hasBed =
                intent.getBooleanExtra(
                        "has_bed",
                        false
                );

        hasWardrobe =
                intent.getBooleanExtra(
                        "has_wardrobe",
                        false
                );

        hasStudyTable =
                intent.getBooleanExtra(
                        "has_study_table",
                        false
                );

        hasChair =
                intent.getBooleanExtra(
                        "has_chair",
                        false
                );

        hasSofa =
                intent.getBooleanExtra(
                        "has_sofa",
                        false
                );

        hasTvUnit =
                intent.getBooleanExtra(
                        "has_tv_unit",
                        false
                );

        hasOtherFurniture =
                intent.getBooleanExtra(
                        "has_other_furniture",
                        false
                );

        furnitureAction =
                intent.getStringExtra(
                        "furniture_action"
                );

        style =
                intent.getStringExtra(
                        "style"
                );

        color =
                intent.getStringExtra(
                        "color"
                );

        material =
                intent.getStringExtra(
                        "material"
                );

        lighting =
                intent.getStringExtra(
                        "lighting"
                );

        specialRequirement =
                intent.getStringExtra(
                        "special_requirement"
                );

        vastuEnabled =
                intent.getBooleanExtra(
                        "vastu_enabled",
                        false
                );

        doorDirection =
                intent.getStringExtra(
                        "door_direction"
                );

        bedDirection =
                intent.getStringExtra(
                        "bed_direction"
                );

        kitchenDirection =
                intent.getStringExtra(
                        "kitchen_direction"
                );

        poojaDirection =
                intent.getStringExtra(
                        "pooja_direction"
                );

        budget =
                intent.getStringExtra(
                        "budget"
                );

        budgetPriority =
                intent.getStringExtra(
                        "budget_priority"
                );

        completion =
                intent.getStringExtra(
                        "completion"
                );
    }

    private void startAnalysis() {

        progress = 0;

        progressAnalysis.setProgress(0);

        tvProgress.setText("0%");

        tvAnalysisMessage.setText(
                "Starting FIIE analysis..."
        );

        handler.postDelayed(
                new Runnable() {

                    @Override
                    public void run() {

                        progress += 5;

                        progressAnalysis.setProgress(
                                progress
                        );

                        tvProgress.setText(
                                progress + "%"
                        );

                        if (progress < 25) {

                            tvAnalysisMessage.setText(
                                    "Processing room image..."
                            );

                        } else if (progress < 45) {

                            tvAnalysisMessage.setText(
                                    "Understanding room and existing furniture..."
                            );

                        } else if (progress < 65) {

                            tvAnalysisMessage.setText(
                                    "Evaluating space utilization and movement..."
                            );

                        } else if (progress < 80) {

                            tvAnalysisMessage.setText(
                                    "Analyzing preferences, Vastu and budget..."
                            );

                        } else if (progress < 100) {

                            tvAnalysisMessage.setText(
                                    "Preparing personalized design recommendations..."
                            );

                        } else {

                            tvAnalysisMessage.setText(
                                    "Analysis completed!"
                            );

                            saveProjectLocally();

                            handler.postDelayed(
                                    () -> openDesignResults(),
                                    800
                            );

                            return;
                        }

                        handler.postDelayed(
                                this,
                                150
                        );
                    }
                },
                300
        );
    }

    private void saveProjectLocally() {

        try {

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

            JSONArray projects;

            if (projectsJson.isEmpty()) {
                projects = new JSONArray();
            } else {
                projects = new JSONArray(projectsJson);
            }

            long projectId =
                    System.currentTimeMillis();

            JSONObject project =
                    new JSONObject();

            project.put(
                    "project_id",
                    projectId
            );

            project.put(
                    "user_id",
                    userId == -1 ? 1 : userId
            );

            project.put(
                    "project_name",
                    createProjectName()
            );

            project.put(
                    "date",
                    new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a",
                            Locale.getDefault()
                    ).format(
                            new Date()
                    )
            );

            project.put(
                    "room_image_uri",
                    safeValue(roomImageUri)
            );

            project.put(
                    "room_type",
                    safeValue(roomType)
            );

            project.put(
                    "room_length",
                    safeValue(roomLength)
            );

            project.put(
                    "room_width",
                    safeValue(roomWidth)
            );

            project.put(
                    "ceiling_height",
                    safeValue(ceilingHeight)
            );

            project.put(
                    "doors",
                    safeValue(doors)
            );

            project.put(
                    "windows",
                    safeValue(windows)
            );

            project.put(
                    "has_bed",
                    hasBed
            );

            project.put(
                    "has_wardrobe",
                    hasWardrobe
            );

            project.put(
                    "has_study_table",
                    hasStudyTable
            );

            project.put(
                    "has_chair",
                    hasChair
            );

            project.put(
                    "has_sofa",
                    hasSofa
            );

            project.put(
                    "has_tv_unit",
                    hasTvUnit
            );

            project.put(
                    "has_other_furniture",
                    hasOtherFurniture
            );

            project.put(
                    "furniture_action",
                    safeValue(furnitureAction)
            );

            project.put(
                    "style",
                    safeValue(style)
            );

            project.put(
                    "color",
                    safeValue(color)
            );

            project.put(
                    "material",
                    safeValue(material)
            );

            project.put(
                    "lighting",
                    safeValue(lighting)
            );

            project.put(
                    "special_requirement",
                    safeValue(specialRequirement)
            );

            project.put(
                    "vastu_enabled",
                    vastuEnabled
            );

            project.put(
                    "door_direction",
                    safeValue(doorDirection)
            );

            project.put(
                    "bed_direction",
                    safeValue(bedDirection)
            );

            project.put(
                    "kitchen_direction",
                    safeValue(kitchenDirection)
            );

            project.put(
                    "pooja_direction",
                    safeValue(poojaDirection)
            );

            project.put(
                    "budget",
                    safeValue(budget)
            );

            project.put(
                    "budget_priority",
                    safeValue(budgetPriority)
            );

            project.put(
                    "completion",
                    safeValue(completion)
            );

            projects.put(project);

            preferences.edit()
                    .putString(
                            "FIIE_PROJECTS",
                            projects.toString()
                    )
                    .apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createProjectName() {

        String room =
                safeValue(roomType);

        String designStyle =
                safeValue(style);

        if (!room.isEmpty() &&
                !designStyle.isEmpty()) {

            return room + " - " + designStyle;
        }

        if (!room.isEmpty()) {

            return room + " Design";
        }

        if (!designStyle.isEmpty()) {

            return designStyle + " Design";
        }

        return "My Interior Design";
    }

    private String safeValue(String value) {

        if (value == null) {
            return "";
        }

        return value;
    }

    private void openDesignResults() {

        Intent intent =
                new Intent(
                        AIAnalysisActivity.this,
                        DesignResultsActivity.class
                );

        if (userId != -1) {

            intent.putExtra(
                    "USER_ID",
                    userId
            );
        }

        intent.putExtra(
                "PROJECT_ID",
                System.currentTimeMillis()
        );

        intent.putExtra(
                "room_image_uri",
                roomImageUri
        );

        intent.putExtra(
                "room_type",
                roomType
        );

        intent.putExtra(
                "room_length",
                roomLength
        );

        intent.putExtra(
                "room_width",
                roomWidth
        );

        intent.putExtra(
                "ceiling_height",
                ceilingHeight
        );

        intent.putExtra(
                "doors",
                doors
        );

        intent.putExtra(
                "windows",
                windows
        );

        intent.putExtra(
                "has_bed",
                hasBed
        );

        intent.putExtra(
                "has_wardrobe",
                hasWardrobe
        );

        intent.putExtra(
                "has_study_table",
                hasStudyTable
        );

        intent.putExtra(
                "has_chair",
                hasChair
        );

        intent.putExtra(
                "has_sofa",
                hasSofa
        );

        intent.putExtra(
                "has_tv_unit",
                hasTvUnit
        );

        intent.putExtra(
                "has_other_furniture",
                hasOtherFurniture
        );

        intent.putExtra(
                "furniture_action",
                furnitureAction
        );

        intent.putExtra(
                "style",
                style
        );

        intent.putExtra(
                "color",
                color
        );

        intent.putExtra(
                "material",
                material
        );

        intent.putExtra(
                "lighting",
                lighting
        );

        intent.putExtra(
                "special_requirement",
                specialRequirement
        );

        intent.putExtra(
                "vastu_enabled",
                vastuEnabled
        );

        intent.putExtra(
                "door_direction",
                doorDirection
        );

        intent.putExtra(
                "bed_direction",
                bedDirection
        );

        intent.putExtra(
                "kitchen_direction",
                kitchenDirection
        );

        intent.putExtra(
                "pooja_direction",
                poojaDirection
        );

        intent.putExtra(
                "budget",
                budget
        );

        intent.putExtra(
                "budget_priority",
                budgetPriority
        );

        intent.putExtra(
                "completion",
                completion
        );

        startActivity(intent);

        finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}