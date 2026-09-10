package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewProjectActivity extends AppCompatActivity {

    private Button btnSelectRoomImage;
    private Button btnContinuePreferences;
    private ImageView ivRoomPreview;

    private Uri selectedImageUri;

    private static final int IMAGE_PICKER_REQUEST = 100;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_project);

        // Find views
        btnSelectRoomImage = findViewById(R.id.btnSelectRoomImage);
        btnContinuePreferences = findViewById(R.id.btnContinuePreferences);
        ivRoomPreview = findViewById(R.id.ivRoomPreview);

        // Get logged-in user ID
        SharedPreferences preferences =
                getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

        userId = preferences.getLong("USER_ID", -1);

        // Check session
        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Session expired. Please login again.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent =
                    new Intent(
                            NewProjectActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);
            finish();
            return;
        }

        // Select room image
        btnSelectRoomImage.setOnClickListener(v -> openImagePicker());

        // Continue to Design Preferences
        btnContinuePreferences.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            NewProjectActivity.this,
                            PreferencesActivity.class
                    );

            // Pass only the logged-in user ID.
            // The selected image will be handled later
            // through ImageUploadActivity.
            intent.putExtra("USER_ID", userId);

            startActivity(intent);
        });
    }

    private void openImagePicker() {

        Intent intent =
                new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.setType("image/*");

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        startActivityForResult(
                intent,
                IMAGE_PICKER_REQUEST
        );
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

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

            ivRoomPreview.setImageURI(
                    selectedImageUri
            );

            ivRoomPreview.setVisibility(
                    View.VISIBLE
            );

            Toast.makeText(
                    NewProjectActivity.this,
                    "Room image selected successfully.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}