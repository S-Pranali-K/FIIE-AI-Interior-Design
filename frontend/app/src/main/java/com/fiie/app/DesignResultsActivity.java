package com.fiie.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DesignResultsActivity extends AppCompatActivity {

    private ImageView ivDesignPreview;

    private TextView tvRoomSummary;
    private TextView tvStyleRecommendation;
    private TextView tvFunctionalRecommendation;
    private TextView tvVastuRecommendation;

    private Button btnViewDetails;
    private Button btnNewDesign;

    // Project data
    private String roomImageUri;
    private String roomType;
    private String roomLength;
    private String roomWidth;
    private String ceilingHeight;
    private String doors;
    private String windows;

    private String style;
    private String color;
    private String material;
    private String lighting;

    private String specialRequirement;
    private String budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design_results);

        initializeViews();
        receiveProjectData();
        displayProjectData();
        setupButtons();
    }

    /**
     * Initialize all views from activity_design_results.xml
     */
    private void initializeViews() {

        ivDesignPreview = findViewById(R.id.ivDesignPreview);

        tvRoomSummary = findViewById(R.id.tvRoomSummary);

        tvStyleRecommendation =
                findViewById(R.id.tvStyleRecommendation);

        tvFunctionalRecommendation =
                findViewById(R.id.tvFunctionalRecommendation);

        tvVastuRecommendation =
                findViewById(R.id.tvVastuRecommendation);

        btnViewDetails =
                findViewById(R.id.btnViewDetails);

        // Some versions of the XML may not contain this button.
        btnNewDesign =
                findViewById(R.id.btnNewDesign);
    }

    /**
     * Receive complete project information
     * from AIAnalysisActivity.
     */
    private void receiveProjectData() {

        roomImageUri =
                getIntent().getStringExtra("room_image_uri");

        roomType =
                getIntent().getStringExtra("room_type");

        roomLength =
                getIntent().getStringExtra("room_length");

        roomWidth =
                getIntent().getStringExtra("room_width");

        ceilingHeight =
                getIntent().getStringExtra("ceiling_height");

        doors =
                getIntent().getStringExtra("doors");

        windows =
                getIntent().getStringExtra("windows");

        style =
                getIntent().getStringExtra("style");

        color =
                getIntent().getStringExtra("color");

        material =
                getIntent().getStringExtra("material");

        lighting =
                getIntent().getStringExtra("lighting");

        specialRequirement =
                getIntent().getStringExtra(
                        "special_requirement"
                );

        budget =
                getIntent().getStringExtra("budget");
    }

    /**
     * Display dynamic project information.
     */
    private void displayProjectData() {

        displayRoomImage();

        displayRoomSummary();

        displayStyleRecommendation();

        displayFunctionalRecommendation();

        displayVastuRecommendation();
    }

    /**
     * Display selected room image.
     */
    private void displayRoomImage() {

        if (roomImageUri != null
                && !roomImageUri.isEmpty()) {

            try {

                Uri imageUri =
                        Uri.parse(roomImageUri);

                ivDesignPreview.setImageURI(imageUri);

            } catch (Exception e) {

                ivDesignPreview.setImageResource(
                        android.R.drawable.ic_menu_gallery
                );
            }
        }
    }

    /**
     * Display a dynamic room summary.
     */
    private void displayRoomSummary() {

        StringBuilder summary =
                new StringBuilder();

        summary.append("AI analysis completed for ");

        if (isValid(roomType)) {
            summary.append(roomType);
        } else {
            summary.append("your room");
        }

        summary.append("\n\n");

        if (isValid(roomLength)
                && isValid(roomWidth)) {

            summary.append("Room Size: ")
                    .append(roomLength)
                    .append(" × ")
                    .append(roomWidth)
                    .append("\n");
        }

        if (isValid(ceilingHeight)) {

            summary.append("Ceiling Height: ")
                    .append(ceilingHeight)
                    .append("\n");
        }

        if (isValid(doors)) {

            summary.append("Doors: ")
                    .append(doors)
                    .append("\n");
        }

        if (isValid(windows)) {

            summary.append("Windows: ")
                    .append(windows)
                    .append("\n");
        }

        if (isValid(budget)) {

            summary.append("Budget: ")
                    .append(budget);
        }

        tvRoomSummary.setText(
                summary.toString()
        );
    }

    /**
     * Display design recommendation based
     * on the user's selected preferences.
     */
    private void displayStyleRecommendation() {

        StringBuilder recommendation =
                new StringBuilder();

        if (isValid(style)) {

            recommendation.append(
                    style
            );

        } else {

            recommendation.append(
                    "Personalized Interior Design"
            );
        }

        if (isValid(color)) {

            recommendation.append(
                    " with "
            ).append(
                    color
            ).append(
                    " color palette"
            );
        }

        if (isValid(material)) {

            recommendation.append(
                    ", using "
            ).append(
                    material
            ).append(
                    " materials"
            );
        }

        if (isValid(lighting)) {

            recommendation.append(
                    " and "
            ).append(
                    lighting
            ).append(
                    " lighting"
            );
        }

        tvStyleRecommendation.setText(
                recommendation.toString()
        );
    }

    /**
     * Generate functional recommendations
     * from room information and requirements.
     */
    private void displayFunctionalRecommendation() {

        StringBuilder recommendation =
                new StringBuilder();

        recommendation.append(
                "Functional Recommendations\n\n"
        );

        recommendation.append(
                "• Maintain comfortable walking space "
                        + "between furniture.\n"
        );

        recommendation.append(
                "• Arrange furniture according to "
                        + "the room dimensions.\n"
        );

        if (isValid(doors)) {

            recommendation.append(
                    "• Keep the door movement area "
                            + "clear and accessible.\n"
            );
        }

        if (isValid(windows)) {

            recommendation.append(
                    "• Avoid blocking windows so "
                            + "natural light can enter the room.\n"
            );
        }

        if (isValid(lighting)) {

            recommendation.append(
                    "• Use "
            ).append(
                    lighting
            ).append(
                    " lighting according to "
                            + "the room's activities.\n"
            );
        }

        if (isValid(specialRequirement)) {

            recommendation.append(
                    "• Special requirement: "
            ).append(
                    specialRequirement
            ).append(
                    "\n"
            );
        }

        if (isValid(budget)) {

            recommendation.append(
                    "• Keep furniture and material "
                            + "selection within the budget of "
            ).append(
                    budget
            ).append(
                    ".\n"
            );
        }

        tvFunctionalRecommendation.setText(
                recommendation.toString()
        );
    }

    /**
     * Generate Vastu-related information.
     *
     * The current SurveyActivity does not pass
     * the Vastu checkbox/directions yet, so this
     * section avoids inventing directions.
     */
    private void displayVastuRecommendation() {

        tvVastuRecommendation.setText(
                "Vastu Considerations\n\n" +
                        "• Keep the entrance area clear.\n" +
                        "• Maintain an open and uncluttered "
                        + "central area.\n" +
                        "• Place furniture while maintaining "
                        + "comfortable movement.\n" +
                        "• Final Vastu recommendations will "
                        + "use the selected room directions "
                        + "when those values are connected "
                        + "to the AI analysis."
        );
    }

    /**
     * Configure action buttons.
     */
    private void setupButtons() {

        if (btnViewDetails != null) {

            btnViewDetails.setOnClickListener(
                    v -> showProjectDetails()
            );
        }

        /*
         * Some versions of activity_design_results.xml
         * do not contain btnNewDesign.
         *
         * Therefore we check for null before using it.
         */
        if (btnNewDesign != null) {

            btnNewDesign.setOnClickListener(v -> {

                Intent intent = new Intent(
                        DesignResultsActivity.this,
                        NewProjectActivity.class
                );

                startActivity(intent);

                finish();
            });
        }
    }

    /**
     * Show complete project details.
     *
     * This keeps the current XML unchanged while
     * providing useful dynamic information.
     */
    private void showProjectDetails() {

        StringBuilder details =
                new StringBuilder();

        details.append("Project Details\n\n");

        addDetail(
                details,
                "Room Type",
                roomType
        );

        addDetail(
                details,
                "Room Length",
                roomLength
        );

        addDetail(
                details,
                "Room Width",
                roomWidth
        );

        addDetail(
                details,
                "Ceiling Height",
                ceilingHeight
        );

        addDetail(
                details,
                "Doors",
                doors
        );

        addDetail(
                details,
                "Windows",
                windows
        );

        addDetail(
                details,
                "Style",
                style
        );

        addDetail(
                details,
                "Color",
                color
        );

        addDetail(
                details,
                "Material",
                material
        );

        addDetail(
                details,
                "Lighting",
                lighting
        );

        addDetail(
                details,
                "Budget",
                budget
        );

        addDetail(
                details,
                "Special Requirement",
                specialRequirement
        );

        new android.app.AlertDialog.Builder(this)
                .setTitle("Design Details")
                .setMessage(details.toString())
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    /**
     * Add a field only when meaningful data exists.
     */
    private void addDetail(
            StringBuilder details,
            String label,
            String value
    ) {

        if (isValid(value)) {

            details.append(label)
                    .append(": ")
                    .append(value)
                    .append("\n");
        }
    }

    /**
     * Check whether a String contains useful data.
     */
    private boolean isValid(String value) {

        return value != null
                && !value.trim().isEmpty();
    }
}