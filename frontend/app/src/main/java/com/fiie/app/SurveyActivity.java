package com.fiie.app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);

        initializeViews();
        setupSpinners();

        btnContinueSurvey.setOnClickListener(v -> validateSurvey());
    }

    private void initializeViews() {

        spinnerRoomType = findViewById(R.id.spinnerRoomType);

        etRoomLength = findViewById(R.id.etRoomLength);
        etRoomWidth = findViewById(R.id.etRoomWidth);
        etCeilingHeight = findViewById(R.id.etCeilingHeight);
        etDoors = findViewById(R.id.etDoors);
        etWindows = findViewById(R.id.etWindows);

        cbBed = findViewById(R.id.cbBed);
        cbWardrobe = findViewById(R.id.cbWardrobe);
        cbStudyTable = findViewById(R.id.cbStudyTable);
        cbChair = findViewById(R.id.cbChair);
        cbSofa = findViewById(R.id.cbSofa);
        cbTvUnit = findViewById(R.id.cbTvUnit);
        cbOthers = findViewById(R.id.cbOthers);

        rgFurnitureAction = findViewById(R.id.rgFurnitureAction);

        spinnerStyle = findViewById(R.id.spinnerStyle);
        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerMaterial = findViewById(R.id.spinnerMaterial);
        spinnerLighting = findViewById(R.id.spinnerLighting);
        etSpecialRequirement = findViewById(R.id.etSpecialRequirement);

        cbEnableVastu = findViewById(R.id.cbEnableVastu);
        spinnerDoorDirection = findViewById(R.id.spinnerDoorDirection);
        spinnerBedDirection = findViewById(R.id.spinnerBedDirection);
        spinnerKitchenDirection = findViewById(R.id.spinnerKitchenDirection);
        spinnerPoojaDirection = findViewById(R.id.spinnerPoojaDirection);

        etBudget = findViewById(R.id.etBudget);
        rgBudgetPriority = findViewById(R.id.rgBudgetPriority);
        spinnerCompletion = findViewById(R.id.spinnerCompletion);

        btnContinueSurvey = findViewById(R.id.btnContinueSurvey);
    }

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

        setSpinnerData(spinnerDoorDirection, directions);
        setSpinnerData(spinnerBedDirection, directions);
        setSpinnerData(spinnerKitchenDirection, directions);
        setSpinnerData(spinnerPoojaDirection, directions);

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

    private void setSpinnerData(Spinner spinner, String[] data) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                data
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }

    private void validateSurvey() {

        String length = etRoomLength.getText().toString().trim();
        String width = etRoomWidth.getText().toString().trim();
        String ceilingHeight = etCeilingHeight.getText().toString().trim();
        String doors = etDoors.getText().toString().trim();
        String windows = etWindows.getText().toString().trim();
        String budget = etBudget.getText().toString().trim();

        if (spinnerRoomType.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select room type",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (length.isEmpty()) {
            etRoomLength.setError("Enter room length");
            etRoomLength.requestFocus();
            return;
        }

        if (width.isEmpty()) {
            etRoomWidth.setError("Enter room width");
            etRoomWidth.requestFocus();
            return;
        }

        if (ceilingHeight.isEmpty()) {
            etCeilingHeight.setError("Enter ceiling height");
            etCeilingHeight.requestFocus();
            return;
        }

        if (doors.isEmpty()) {
            etDoors.setError("Enter number of doors");
            etDoors.requestFocus();
            return;
        }

        if (windows.isEmpty()) {
            etWindows.setError("Enter number of windows");
            etWindows.requestFocus();
            return;
        }

        if (rgFurnitureAction.getCheckedRadioButtonId() == -1) {
            Toast.makeText(
                    this,
                    "Please select furniture action",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerStyle.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select preferred style",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerColor.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select color preference",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerMaterial.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select material preference",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerLighting.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select lighting preference",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (cbEnableVastu.isChecked()) {

            if (spinnerDoorDirection.getSelectedItemPosition() == 0) {
                Toast.makeText(
                        this,
                        "Please select main door direction",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (spinnerBedDirection.getSelectedItemPosition() == 0) {
                Toast.makeText(
                        this,
                        "Please select bed direction",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (spinnerKitchenDirection.getSelectedItemPosition() == 0) {
                Toast.makeText(
                        this,
                        "Please select kitchen direction",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (spinnerPoojaDirection.getSelectedItemPosition() == 0) {
                Toast.makeText(
                        this,
                        "Please select pooja/temple direction",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
        }

        if (budget.isEmpty()) {
            etBudget.setError("Enter your budget");
            etBudget.requestFocus();
            return;
        }

        if (rgBudgetPriority.getCheckedRadioButtonId() == -1) {
            Toast.makeText(
                    this,
                    "Please select budget priority",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerCompletion.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select expected completion",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Intent intent = new Intent(
                SurveyActivity.this,
                AIAnalysisActivity.class
        );

        startActivity(intent);
        finish();
    }
}