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

import com.fiie.app.model.ProjectSurveyRequest;
import com.fiie.app.network.ApiService;
import com.fiie.app.network.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private String roomImageUri;

    private long userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);

        roomImageUri =
                getIntent().getStringExtra("room_image_uri");

        userId =
                getIntent().getLongExtra(
                        "USER_ID",
                        -1
                );

        // If USER_ID was not passed, get it from session
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

        initializeViews();
        setupSpinners();

        btnContinueSurvey.setOnClickListener(
                v -> validateAndContinue()
        );
    }

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

    private void validateAndContinue() {

        String length =
                etRoomLength.getText()
                        .toString()
                        .trim();

        String width =
                etRoomWidth.getText()
                        .toString()
                        .trim();

        String ceilingHeight =
                etCeilingHeight.getText()
                        .toString()
                        .trim();

        String doors =
                etDoors.getText()
                        .toString()
                        .trim();

        String windows =
                etWindows.getText()
                        .toString()
                        .trim();

        String budget =
                etBudget.getText()
                        .toString()
                        .trim();

        if (spinnerRoomType.getSelectedItemPosition() == 0) {
            showMessage("Please select room type");
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
            showMessage("Please select furniture action");
            return;
        }

        if (spinnerStyle.getSelectedItemPosition() == 0) {
            showMessage("Please select preferred style");
            return;
        }

        if (spinnerColor.getSelectedItemPosition() == 0) {
            showMessage("Please select color preference");
            return;
        }

        if (spinnerMaterial.getSelectedItemPosition() == 0) {
            showMessage("Please select material preference");
            return;
        }

        if (spinnerLighting.getSelectedItemPosition() == 0) {
            showMessage("Please select lighting preference");
            return;
        }

        if (cbEnableVastu.isChecked()) {

            if (spinnerDoorDirection.getSelectedItemPosition() == 0) {
                showMessage("Please select main door direction");
                return;
            }

            if (spinnerBedDirection.getSelectedItemPosition() == 0) {
                showMessage("Please select bed direction");
                return;
            }

            if (spinnerKitchenDirection.getSelectedItemPosition() == 0) {
                showMessage("Please select kitchen direction");
                return;
            }

            if (spinnerPoojaDirection.getSelectedItemPosition() == 0) {
                showMessage("Please select pooja/temple direction");
                return;
            }
        }

        if (budget.isEmpty()) {
            etBudget.setError("Enter your budget");
            etBudget.requestFocus();
            return;
        }

        if (rgBudgetPriority.getCheckedRadioButtonId() == -1) {
            showMessage("Please select budget priority");
            return;
        }

        if (spinnerCompletion.getSelectedItemPosition() == 0) {
            showMessage("Please select expected completion");
            return;
        }

        createProjectAndSaveSurvey();
    }

    private void createProjectAndSaveSurvey() {

        String roomType =
                spinnerRoomType
                        .getSelectedItem()
                        .toString();

        String projectName =
                roomType + " Design";

        Toast.makeText(
                this,
                "Creating project...",
                Toast.LENGTH_SHORT
        ).show();

        ApiService apiService =
                RetrofitClient
                        .getInstance()
                        .create(ApiService.class);

        apiService.createProject(
                userId,
                projectName,
                roomType
        ).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(
                    Call<JsonObject> call,
                    Response<JsonObject> response) {

                if (!response.isSuccessful()
                        || response.body() == null
                        || !response.body().has("id")) {

                    Toast.makeText(
                            SurveyActivity.this,
                            "Project creation failed: "
                                    + response.code(),
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                long projectId =
                        response.body()
                                .get("id")
                                .getAsLong();

                saveSurvey(
                        apiService,
                        projectId
                );
            }

            @Override
            public void onFailure(
                    Call<JsonObject> call,
                    Throwable t) {

                Toast.makeText(
                        SurveyActivity.this,
                        "Project API error: "
                                + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void saveSurvey(
            ApiService apiService,
            long projectId
    ) {

        ProjectSurveyRequest request =
                new ProjectSurveyRequest();

        request.setRoomLength(
                Double.parseDouble(
                        etRoomLength.getText()
                                .toString()
                                .trim()
                )
        );

        request.setRoomWidth(
                Double.parseDouble(
                        etRoomWidth.getText()
                                .toString()
                                .trim()
                )
        );

        request.setCeilingHeight(
                Double.parseDouble(
                        etCeilingHeight.getText()
                                .toString()
                                .trim()
                )
        );

        request.setDoors(
                Integer.parseInt(
                        etDoors.getText()
                                .toString()
                                .trim()
                )
        );

        request.setWindows(
                Integer.parseInt(
                        etWindows.getText()
                                .toString()
                                .trim()
                )
        );

        String furniture =
                buildFurnitureString();

        request.setFurniture(furniture);

        RadioButton furnitureRadio =
                findViewById(
                        rgFurnitureAction
                                .getCheckedRadioButtonId()
                );

        if (furnitureRadio != null) {

            request.setFurnitureAction(
                    furnitureRadio
                            .getText()
                            .toString()
            );
        }

        request.setStyle(
                spinnerStyle
                        .getSelectedItem()
                        .toString()
        );

        request.setColor(
                spinnerColor
                        .getSelectedItem()
                        .toString()
        );

        request.setMaterial(
                spinnerMaterial
                        .getSelectedItem()
                        .toString()
        );

        request.setLighting(
                spinnerLighting
                        .getSelectedItem()
                        .toString()
        );

        request.setSpecialRequirement(
                etSpecialRequirement
                        .getText()
                        .toString()
                        .trim()
        );

        request.setVastuEnabled(
                cbEnableVastu.isChecked()
        );

        if (cbEnableVastu.isChecked()) {

            request.setDoorDirection(
                    spinnerDoorDirection
                            .getSelectedItem()
                            .toString()
            );

            request.setBedDirection(
                    spinnerBedDirection
                            .getSelectedItem()
                            .toString()
            );

            request.setKitchenDirection(
                    spinnerKitchenDirection
                            .getSelectedItem()
                            .toString()
            );

            request.setPoojaDirection(
                    spinnerPoojaDirection
                            .getSelectedItem()
                            .toString()
            );

        } else {

            request.setDoorDirection(null);
            request.setBedDirection(null);
            request.setKitchenDirection(null);
            request.setPoojaDirection(null);
        }

        request.setBudget(
                Double.parseDouble(
                        etBudget.getText()
                                .toString()
                                .trim()
                )
        );

        RadioButton budgetRadio =
                findViewById(
                        rgBudgetPriority
                                .getCheckedRadioButtonId()
                );

        if (budgetRadio != null) {

            request.setBudgetPriority(
                    budgetRadio
                            .getText()
                            .toString()
            );
        }

        request.setCompletion(
                spinnerCompletion
                        .getSelectedItem()
                        .toString()
        );

        Toast.makeText(
                this,
                "Saving survey...",
                Toast.LENGTH_SHORT
        ).show();

        apiService.saveSurvey(
                projectId,
                request
        ).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(
                    Call<JsonObject> call,
                    Response<JsonObject> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    Toast.makeText(
                            SurveyActivity.this,
                            "Survey saved successfully. Project ID: "
                                    + projectId,
                            Toast.LENGTH_LONG
                    ).show();

                    openImageUpload(
                            projectId
                    );

                } else {

                    String errorMessage =
                            "Survey save failed: "
                                    + response.code();

                    if (response.errorBody() != null) {
                        try {
                            errorMessage +=
                                    "\n"
                                            + response.errorBody()
                                            .string();
                        } catch (Exception ignored) {
                        }
                    }

                    Toast.makeText(
                            SurveyActivity.this,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<JsonObject> call,
                    Throwable t) {

                Toast.makeText(
                        SurveyActivity.this,
                        "Survey API error: "
                                + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private String buildFurnitureString() {

        StringBuilder furniture =
                new StringBuilder();

        if (cbBed.isChecked()) {
            furniture.append("Bed, ");
        }

        if (cbWardrobe.isChecked()) {
            furniture.append("Wardrobe, ");
        }

        if (cbStudyTable.isChecked()) {
            furniture.append("Study Table, ");
        }

        if (cbChair.isChecked()) {
            furniture.append("Chair, ");
        }

        if (cbSofa.isChecked()) {
            furniture.append("Sofa, ");
        }

        if (cbTvUnit.isChecked()) {
            furniture.append("TV Unit, ");
        }

        if (cbOthers.isChecked()) {
            furniture.append("Others, ");
        }

        if (furniture.length() == 0) {
            return "None";
        }

        return furniture
                .toString()
                .replaceAll(", $", "");
    }

    private void openImageUpload(
            long projectId
    ) {

        Intent intent =
                new Intent(
                        SurveyActivity.this,
                        ImageUploadActivity.class
                );

        intent.putExtra(
                "USER_ID",
                userId
        );

        intent.putExtra(
                "PROJECT_ID",
                projectId
        );

        if (roomImageUri != null
                && !roomImageUri.isEmpty()) {

            intent.putExtra(
                    "room_image_uri",
                    roomImageUri
            );
        }

        intent.putExtra(
                "room_type",
                spinnerRoomType
                        .getSelectedItem()
                        .toString()
        );

        intent.putExtra(
                "room_length",
                etRoomLength.getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "room_width",
                etRoomWidth.getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "ceiling_height",
                etCeilingHeight.getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "doors",
                etDoors.getText()
                        .toString()
                        .trim()
        );

        intent.putExtra(
                "windows",
                etWindows.getText()
                        .toString()
                        .trim()
        );

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

        intent.putExtra(
                "budget",
                etBudget.getText()
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

        intent.putExtra(
                "completion",
                spinnerCompletion
                        .getSelectedItem()
                        .toString()
        );

        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}