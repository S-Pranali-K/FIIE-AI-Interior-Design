package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fiie.app.network.ApiService;
import com.fiie.app.network.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Create Retrofit API service
        apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

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
            etPassword.setError("Password must contain at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // Call backend
        loginToBackend(email, password);
    }

    private void loginToBackend(String email, String password) {

        Map<String, String> loginData = new HashMap<>();

        loginData.put("email", email);
        loginData.put("password", password);

        btnLogin.setEnabled(false);

        Call<String> call = apiService.login(loginData);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(
                    Call<String> call,
                    Response<String> response
            ) {

                btnLogin.setEnabled(true);

                if (response.isSuccessful()) {

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

                } else {

                    Toast.makeText(
                            LoginActivity.this,
                            "Login failed. Check email and password.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<String> call,
                    Throwable t
            ) {

                btnLogin.setEnabled(true);

                Toast.makeText(
                        LoginActivity.this,
                        "Connection failed: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}