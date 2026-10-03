package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> validateLogin());

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });
    }

    private void validateLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(
                    "Password must contain at least 6 characters"
            );
            etPassword.requestFocus();
            return;
        }

        loginLocally(email);
    }

    private void loginLocally(String email) {

        // Temporary frontend-only user ID.
        // This will be replaced by the real backend User ID
        // when backend integration is restored.
        long demoUserId = 1;

        SharedPreferences preferences =
                getSharedPreferences(
                        "FIIE_PREFS",
                        MODE_PRIVATE
                );

        preferences.edit()
                .putLong("USER_ID", demoUserId)
                .putString("USER_EMAIL", email)
                .putBoolean("IS_LOGGED_IN", true)
                .apply();

        Toast.makeText(
                LoginActivity.this,
                "Login successful",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(
                LoginActivity.this,
                DashboardActivity.class
        );

        startActivity(intent);
        finish();
    }

}
