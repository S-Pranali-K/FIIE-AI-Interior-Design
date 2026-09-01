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

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvAlreadyAccount;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvAlreadyAccount = findViewById(R.id.tvAlreadyAccount);

        // Connect Retrofit to backend API
        apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        // Register button
        btnRegister.setOnClickListener(v -> validateRegistration());

        // Already have account -> Login
        tvAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(
                    RegisterActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();
        });
    }

    private void validateRegistration() {

        String name = etName.getText().toString().trim();
        String email = etRegisterEmail.getText().toString().trim();
        String password = etRegisterPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Name validation
        if (name.isEmpty()) {
            etName.setError("Full name is required");
            etName.requestFocus();
            return;
        }

        // Email validation
        if (email.isEmpty()) {
            etRegisterEmail.setError("Email is required");
            etRegisterEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRegisterEmail.setError("Enter a valid email address");
            etRegisterEmail.requestFocus();
            return;
        }

        // Password validation
        if (password.isEmpty()) {
            etRegisterPassword.setError("Password is required");
            etRegisterPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etRegisterPassword.setError(
                    "Password must contain at least 6 characters"
            );
            etRegisterPassword.requestFocus();
            return;
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError(
                    "Please confirm your password"
            );
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(
                    "Passwords do not match"
            );
            etConfirmPassword.requestFocus();
            return;
        }

        // Everything is valid -> call backend
        registerToBackend(name, email, password);
    }

    private void registerToBackend(
            String name,
            String email,
            String password
    ) {

        Map<String, String> registerData = new HashMap<>();

        registerData.put("name", name);
        registerData.put("email", email);
        registerData.put("password", password);

        // Prevent multiple clicks
        btnRegister.setEnabled(false);

        Call<String> call = apiService.register(registerData);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(
                    Call<String> call,
                    Response<String> response
            ) {

                btnRegister.setEnabled(true);

                if (response.isSuccessful()) {

                    Toast.makeText(
                            RegisterActivity.this,
                            "Registration successful",
                            Toast.LENGTH_SHORT
                    ).show();

                    // IMPORTANT:
                    // Redirect to Login AFTER successful backend response
                    Intent intent = new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

                    startActivity(intent);
                    finish();

                } else {

                    String errorMessage = "Registration failed";

                    try {
                        if (response.errorBody() != null) {
                            errorMessage =
                                    response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(
                            RegisterActivity.this,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<String> call,
                    Throwable t
            ) {

                btnRegister.setEnabled(true);

                Toast.makeText(
                        RegisterActivity.this,
                        "Connection failed: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}