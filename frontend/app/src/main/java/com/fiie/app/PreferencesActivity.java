package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    private Button btnGenerateDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        btnGenerateDesign = findViewById(R.id.btnGenerateDesign);

        btnGenerateDesign.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PreferencesActivity.this,
                    SurveyActivity.class
            );

            startActivity(intent);
        });
    }
}