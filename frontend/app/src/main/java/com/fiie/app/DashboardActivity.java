package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnNewDesign;

    private long userId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        // Get logged-in user's session data
        SharedPreferences preferences =
                getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

        userId = preferences.getLong("USER_ID", -1);
        userEmail = preferences.getString("USER_EMAIL", "");

        // Check whether a user is logged in
        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Session expired. Please login again.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);
            finish();
            return;
        }

        // Temporary verification
        Toast.makeText(
                this,
                "Logged in User ID: " + userId +
                        "\nEmail: " + userEmail,
                Toast.LENGTH_LONG
        ).show();

        btnNewDesign = findViewById(R.id.btnNewDesign);

        btnNewDesign.setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    NewProjectActivity.class
            );

            // Pass logged-in user ID to New Project screen
            intent.putExtra("USER_ID", userId);

            startActivity(intent);
        });
    }
}