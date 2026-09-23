package com.fiie.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fiie.app.network.ApiService;
import com.fiie.app.network.RetrofitClient;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private long projectId;

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

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_upload);

        // -----------------------------------------
        // Initialize Views
        // -----------------------------------------

        ivRoomImage = findViewById(R.id.ivRoomImage);
        tvImageStatus = findViewById(R.id.tvImageStatus);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnContinueImage = findViewById(R.id.btnContinueImage);

        // -----------------------------------------
        // Retrofit API
        // -----------------------------------------

        apiService =
                RetrofitClient
                        .getInstance()
                        .create(ApiService.class);

        // -----------------------------------------
        // Receive Survey Data
        // -----------------------------------------

        receiveSurveyData();

        // -----------------------------------------
        // Validate Project ID
        // -----------------------------------------

        if (projectId == -1) {

            Toast.makeText(
                    this,
                    "Project ID missing. Please create the project again.",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

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
        // Continue
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
        // User ID
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
                intent.getLongExtra(
                        "PROJECT_ID",
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
        // Vastu
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

        // -----------------------------------------
        // Validate Image
        // -----------------------------------------

        if (selectedImageUri == null) {

            Toast.makeText(
                    this,
                    "Please select a room image first.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // -----------------------------------------
        // Validate Project
        // -----------------------------------------

        if (projectId == -1) {

            Toast.makeText(
                    this,
                    "Project ID is missing.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // -----------------------------------------
        // Disable button during upload
        // -----------------------------------------

        btnContinueImage.setEnabled(false);

        tvImageStatus.setText(
                "Uploading image..."
        );

        Toast.makeText(
                this,
                "Uploading image...",
                Toast.LENGTH_SHORT
        ).show();

        // -----------------------------------------
        // Upload Image
        // -----------------------------------------

        uploadImage();
    }


    // =========================================
    // UPLOAD IMAGE TO BACKEND
    // =========================================

    private void uploadImage() {

        try {

            // -----------------------------------------
            // Convert URI to File
            // -----------------------------------------

            File imageFile =
                    createFileFromUri(
                            selectedImageUri
                    );

            if (imageFile == null) {

                btnContinueImage.setEnabled(true);

                Toast.makeText(
                        this,
                        "Unable to read selected image.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            // -----------------------------------------
            // Request Body
            // -----------------------------------------

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(
                                    getContentResolver()
                                            .getType(selectedImageUri)
                            ),
                            imageFile
                    );

            // -----------------------------------------
            // Multipart
            // -----------------------------------------

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData(
                            "file",
                            imageFile.getName(),
                            requestFile
                    );

            // -----------------------------------------
            // API Call
            // -----------------------------------------

            Call<JsonObject> call =
                    apiService.uploadRoomImage(
                            projectId,
                            body
                    );

            call.enqueue(
                    new Callback<JsonObject>() {

                        @Override
                        public void onResponse(
                                Call<JsonObject> call,
                                Response<JsonObject> response
                        ) {

                            btnContinueImage.setEnabled(true);

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                tvImageStatus.setText(
                                        "Image uploaded successfully"
                                );

                                Toast.makeText(
                                        ImageUploadActivity.this,
                                        "Image uploaded successfully.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                // -----------------------------------------
                                // Open AI Analysis
                                // -----------------------------------------

                                openAIAnalysis();

                            } else {

                                Toast.makeText(
                                        ImageUploadActivity.this,
                                        "Image upload failed. HTTP "
                                                + response.code(),
                                        Toast.LENGTH_LONG
                                ).show();

                                tvImageStatus.setText(
                                        "Image upload failed"
                                );
                            }
                        }


                        @Override
                        public void onFailure(
                                Call<JsonObject> call,
                                Throwable t
                        ) {

                            btnContinueImage.setEnabled(true);

                            tvImageStatus.setText(
                                    "Image upload failed"
                            );

                            Toast.makeText(
                                    ImageUploadActivity.this,
                                    "Upload error: "
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );

        } catch (Exception e) {

            btnContinueImage.setEnabled(true);

            Toast.makeText(
                    this,
                    "Error preparing image: "
                            + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }


    // =========================================
    // CREATE FILE FROM URI
    // =========================================

    private File createFileFromUri(Uri uri)
            throws Exception {

        String fileName =
                getFileName(uri);

        if (fileName == null
                || fileName.isEmpty()) {

            fileName =
                    "room_image.jpg";
        }

        File file =
                new File(
                        getCacheDir(),
                        fileName
                );

        InputStream inputStream =
                getContentResolver()
                        .openInputStream(uri);

        if (inputStream == null) {

            return null;
        }

        FileOutputStream outputStream =
                new FileOutputStream(file);

        byte[] buffer =
                new byte[4096];

        int bytesRead;

        while (
                (bytesRead =
                        inputStream.read(buffer))
                        != -1
        ) {

            outputStream.write(
                    buffer,
                    0,
                    bytesRead
            );
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return file;
    }


    // =========================================
    // GET FILE NAME
    // =========================================

    private String getFileName(Uri uri) {

        String result = null;

        if ("content".equals(
                uri.getScheme()
        )) {

            Cursor cursor =
                    getContentResolver().query(
                            uri,
                            null,
                            null,
                            null,
                            null
                    );

            if (cursor != null) {

                try {

                    int nameIndex =
                            cursor.getColumnIndex(
                                    OpenableColumns.DISPLAY_NAME
                            );

                    if (nameIndex >= 0
                            && cursor.moveToFirst()) {

                        result =
                                cursor.getString(
                                        nameIndex
                                );
                    }

                } finally {

                    cursor.close();
                }
            }
        }

        if (result == null) {

            result =
                    uri.getPath();

            if (result != null) {

                int cut =
                        result.lastIndexOf('/');

                if (cut != -1) {

                    result =
                            result.substring(
                                    cut + 1
                            );
                }
            }
        }

        return result;
    }


    // =========================================
    // OPEN AI ANALYSIS
    // =========================================

    private void openAIAnalysis() {

        Intent intent =
                new Intent(
                        ImageUploadActivity.this,
                        AIAnalysisActivity.class
                );

        // -----------------------------------------
        // User
        // -----------------------------------------

        intent.putExtra(
                "USER_ID",
                userId
        );

        // -----------------------------------------
        // Project
        // -----------------------------------------

        intent.putExtra(
                "PROJECT_ID",
                projectId
        );

        // -----------------------------------------
        // Image
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
        // Furniture
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
        // Start AI Analysis
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