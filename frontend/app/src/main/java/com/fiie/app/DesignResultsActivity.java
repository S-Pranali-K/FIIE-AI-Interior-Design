package com.fiie.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DesignResultsActivity extends AppCompatActivity {

    // =========================================
    // VIEWS
    // =========================================

    private ImageView ivDesignPreview;

    private TextView tvRoomSummary;
    private TextView tvStyleRecommendation;
    private TextView tvFunctionalRecommendation;
    private TextView tvVastuRecommendation;

    private Button btnViewDetails;
    private Button btnRecommendations;
    private Button btnNewDesign;


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


    // =========================================
    // ON CREATE
    // =========================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design_results);

        initializeViews();

        receiveProjectData();

        displayProjectData();

        setupButtons();
    }


    // =========================================
    // INITIALIZE VIEWS
    // =========================================

    private void initializeViews() {

        ivDesignPreview =
                findViewById(R.id.ivDesignPreview);

        tvRoomSummary =
                findViewById(R.id.tvRoomSummary);

        tvStyleRecommendation =
                findViewById(R.id.tvStyleRecommendation);

        tvFunctionalRecommendation =
                findViewById(R.id.tvFunctionalRecommendation);

        tvVastuRecommendation =
                findViewById(R.id.tvVastuRecommendation);

        btnViewDetails =
                findViewById(R.id.btnViewDetails);

        btnRecommendations =
                findViewById(R.id.btnRecommendations);

        btnNewDesign =
                findViewById(R.id.btnNewDesign);
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
    // DISPLAY PROJECT DATA
    // =========================================

    private void displayProjectData() {

        displayRoomImage();

        displayRoomSummary();

        displayStyleRecommendation();

        displayFunctionalRecommendation();

        displayVastuRecommendation();
    }


    // =========================================
    // ROOM IMAGE
    // =========================================

    private void displayRoomImage() {

        if (ivDesignPreview == null) {
            return;
        }

        if (isValid(roomImageUri)) {

            try {

                Uri imageUri =
                        Uri.parse(roomImageUri);

                ivDesignPreview.setImageURI(
                        imageUri
                );

            } catch (Exception e) {

                ivDesignPreview.setImageResource(
                        android.R.drawable.ic_menu_gallery
                );
            }

        } else {

            ivDesignPreview.setImageResource(
                    android.R.drawable.ic_menu_gallery
            );
        }
    }


    // =========================================
    // ROOM SUMMARY
    // =========================================

    private void displayRoomSummary() {

        StringBuilder summary =
                new StringBuilder();

        summary.append("Room: ")
                .append(
                        isValid(roomType)
                                ? roomType
                                : "Not specified"
                )
                .append("\n\n");

        summary.append("Room Size: ")
                .append(
                        isValid(roomLength)
                                ? roomLength
                                : "N/A"
                )
                .append(" × ")
                .append(
                        isValid(roomWidth)
                                ? roomWidth
                                : "N/A"
                )
                .append("\n");

        summary.append("Ceiling Height: ")
                .append(
                        isValid(ceilingHeight)
                                ? ceilingHeight
                                : "N/A"
                )
                .append("\n");

        summary.append("Doors: ")
                .append(
                        isValid(doors)
                                ? doors
                                : "N/A"
                )
                .append("\n");

        summary.append("Windows: ")
                .append(
                        isValid(windows)
                                ? windows
                                : "N/A"
                )
                .append("\n");

        summary.append("Budget: ")
                .append(
                        isValid(budget)
                                ? budget
                                : "N/A"
                );

        tvRoomSummary.setText(
                summary.toString()
        );
    }


    // =========================================
    // STYLE RECOMMENDATION
    // =========================================

    private void displayStyleRecommendation() {

        StringBuilder recommendation =
                new StringBuilder();

        recommendation.append(
                        "Style: "
                )
                .append(
                        isValid(style)
                                ? style
                                : "Not specified"
                )
                .append("\n\n");

        recommendation.append(
                        "Color Preference: "
                )
                .append(
                        isValid(color)
                                ? color
                                : "Not specified"
                )
                .append("\n\n");

        recommendation.append(
                        "Material: "
                )
                .append(
                        isValid(material)
                                ? material
                                : "Not specified"
                )
                .append("\n\n");

        recommendation.append(
                        "Lighting: "
                )
                .append(
                        isValid(lighting)
                                ? lighting
                                : "Not specified"
                );

        tvStyleRecommendation.setText(
                recommendation.toString()
        );
    }


    // =========================================
    // FUNCTIONAL RECOMMENDATION
    // =========================================

    private void displayFunctionalRecommendation() {

        StringBuilder recommendation =
                new StringBuilder();

        recommendation.append(
                "• Maintain clear walking space between major furniture pieces.\n\n"
        );

        recommendation.append(
                "• Use furniture dimensions that are appropriate for the available room area.\n\n"
        );

        recommendation.append(
                "• Keep doors and windows unobstructed for comfortable movement and ventilation.\n\n"
        );


        if (isValid(furnitureAction)) {

            recommendation.append(
                            "Furniture Preference: "
                    )
                    .append(furnitureAction)
                    .append("\n\n");
        }


        if (isValid(specialRequirement)) {

            recommendation.append(
                            "Special Requirement: "
                    )
                    .append(specialRequirement)
                    .append("\n\n");
        }


        if (isValid(lighting)) {

            recommendation.append(
                            "Lighting Consideration: "
                    )
                    .append(lighting)
                    .append("\n\n");
        }


        if (isValid(budgetPriority)) {

            recommendation.append(
                            "Budget Priority: "
                    )
                    .append(budgetPriority);
        }


        tvFunctionalRecommendation.setText(
                recommendation.toString()
        );
    }


    // =========================================
    // VASTU RECOMMENDATION
    // =========================================

    private void displayVastuRecommendation() {

        StringBuilder recommendation =
                new StringBuilder();


        if (!vastuEnabled) {

            recommendation.append(
                    "Vastu suggestions were not enabled for this project."
            );

        } else {

            recommendation.append(
                    "Vastu preferences are enabled for this project.\n\n"
            );


            if (isValid(doorDirection)) {

                recommendation.append(
                                "Door Direction: "
                        )
                        .append(doorDirection)
                        .append("\n\n");
            }


            if (isValid(bedDirection)) {

                recommendation.append(
                                "Bed Direction: "
                        )
                        .append(bedDirection)
                        .append("\n\n");
            }


            if (isValid(kitchenDirection)) {

                recommendation.append(
                                "Kitchen Direction: "
                        )
                        .append(kitchenDirection)
                        .append("\n\n");
            }


            if (isValid(poojaDirection)) {

                recommendation.append(
                                "Pooja Direction: "
                        )
                        .append(poojaDirection);
            }
        }


        tvVastuRecommendation.setText(
                recommendation.toString()
        );
    }


    // =========================================
    // BUTTONS
    // =========================================

    private void setupButtons() {

        // -----------------------------------------
        // VIEW FULL DETAILS
        // -----------------------------------------

        if (btnViewDetails != null) {

            btnViewDetails.setOnClickListener(
                    v -> openDesignDetails()
            );
        }


        // -----------------------------------------
        // VIEW RECOMMENDATIONS
        // -----------------------------------------

        if (btnRecommendations != null) {

            btnRecommendations.setOnClickListener(
                    v -> openRecommendations()
            );
        }


        // -----------------------------------------
        // CREATE NEW DESIGN
        // -----------------------------------------

        if (btnNewDesign != null) {

            btnNewDesign.setOnClickListener(
                    v -> {

                        Intent intent =
                                new Intent(
                                        DesignResultsActivity.this,
                                        NewProjectActivity.class
                                );

                        startActivity(intent);

                        finish();
                    }
            );
        }
    }


    // =========================================
    // OPEN DESIGN DETAILS
    // =========================================

    private void openDesignDetails() {

        String selectedDesign =
                (isValid(style)
                        ? style
                        : "Personalized")
                        + " Interior Design";


        String whySuitable =
                "This design is selected based on your "
                        + (isValid(style)
                        ? style
                        : "preferred")
                        + " style, "
                        + (isValid(color)
                        ? color.toLowerCase()
                        : "selected")
                        + " color preference, and the "
                        + (isValid(roomType)
                        ? roomType.toLowerCase()
                        : "room")
                        + " requirements.";


        String spaceUtilization =
                "The layout focuses on efficient use of available "
                        + "floor space while maintaining comfortable "
                        + "movement around the main furniture.";


        String furniturePlacement =
                "Furniture placement is planned to maintain clear "
                        + "walking paths and practical access to doors "
                        + "and windows.";

        if (isValid(furnitureAction)) {

            furniturePlacement +=
                    " Existing furniture preference: "
                            + furnitureAction
                            + ".";
        }


        String lightingDetails =
                isValid(lighting)
                        ? "The design follows your "
                          + lighting
                          + " lighting preference while maintaining "
                          + "comfortable illumination for the room."
                        : "The design uses a balanced lighting "
                          + "arrangement suitable for the room.";


        String materialDetails =
                isValid(material)
                        ? "The recommended material direction is "
                          + material
                          + " to complement the selected style."
                        : "Materials are selected to complement "
                          + "the overall interior style.";


        String functionalScore =
                "Good";


        DesignDetailData designDetailData =
                new DesignDetailData(
                        selectedDesign,
                        whySuitable,
                        spaceUtilization,
                        furniturePlacement,
                        lightingDetails,
                        materialDetails,
                        functionalScore
                );


        Intent intent =
                new Intent(
                        DesignResultsActivity.this,
                        DesignDetailsActivity.class
                );


        intent.putExtra(
                "DESIGN_DETAILS",
                designDetailData
        );


        startActivity(intent);
    }


    // =========================================
    // OPEN RECOMMENDATIONS
    // =========================================

    private void openRecommendations() {

        ArrayList<RecommendationData> recommendations =
                new ArrayList<>();


        // -----------------------------------------
        // STYLE
        // -----------------------------------------

        if (isValid(style)) {

            recommendations.add(
                    new RecommendationData(
                            "Style",
                            "Continue with the "
                                    + style
                                    + " interior style.",
                            "It matches the design preference selected "
                                    + "for this project."
                    )
            );
        }


        // -----------------------------------------
        // COLOR
        // -----------------------------------------

        if (isValid(color)) {

            recommendations.add(
                    new RecommendationData(
                            "Color",
                            "Use "
                                    + color
                                    + " tones as part of the room palette.",
                            "This keeps the final design aligned with "
                                    + "your selected color preference."
                    )
            );
        }


        // -----------------------------------------
        // MATERIAL
        // -----------------------------------------

        if (isValid(material)) {

            recommendations.add(
                    new RecommendationData(
                            "Materials",
                            "Consider "
                                    + material
                                    + " for suitable furniture and "
                                    + "interior surfaces.",
                            "The material choice complements the selected "
                                    + "design direction."
                    )
            );
        }


        // -----------------------------------------
        // LIGHTING
        // -----------------------------------------

        if (isValid(lighting)) {

            recommendations.add(
                    new RecommendationData(
                            "Lighting",
                            "Use "
                                    + lighting
                                    + " lighting in the planned areas.",
                            "It follows the lighting preference provided "
                                    + "during the room survey."
                    )
            );
        }


        // -----------------------------------------
        // FURNITURE
        // -----------------------------------------

        if (isValid(furnitureAction)) {

            recommendations.add(
                    new RecommendationData(
                            "Furniture",
                            furnitureAction,
                            "This recommendation considers the existing "
                                    + "furniture arrangement and your selected "
                                    + "furniture preference."
                    )
            );
        }


        // -----------------------------------------
        // EXISTING FURNITURE
        // -----------------------------------------

        StringBuilder existingFurniture =
                new StringBuilder();

        if (hasBed) {
            existingFurniture.append("Bed, ");
        }

        if (hasWardrobe) {
            existingFurniture.append("Wardrobe, ");
        }

        if (hasStudyTable) {
            existingFurniture.append("Study table, ");
        }

        if (hasChair) {
            existingFurniture.append("Chair, ");
        }

        if (hasSofa) {
            existingFurniture.append("Sofa, ");
        }

        if (hasTvUnit) {
            existingFurniture.append("TV unit, ");
        }

        if (hasOtherFurniture) {
            existingFurniture.append("Other furniture, ");
        }


        if (existingFurniture.length() > 0) {

            String furnitureList =
                    existingFurniture
                            .toString()
                            .replaceAll(", $", "");


            recommendations.add(
                    new RecommendationData(
                            "Existing Furniture",
                            "Plan the new layout around the existing "
                                    + "items: "
                                    + furnitureList
                                    + ".",
                            "Considering existing furniture helps avoid "
                                    + "unnecessary replacement and improves "
                                    + "space planning."
                    )
            );
        }


        // -----------------------------------------
        // SPACE UTILIZATION
        // -----------------------------------------

        recommendations.add(
                new RecommendationData(
                        "Space Utilization",
                        "Maintain clear movement paths around major "
                                + "furniture pieces and avoid blocking "
                                + "doors or windows.",
                        "Efficient circulation helps make the room "
                                + "more comfortable and practical."
                )
        );


        // -----------------------------------------
        // SPECIAL REQUIREMENT
        // -----------------------------------------

        if (isValid(specialRequirement)) {

            recommendations.add(
                    new RecommendationData(
                            "Special Requirement",
                            specialRequirement,
                            "This recommendation is based on the "
                                    + "specific requirement provided "
                                    + "for the project."
                    )
            );
        }


        // -----------------------------------------
        // BUDGET
        // -----------------------------------------

        if (isValid(budget)) {

            String budgetMessage =
                    "Plan furniture and material selections "
                            + "within the "
                            + budget
                            + " budget range.";

            if (isValid(budgetPriority)) {

                budgetMessage +=
                        " Your budget priority is "
                                + budgetPriority
                                + ".";
            }


            recommendations.add(
                    new RecommendationData(
                            "Budget",
                            budgetMessage,
                            "Keeping selections within the chosen "
                                    + "budget helps maintain project "
                                    + "feasibility."
                    )
            );
        }


        // -----------------------------------------
        // VASTU
        // -----------------------------------------

        if (vastuEnabled) {

            String vastuRecommendation =
                    "Consider the selected Vastu directions when "
                            + "finalizing furniture placement.";


            if (isValid(doorDirection)) {

                vastuRecommendation +=
                        " Door: "
                                + doorDirection
                                + ".";
            }


            if (isValid(bedDirection)) {

                vastuRecommendation +=
                        " Bed: "
                                + bedDirection
                                + ".";
            }


            if (isValid(kitchenDirection)) {

                vastuRecommendation +=
                        " Kitchen: "
                                + kitchenDirection
                                + ".";
            }


            if (isValid(poojaDirection)) {

                vastuRecommendation +=
                        " Pooja: "
                                + poojaDirection
                                + ".";
            }


            recommendations.add(
                    new RecommendationData(
                            "Vastu",
                            vastuRecommendation,
                            "These suggestions are based on the Vastu "
                                    + "preferences entered for this project."
                    )
            );
        }


        // -----------------------------------------
        // OPEN RECOMMENDATIONS SCREEN
        // -----------------------------------------

        Intent intent =
                new Intent(
                        DesignResultsActivity.this,
                        RecommendationsActivity.class
                );


        intent.putExtra(
                "RECOMMENDATIONS",
                recommendations
        );


        startActivity(intent);
    }


    // =========================================
    // SHOW PROJECT DETAILS
    // =========================================

    private void showProjectDetails() {

        StringBuilder details =
                new StringBuilder();

        details.append("Room Type: ")
                .append(
                        isValid(roomType)
                                ? roomType
                                : "N/A"
                )
                .append("\n\n");

        details.append("Room Size: ")
                .append(
                        isValid(roomLength)
                                ? roomLength
                                : "N/A"
                )
                .append(" × ")
                .append(
                        isValid(roomWidth)
                                ? roomWidth
                                : "N/A"
                )
                .append("\n\n");

        details.append("Ceiling Height: ")
                .append(
                        isValid(ceilingHeight)
                                ? ceilingHeight
                                : "N/A"
                )
                .append("\n\n");

        details.append("Doors: ")
                .append(
                        isValid(doors)
                                ? doors
                                : "N/A"
                )
                .append("\n\n");

        details.append("Windows: ")
                .append(
                        isValid(windows)
                                ? windows
                                : "N/A"
                )
                .append("\n\n");

        details.append("Style: ")
                .append(
                        isValid(style)
                                ? style
                                : "N/A"
                )
                .append("\n\n");

        details.append("Color: ")
                .append(
                        isValid(color)
                                ? color
                                : "N/A"
                )
                .append("\n\n");

        details.append("Material: ")
                .append(
                        isValid(material)
                                ? material
                                : "N/A"
                )
                .append("\n\n");

        details.append("Lighting: ")
                .append(
                        isValid(lighting)
                                ? lighting
                                : "N/A"
                )
                .append("\n\n");

        details.append("Special Requirement: ")
                .append(
                        isValid(specialRequirement)
                                ? specialRequirement
                                : "None"
                )
                .append("\n\n");

        details.append("Budget: ")
                .append(
                        isValid(budget)
                                ? budget
                                : "N/A"
                );


        new AlertDialog.Builder(this)
                .setTitle("Project Details")
                .setMessage(details.toString())
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }


    // =========================================
    // VALIDATION HELPER
    // =========================================

    private boolean isValid(String value) {

        return value != null
                && !value.trim().isEmpty()
                && !value.equalsIgnoreCase("null");
    }
}

