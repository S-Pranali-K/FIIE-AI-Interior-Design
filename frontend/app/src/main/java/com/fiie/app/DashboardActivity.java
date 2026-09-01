package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnNewDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        btnNewDesign = findViewById(R.id.btnNewDesign);

        btnNewDesign.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    NewProjectActivity.class
            );

            startActivity(intent);
        });
    }
}