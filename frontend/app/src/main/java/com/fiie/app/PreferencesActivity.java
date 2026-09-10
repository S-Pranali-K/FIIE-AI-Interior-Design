package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    private Button btnGenerateDesign;
    private Uri roomImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);
        String imageUriString = getIntent().getStringExtra("room_image_uri");

        if (imageUriString != null) {
            roomImageUri = Uri.parse(imageUriString);
        }

        btnGenerateDesign = findViewById(R.id.btnGenerateDesign);

        btnGenerateDesign.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PreferencesActivity.this,
                    SurveyActivity.class
            );

            if (roomImageUri != null) {
                intent.putExtra("room_image_uri", roomImageUri.toString());
            }

            startActivity(intent);
        });
    }
}