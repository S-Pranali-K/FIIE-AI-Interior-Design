package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    private Spinner spinnerRoomType;

    private EditText etRoomLength;
    private EditText etRoomWidth;
    private EditText etCeilingHeight;
    private EditText etDoors;
    private EditText etWindows;

    private CheckBox cbBed;
    private CheckBox cbWardrobe;
    private CheckBox cbStudyTable;
    private CheckBox cbChair;
    private CheckBox cbSofa;
    private CheckBox cbTvUnit;
    private CheckBox cbOthers;

    private RadioGroup rgFurnitureAction;

    private Spinner spinnerStyle;
    private Spinner spinnerColor;
    private Spinner spinnerMaterial;
    private Spinner spinnerLighting;

    private EditText etSpecialRequirement;

    private CheckBox cbEnableVastu;

    private Spinner spinnerDoorDirection;
    private Spinner spinnerBedDirection;
    private Spinner spinnerKitchenDirection;
    private Spinner spinnerPoojaDirection;

    private EditText etBudget;

    private RadioGroup rgBudgetPriority;

    private Spinner spinnerCompletion;

    private Button btnContinueSurvey;

    // Image selected in NewProjectActivity
    private String roomImageUri;

    // Logged-in user
    private long userId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);

        // ---------------------------------------------------------
        // Receive selected room image
        // ---------------------------------------------------------

        roomImageUri =
                getIntent().getStringExtra(
                        "room_image_uri"
                );

        // ---------------------------------------------------------
        // Receive user ID
        // ---------------------------------------------------------

        userId =
                getIntent().getLongExtra(
                        "USER_ID",
                        -1
                );

        if (userId == -1) {

            userId =
                    getSharedPreferences(
                            "FIIE_PREFS",
                            MODE_PRIVATE
                    ).getLong(
                            "USER_ID",
                            -1
                    );
        }

        // ---------------------------------------------------------
        // Check user session
        // ---------------------------------------------------------

        if (userId == -1) {

            Toast.makeText(
                    this,
                    "User session not found. Please login again.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent =
                    new Intent(
                            SurveyActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);
            finish();
            return;
        }

        // ---------------------------------------------------------
        // Check that image was selected
        // ---------------------------------------------------------

        if (roomImageUri == null
                || roomImageUri.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please select a room image first.",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // ---------------------------------------------------------
        // Initialize views
        // ---------------------------------------------------------

        initializeViews();

        // ---------------------------------------------------------
        // Setup spinners
        // ---------------------------------------------------------

        setupSpinners();

        // ---------------------------------------------------------
        // Continue button
        // ---------------------------------------------------------

        btnContinueSurvey.setOnClickListener(
                v -> validateAndContinue()
        );
    }


    // =============================================================
    // INITIALIZE VIEWS
    // =============================================================

    private void initializeViews() {

        spinnerRoomType =
                findViewById(R.id.spinnerRoomType);

        etRoomLength =
                findViewById(R.id.etRoomLength);

        etRoomWidth =
                findViewById(R.id.etRoomWidth);

        etCeilingHeight =
                findViewById(R.id.etCeilingHeight);

        etDoors =
                findViewById(R.id.etDoors);

        etWindows =
                findViewById(R.id.etWindows);


        cbBed =
                findViewById(R.id.cbBed);

        cbWardrobe =
                findViewById(R.id.cbWardrobe);

        cbStudyTable =
                findViewById(R.id.cbStudyTable);

        cbChair =
                findViewById(R.id.cbChair);

        cbSofa =
                findViewById(R.id.cbSofa);

        cbTvUnit =
                findViewById(R.id.cbTvUnit);

        cbOthers =
                findViewById(R.id.cbOthers);


        rgFurnitureAction =
                findViewById(R.id.rgFurnitureAction);


        spinnerStyle =
                findViewById(R.id.spinnerStyle);

        spinnerColor =
                findViewById(R.id.spinnerColor);

        spinnerMaterial =
                findViewById(R.id.spinnerMaterial);

        spinnerLighting =
                findViewById(R.id.spinnerLighting);


        etSpecialRequirement =
                findViewById(R.id.etSpecialRequirement);


        cbEnableVastu =
                findViewById(R.id.cbEnableVastu);

        spinnerDoorDirection =
                findViewById(R.id.spinnerDoorDirection);

        spinnerBedDirection =
                findViewById(R.id.spinnerBedDirection);

        spinnerKitchenDirection =
                findViewById(R.id.spinnerKitchenDirection);

        spinnerPoojaDirection =
                findViewById(R.id.spinnerPoojaDirection);


        etBudget =
                findViewById(R.id.etBudget);

        rgBudgetPriority =
                findViewById(R.id.rgBudgetPriority);


        spinnerCompletion =
                findViewById(R.id.spinnerCompletion);


        btnContinueSurvey =
                findViewById(R.id.btnContinueSurvey);
    }


    // =============================================================
    // SETUP SPINNERS
    // =============================================================

    private void setupSpinners() {

        setSpinnerData(
                spinnerRoomType,
                new String[]{
                        "Select Room Type",
                        "Bedroom",
                        "Living Room",
                        "Kitchen",
                        "Dining Room",
                        "Study Room",
                        "Kids Room",
                        "Office"
                }
        );

        setSpinnerData(
                spinnerStyle,
                new String[]{
                        "Select Style",
                        "Modern",
                        "Minimalist",
                        "Contemporary",
                        "Traditional",
                        "Industrial",
                        "Scandinavian"
                }
        );

        setSpinnerData(
                spinnerColor,
                new String[]{
                        "Select Color",
                        "Neutral",
                        "White",
                        "Beige",
                        "Blue",
                        "Green",
                        "Brown",
                        "Grey"
                }
        );

        setSpinnerData(
                spinnerMaterial,
                new String[]{
                        "Select Material",
                        "Wood",
                        "Metal",
                        "Glass",
                        "Marble",
                        "Laminate",
                        "Mixed"
                }
        );

        setSpinnerData(
                spinnerLighting,
                new String[]{
                        "Select Lighting",
                        "Warm White",
                        "Cool White",
                        "Natural Light",
                        "Ambient",
                        "Task Lighting",
                        "Mixed"
                }
        );

        String[] directions = {
                "Select Direction",
                "North",
                "South",
                "East",
                "West",
                "North-East",
                "North-West",
                "South-East",
                "South-West"
        };

        setSpinnerData(
                spinnerDoorDirection,
                directions
        );

        setSpinnerData(
                spinnerBedDirection,
                directions
        );

        setSpinnerData(
                spinnerKitchenDirection,
                directions
        );

        setSpinnerData(
                spinnerPoojaDirection,
                directions
        );

        setSpinnerData(
                spinnerCompletion,
                new String[]{
                        "Select Expected Completion",
                        "Less than 1 Month",
                        "1-2 Months",
                        "2-3 Months",
                        "3-6 Months",
                        "More than 6 Months"
                }
        );
    }


    // =============================================================
    // SPINNER HELPER
    // =============================================================

    private void setSpinnerData(
            Spinner spinner,
            String[] data
    ) {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        data
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }


    // =============================================================
    // VALIDATE SURVEY
    // =============================================================

    private void validateAndContinue() {

        String length =
                etRoomLength
                        .getText()
                        .toString()
                        .trim();

        String width =
                etRoomWidth
                        .getText()
                        .toString()
                        .trim();

        String ceilingHeight =
                etCeilingHeight
                        .getText()
                        .toString()
                        .trim();

        String doors =
                etDoors
                        .getText()
                        .toString()
                        .trim();

        String windows =
                etWindows
                        .getText()
                        .toString()
                        .trim();

        String budget =
                etBudget
                        .getText()
                        .toString()
                        .trim();


        if (spinnerRoomType.getSelectedItemPosition() == 0) {

            showMessage("Please select room type");
            return;
        }


        if (length.isEmpty()) {

            etRoomLength.setError(
                    "Enter room length"
            );

            etRoomLength.requestFocus();
            return;
        }


        if (width.isEmpty()) {

            etRoomWidth.setError(
                    "Enter room width"
            );

            etRoomWidth.requestFocus();
            return;
        }


        if (ceilingHeight.isEmpty()) {

            etCeilingHeight.setError(
                    "Enter ceiling height"
            );

            etCeilingHeight.requestFocus();
            return;
        }


        if (doors.isEmpty()) {

            etDoors.setError(
                    "Enter number of doors"
            );

            etDoors.requestFocus();
            return;
        }


        if (windows.isEmpty()) {

            etWindows.setError(
                    "Enter number of windows"
            );

            etWindows.requestFocus();
            return;
        }


        if (rgFurnitureAction.getCheckedRadioButtonId() == -1) {

            showMessage(
                    "Please select furniture action"
            );

            return;
        }


        if (spinnerStyle.getSelectedItemPosition() == 0) {

            showMessage(
                    "Please select preferred style"
            );

            return;
        }


        if (spinnerColor.getSelectedItemPosition() == 0) {

            showMessage(
                    "Please select color preference"
            );

            return;
        }


        if (spinnerMaterial.getSelectedItemPosition() == 0) {

            showMessage(
                    "Please select material preference"
            );

            return;
        }


        if (spinnerLighting.getSelectedItemPosition() == 0) {

            showMessage(
                    "Please select lighting preference"
            );

            return;
        }


        // ---------------------------------------------------------
        // Vastu validation
        // ---------------------------------------------------------

        if (cbEnableVastu.isChecked()) {

            if (spinnerDoorDirection.getSelectedItemPosition() == 0) {

                showMessage(
                        "Please select main door direction"
                );

                return;
            }

            if (spinnerBedDirection.getSelectedItemPosition() == 0) {

                showMessage(
                        "Please select bed direction"
                );

                return;
            }

            if (spinnerKitchenDirection.getSelectedItemPosition() == 0) {

                showMessage(
                        "Please select kitchen direction"
                );

                return;
            }

            if (spinnerPoojaDirection.getSelectedItemPosition() == 0) {

                showMessage(
                        "Please select pooja/temple direction"
                );

                return;
            }
        }


        if (budget.isEmpty()) {

            etBudget.setError(
                    "Enter your budget"
            );

            etBudget.requestFocus();
            return;
        }


        if (rgBudgetPriority.getCheckedRadioButtonId() == -1) {

            showMessage(
                    "Please select budget priority"
            );

            return;
        }


        if (spinnerCompletion.getSelectedItemPosition() == 0) {

            showMessage(
                    "Please select expected completion"
            );

            return;
        }


        // Everything is valid
        continueToAIAnalysis();
    }


    // =============================================================
    // GO DIRECTLY TO AI ANALYSIS
    // =============================================================

    private void continueToAIAnalysis() {

        Intent intent =
                new Intent(
                        SurveyActivity.this,
                        AIAnalysisActivity.class
                );


        // ---------------------------------------------------------
        // USER ID
        // ---------------------------------------------------------

        intent.putExtra(
                "USER_ID",
                userId
        );


        // ---------------------------------------------------------
        // ROOM IMAGE
        // ---------------------------------------------------------

        intent.putExtra(
                "room_image_uri",
                roomImageUri
        );


        // ---------------------------------------------------------
        // ROOM INFORMATION
        // ---------------------------------------------------------

        intent.putExtra(
                "room_type",
                spinnerRoomType
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "room_length",
                etRoomLength
                        .getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "room_width",
                etRoomWidth
                        .getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "ceiling_height",
                etCeilingHeight
                        .getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "doors",
                etDoors
                        .getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "windows",
                etWindows
                        .getText()
                        .toString()
                        .trim()
        );


        // ---------------------------------------------------------
        // EXISTING FURNITURE
        // ---------------------------------------------------------

        intent.putExtra(
                "has_bed",
                cbBed.isChecked()
        );

        intent.putExtra(
                "has_wardrobe",
                cbWardrobe.isChecked()
        );

        intent.putExtra(
                "has_study_table",
                cbStudyTable.isChecked()
        );

        intent.putExtra(
                "has_chair",
                cbChair.isChecked()
        );

        intent.putExtra(
                "has_sofa",
                cbSofa.isChecked()
        );

        intent.putExtra(
                "has_tv_unit",
                cbTvUnit.isChecked()
        );

        intent.putExtra(
                "has_other_furniture",
                cbOthers.isChecked()
        );


        RadioButton furnitureRadio =
                findViewById(
                        rgFurnitureAction
                                .getCheckedRadioButtonId()
                );

        if (furnitureRadio != null) {

            intent.putExtra(
                    "furniture_action",
                    furnitureRadio
                            .getText()
                            .toString()
            );
        }


        // ---------------------------------------------------------
        // DESIGN PREFERENCES
        // ---------------------------------------------------------

        intent.putExtra(
                "style",
                spinnerStyle
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "color",
                spinnerColor
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "material",
                spinnerMaterial
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "lighting",
                spinnerLighting
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "special_requirement",
                etSpecialRequirement
                        .getText()
                        .toString()
                        .trim()
        );


        // ---------------------------------------------------------
        // VASTU
        // ---------------------------------------------------------

        intent.putExtra(
                "vastu_enabled",
                cbEnableVastu.isChecked()
        );

        if (cbEnableVastu.isChecked()) {

            intent.putExtra(
                    "door_direction",
                    spinnerDoorDirection
                            .getSelectedItem()
                            .toString()
            );

            intent.putExtra(
                    "bed_direction",
                    spinnerBedDirection
                            .getSelectedItem()
                            .toString()
            );

            intent.putExtra(
                    "kitchen_direction",
                    spinnerKitchenDirection
                            .getSelectedItem()
                            .toString()
            );

            intent.putExtra(
                    "pooja_direction",
                    spinnerPoojaDirection
                            .getSelectedItem()
                            .toString()
            );
        }


        // ---------------------------------------------------------
        // BUDGET
        // ---------------------------------------------------------

        intent.putExtra(
                "budget",
                etBudget
                        .getText()
                        .toString()
                        .trim()
        );


        RadioButton budgetRadio =
                findViewById(
                        rgBudgetPriority
                                .getCheckedRadioButtonId()
                );

        if (budgetRadio != null) {

            intent.putExtra(
                    "budget_priority",
                    budgetRadio
                            .getText()
                            .toString()
            );
        }


        // ---------------------------------------------------------
        // COMPLETION
        // ---------------------------------------------------------

        intent.putExtra(
                "completion",
                spinnerCompletion
                        .getSelectedItem()
                        .toString()
        );


        // ---------------------------------------------------------
        // START AI ANALYSIS
        // ---------------------------------------------------------

        Toast.makeText(
                this,
                "Survey completed successfully",
                Toast.LENGTH_SHORT
        ).show();

        startActivity(intent);

        finish();
    }


    // =============================================================
    // SHOW MESSAGE
    // =============================================================

    private void showMessage(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}