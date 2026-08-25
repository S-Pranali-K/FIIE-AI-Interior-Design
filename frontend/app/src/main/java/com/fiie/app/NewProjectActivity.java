package com.fiie.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NewProjectActivity extends AppCompatActivity {

    private Button btnSelectRoomImage;
    private Button btnContinuePreferences;
    private ImageView ivRoomPreview;

    private static final int IMAGE_PICKER_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_project);

        btnSelectRoomImage = findViewById(R.id.btnSelectRoomImage);
        btnContinuePreferences = findViewById(R.id.btnContinuePreferences);
        ivRoomPreview = findViewById(R.id.ivRoomPreview);

        btnSelectRoomImage.setOnClickListener(v -> openImagePicker());

        btnContinuePreferences.setOnClickListener(v -> {

            Intent intent = new Intent(
                    NewProjectActivity.this,
                    PreferencesActivity.class
            );

            startActivity(intent);
        });
    }

    private void openImagePicker() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.setType("image/*");

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            Uri imageUri = data.getData();

            ivRoomPreview.setImageURI(imageUri);
            ivRoomPreview.setVisibility(View.VISIBLE);
        }
    }
}