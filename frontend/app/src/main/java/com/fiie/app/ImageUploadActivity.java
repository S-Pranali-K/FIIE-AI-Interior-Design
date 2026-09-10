package com.fiie.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUEST = 100;

    private ImageView ivRoomImage;
    private TextView tvImageStatus;
    private Button btnSelectImage;
    private Button btnContinueImage;

    private Uri selectedImageUri;

    // -----------------------------------------
    // Project / Survey Data
    // -----------------------------------------

    private String roomImageUri;

    private long userId;

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

        setContentView(R.layout.activity_image_upload);


        // -----------------------------------------
        // Initialize Views
        // -----------------------------------------

        ivRoomImage =
                findViewById(R.id.ivRoomImage);

        tvImageStatus =
                findViewById(R.id.tvImageStatus);

        btnSelectImage =
                findViewById(R.id.btnSelectImage);

        btnContinueImage =
                findViewById(R.id.btnContinueImage);


        // -----------------------------------------
        // Receive Survey Data
        // -----------------------------------------

        receiveSurveyData();


        // -----------------------------------------
        // Display Existing Image
        // -----------------------------------------

        if (roomImageUri != null
                && !roomImageUri.isEmpty()) {

            selectedImageUri =
                    Uri.parse(roomImageUri);

            ivRoomImage.setImageURI(
                    selectedImageUri
            );

            ivRoomImage.setVisibility(
                    ImageView.VISIBLE
            );

            tvImageStatus.setText(
                    "Room image selected successfully"
            );
        }


        // -----------------------------------------
        // Select / Replace Image
        // -----------------------------------------

        btnSelectImage.setOnClickListener(
                v -> openImagePicker()
        );


        // -----------------------------------------
        // Continue to AI Analysis
        // -----------------------------------------

        btnContinueImage.setOnClickListener(
                v -> continueToAIAnalysis()
        );
    }


    // =========================================
    // RECEIVE SURVEY DATA
    // =========================================

    private void receiveSurveyData() {

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
        // Image
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
    // CONTINUE TO AI ANALYSIS
    // =========================================

    private void continueToAIAnalysis() {

        if (selectedImageUri == null) {

            Toast.makeText(
                    ImageUploadActivity.this,
                    "Please select a room image first.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        Intent intent =
                new Intent(
                        ImageUploadActivity.this,
                        AIAnalysisActivity.class
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
                selectedImageUri.toString()
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
        // Vastu Preferences
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
        // Open AI Analysis
        // -----------------------------------------

        startActivity(intent);

        finish();
    }


    // =========================================
    // IMAGE PICKER
    // =========================================

    private void openImagePicker() {

        Intent intent =
                new Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                );

        intent.setType("image/*");

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        startActivityForResult(
                intent,
                IMAGE_PICKER_REQUEST
        );
    }


    // =========================================
    // IMAGE RESULT
    // =========================================

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == IMAGE_PICKER_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            selectedImageUri =
                    data.getData();

            ivRoomImage.setImageURI(
                    selectedImageUri
            );

            ivRoomImage.setVisibility(
                    ImageView.VISIBLE
            );

            tvImageStatus.setText(
                    "Room image selected successfully"
            );

            Toast.makeText(
                    ImageUploadActivity.this,
                    "Room image updated successfully.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}