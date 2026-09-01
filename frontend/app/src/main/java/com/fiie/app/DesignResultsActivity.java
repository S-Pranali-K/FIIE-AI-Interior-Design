package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DesignResultsActivity extends AppCompatActivity {

    private Button btnViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design_results);

        btnViewDetails = findViewById(R.id.btnViewDetails);

        btnViewDetails.setOnClickListener(v -> {

            Toast.makeText(
                    DesignResultsActivity.this,
                    "Design details will open here",
                    Toast.LENGTH_SHORT
            ).show();

        });
    }
}