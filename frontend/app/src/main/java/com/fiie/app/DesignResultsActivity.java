package com.fiie.app;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
    private Button btnDownloadDesign;
    private Button btnNewDesign;

    private static final int STORAGE_PERMISSION_REQUEST = 101;


    // =========================================
    // PROJECT DATA
    // =========================================

    private long userId;

    private String projectId;

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

        btnDownloadDesign =
                findViewById(R.id.btnDownloadDesign);

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
        // Project ID
        // -----------------------------------------

        projectId =
                intent.getStringExtra(
                        "PROJECT_ID"
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
        // FULL SCREEN IMAGE PREVIEW
        // -----------------------------------------

        if (ivDesignPreview != null) {

            ivDesignPreview.setOnClickListener(
                    v -> showFullScreenImage()
            );
        }


        // -----------------------------------------
        // DOWNLOAD DESIGN
        // -----------------------------------------

        if (btnDownloadDesign != null) {

            btnDownloadDesign.setOnClickListener(
                    v -> downloadDesign()
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
    // FULL SCREEN IMAGE PREVIEW
    // =========================================

    private void showFullScreenImage() {

        if (ivDesignPreview == null ||
                ivDesignPreview.getDrawable() == null) {

            Toast.makeText(
                    this,
                    "No design image available.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        Dialog dialog =
                new Dialog(
                        this,
                        android.R.style.Theme_Black_NoTitleBar_Fullscreen
                );


        dialog.setContentView(
                R.layout.dialog_fullscreen_image
        );


        ImageView ivFullScreenImage =
                dialog.findViewById(
                        R.id.ivFullScreenImage
                );


        TextView btnCloseImage =
                dialog.findViewById(
                        R.id.btnCloseImage
                );


        // Use the same image displayed in Design Results
        ivFullScreenImage.setImageDrawable(
                ivDesignPreview.getDrawable()
        );


        // Close button
        btnCloseImage.setOnClickListener(
                v -> dialog.dismiss()
        );


        dialog.show();


        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.BLACK)
            );


            dialog.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
            );
        }
    }


    // =========================================
    // DOWNLOAD DESIGN
    // =========================================

    private void downloadDesign() {

        if (ivDesignPreview == null ||
                ivDesignPreview.getDrawable() == null) {

            Toast.makeText(
                    this,
                    "No design image available to download.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // Android 9 and below
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        STORAGE_PERMISSION_REQUEST
                );

                return;
            }
        }


        saveImageToGallery();
    }


    // =========================================
    // SAVE IMAGE TO GALLERY
    // =========================================

    private void saveImageToGallery() {

        try {

            Drawable drawable =
                    ivDesignPreview.getDrawable();

            int width =
                    drawable.getIntrinsicWidth() > 0
                            ? drawable.getIntrinsicWidth()
                            : ivDesignPreview.getWidth();

            int height =
                    drawable.getIntrinsicHeight() > 0
                            ? drawable.getIntrinsicHeight()
                            : ivDesignPreview.getHeight();

            if (width <= 0 || height <= 0) {

                Toast.makeText(
                        this,
                        "Unable to prepare the design image.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            Bitmap bitmap =
                    Bitmap.createBitmap(
                            width,
                            height,
                            Bitmap.Config.ARGB_8888
                    );


            Canvas canvas =
                    new Canvas(bitmap);

            drawable.setBounds(
                    0,
                    0,
                    canvas.getWidth(),
                    canvas.getHeight()
            );

            drawable.draw(canvas);


            String fileName =
                    "FIIE_Design_"
                            + System.currentTimeMillis()
                            + ".jpg";


            // =====================================
            // ANDROID 10+
            // =====================================

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentValues values =
                        new ContentValues();

                values.put(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        fileName
                );

                values.put(
                        MediaStore.Images.Media.MIME_TYPE,
                        "image/jpeg"
                );

                values.put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES
                                + "/FIIE"
                );

                values.put(
                        MediaStore.Images.Media.IS_PENDING,
                        1
                );


                Uri imageUri =
                        getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values
                        );


                if (imageUri == null) {

                    Toast.makeText(
                            this,
                            "Failed to save design.",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }


                try (OutputStream outputStream =
                             getContentResolver()
                                     .openOutputStream(imageUri)) {

                    if (outputStream == null) {
                        throw new Exception(
                                "Unable to open image output."
                        );
                    }

                    bitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            95,
                            outputStream
                    );
                }


                values.clear();

                values.put(
                        MediaStore.Images.Media.IS_PENDING,
                        0
                );

                getContentResolver().update(
                        imageUri,
                        values,
                        null,
                        null
                );


                // =====================================
                // ANDROID 9 AND BELOW
                // =====================================

            } else {

                File picturesDirectory =
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES
                        );

                File fiieDirectory =
                        new File(
                                picturesDirectory,
                                "FIIE"
                        );


                if (!fiieDirectory.exists()) {

                    if (!fiieDirectory.mkdirs()) {

                        Toast.makeText(
                                this,
                                "Unable to create Gallery folder.",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }
                }


                File imageFile =
                        new File(
                                fiieDirectory,
                                fileName
                        );


                try (FileOutputStream outputStream =
                             new FileOutputStream(imageFile)) {

                    bitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            95,
                            outputStream
                    );
                }


                // Make the image visible in Gallery
                sendBroadcast(
                        new Intent(
                                android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(imageFile)
                        )
                );
            }


            // =====================================
            // GALLERY SAVE SUCCESSFUL
            // =====================================

            Toast.makeText(
                    this,
                    "Design saved to Gallery.",
                    Toast.LENGTH_SHORT
            ).show();

            // Save to Saved Designs only after
            // the Gallery save has succeeded.
            saveProjectToSaved();


        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Failed to save design.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =========================================
    // SAVE PROJECT TO SAVED DESIGNS
    // =========================================

    private void saveProjectToSaved() {

        try {

            SharedPreferences preferences =
                    getSharedPreferences(
                            "FIIE_PREFS",
                            MODE_PRIVATE
                    );

            String savedProjectsJson =
                    preferences.getString(
                            "FIIE_SAVED_PROJECTS",
                            "[]"
                    );

            JSONArray savedProjects =
                    new JSONArray(savedProjectsJson);


            // -----------------------------------------
            // Create a project ID if one was not passed
            // -----------------------------------------

            String finalProjectId =
                    projectId;

            if (!isValid(finalProjectId)) {

                finalProjectId =
                        String.valueOf(
                                (
                                        roomType
                                                + "|"
                                                + roomLength
                                                + "|"
                                                + roomWidth
                                                + "|"
                                                + style
                                                + "|"
                                                + color
                                                + "|"
                                                + budget
                                ).hashCode()
                        );
            }


            // -----------------------------------------
            // Prevent duplicate saved projects
            // -----------------------------------------

            for (int i = 0;
                 i < savedProjects.length();
                 i++) {

                JSONObject existingProject =
                        savedProjects.getJSONObject(i);

                String existingId =
                        existingProject.optString(
                                "project_id",
                                ""
                        );

                if (existingId.equals(finalProjectId)) {

                    Toast.makeText(
                            this,
                            "Design is already saved.",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }
            }


            // -----------------------------------------
            // Create saved project object
            // -----------------------------------------

            JSONObject savedProject =
                    new JSONObject();

            savedProject.put(
                    "project_id",
                    finalProjectId
            );

            savedProject.put(
                    "user_id",
                    userId
            );

            savedProject.put(
                    "project_name",
                    isValid(style)
                            ? style + " Interior Design"
                            : "My Interior Design"
            );

            savedProject.put(
                    "room_image_uri",
                    roomImageUri
            );

            // The current Design Results image is the
            // image displayed in ivDesignPreview.
            savedProject.put(
                    "design_image_uri",
                    roomImageUri
            );

            savedProject.put(
                    "room_type",
                    roomType
            );

            savedProject.put(
                    "room_length",
                    roomLength
            );

            savedProject.put(
                    "room_width",
                    roomWidth
            );

            savedProject.put(
                    "ceiling_height",
                    ceilingHeight
            );

            savedProject.put(
                    "doors",
                    doors
            );

            savedProject.put(
                    "windows",
                    windows
            );

            savedProject.put(
                    "style",
                    style
            );

            savedProject.put(
                    "color",
                    color
            );

            savedProject.put(
                    "material",
                    material
            );

            savedProject.put(
                    "lighting",
                    lighting
            );

            savedProject.put(
                    "special_requirement",
                    specialRequirement
            );

            savedProject.put(
                    "furniture_action",
                    furnitureAction
            );

            savedProject.put(
                    "vastu_enabled",
                    vastuEnabled
            );

            savedProject.put(
                    "door_direction",
                    doorDirection
            );

            savedProject.put(
                    "bed_direction",
                    bedDirection
            );

            savedProject.put(
                    "kitchen_direction",
                    kitchenDirection
            );

            savedProject.put(
                    "pooja_direction",
                    poojaDirection
            );

            savedProject.put(
                    "budget",
                    budget
            );

            savedProject.put(
                    "budget_priority",
                    budgetPriority
            );

            savedProject.put(
                    "completion",
                    completion
            );

            savedProject.put(
                    "date",
                    System.currentTimeMillis()
            );


            // -----------------------------------------
            // Add project to Saved Designs
            // -----------------------------------------

            savedProjects.put(
                    savedProject
            );


            preferences.edit()
                    .putString(
                            "FIIE_SAVED_PROJECTS",
                            savedProjects.toString()
                    )
                    .apply();


        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Design saved to Gallery, but could not be added to Saved Designs.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }


    // =========================================
    // STORAGE PERMISSION RESULT
    // =========================================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );


        if (requestCode ==
                STORAGE_PERMISSION_REQUEST) {

            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                saveImageToGallery();

            } else {

                Toast.makeText(
                        this,
                        "Storage permission is required to save the design.",
                        Toast.LENGTH_SHORT
                ).show();
            }
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