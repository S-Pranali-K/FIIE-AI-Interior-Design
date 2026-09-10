package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AIAnalysisActivity extends AppCompatActivity {

    private ProgressBar progressAnalysis;
    private TextView tvProgress;
    private TextView tvAnalysisMessage;

    private int progress = 0;

    private final Handler handler =
            new Handler(Looper.getMainLooper());


    // =========================================
    // PROJECT DATA
    // =========================================

    private long userId;

    private String roomImageUri;

    // Room information
    private String roomType;
    private String roomLength;
    private String roomWidth;
    private String ceilingHeight;
    private String doors;
    private String windows;

    // Existing furniture
    private boolean hasBed;
    private boolean hasWardrobe;
    private boolean hasStudyTable;
    private boolean hasChair;
    private boolean hasSofa;
    private boolean hasTvUnit;
    private boolean hasOtherFurniture;

    private String furnitureAction;

    // Design preferences
    private String style;
    private String color;
    private String material;
    private String lighting;
    private String specialRequirement;

    // Vastu
    private boolean vastuEnabled;
    private String doorDirection;
    private String bedDirection;
    private String kitchenDirection;
    private String poojaDirection;

    // Budget
    private String budget;
    private String budgetPriority;

    // Completion
    private String completion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ai_analysis);


        // -----------------------------------------
        // Initialize Views
        // -----------------------------------------

        progressAnalysis =
                findViewById(R.id.progressAnalysis);

        tvProgress =
                findViewById(R.id.tvProgress);

        tvAnalysisMessage =
                findViewById(R.id.tvAnalysisMessage);


        // -----------------------------------------
        // Receive Complete Project Data
        // -----------------------------------------

        receiveProjectData();


        // -----------------------------------------
        // Start Analysis
        // -----------------------------------------

        startAnalysis();
    }


    // =========================================
    // RECEIVE PROJECT DATA
    // =========================================

    private void receiveProjectData() {

        Intent intent = getIntent();


        // -----------------------------------------
        // User
        // -----------------------------------------

        userId =
                intent.getLongExtra(
                        "USER_ID",
                        -1
                );


        // -----------------------------------------
        // Room Image
        // -----------------------------------------

        roomImageUri =
                intent.getStringExtra(
                        "room_image_uri"
                );


        // -----------------------------------------
        // Room Information
        // -----------------------------------------

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


        // -----------------------------------------
        // Existing Furniture
        // -----------------------------------------

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


        // -----------------------------------------
        // Design Preferences
        // -----------------------------------------

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


        // -----------------------------------------
        // Vastu Preferences
        // -----------------------------------------

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


        // -----------------------------------------
        // Budget
        // -----------------------------------------

        budget =
                intent.getStringExtra(
                        "budget"
                );

        budgetPriority =
                intent.getStringExtra(
                        "budget_priority"
                );


        // -----------------------------------------
        // Completion
        // -----------------------------------------

        completion =
                intent.getStringExtra(
                        "completion"
                );
    }


    // =========================================
    // AI ANALYSIS UI
    // =========================================

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


    // =========================================
    // OPEN DESIGN RESULTS
    // =========================================

    private void openDesignResults() {

        Intent intent =
                new Intent(
                        AIAnalysisActivity.this,
                        DesignResultsActivity.class
                );


        // -----------------------------------------
        // User
        // -----------------------------------------

        if (userId != -1) {

            intent.putExtra(
                    "USER_ID",
                    userId
            );
        }


        // -----------------------------------------
        // Room Image
        // -----------------------------------------

        intent.putExtra(
                "room_image_uri",
                roomImageUri
        );


        // -----------------------------------------
        // Room Information
        // -----------------------------------------

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


        // -----------------------------------------
        // Existing Furniture
        // -----------------------------------------

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


        // -----------------------------------------
        // Design Preferences
        // -----------------------------------------

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


        // -----------------------------------------
        // Vastu
        // -----------------------------------------

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


        // -----------------------------------------
        // Budget
        // -----------------------------------------

        intent.putExtra(
                "budget",
                budget
        );

        intent.putExtra(
                "budget_priority",
                budgetPriority
        );


        // -----------------------------------------
        // Completion
        // -----------------------------------------

        intent.putExtra(
                "completion",
                completion
        );


        // -----------------------------------------
        // Navigate
        // -----------------------------------------

        startActivity(intent);

        finish();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}