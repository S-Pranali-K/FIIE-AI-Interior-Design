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

import com.fiie.app.network.ApiService;
import com.fiie.app.network.RetrofitClient;
import com.google.gson.JsonObject;

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
            etPassword.setError("Password must contain at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        loginToBackend(email, password);
    }

    private void loginToBackend(String email, String password) {

        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        Call<JsonObject> call = apiService.login(loginData);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(
                    Call<JsonObject> call,
                    Response<JsonObject> response) {

                if (response.isSuccessful() && response.body() != null) {

                    JsonObject result = response.body();

                    // Backend must return the real user ID
                    if (!result.has("id")
                            || result.get("id").isJsonNull()) {

                        Toast.makeText(
                                LoginActivity.this,
                                "Login succeeded, but User ID was not returned",
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    long userId;

                    try {
                        userId = result.get("id").getAsLong();
                    } catch (Exception e) {

                        Toast.makeText(
                                LoginActivity.this,
                                "Invalid User ID received from server",
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    // Save the logged-in user's session
                    SharedPreferences preferences =
                            getSharedPreferences(
                                    "FIIE_PREFS",
                                    MODE_PRIVATE
                            );

                    preferences.edit()
                            .putLong("USER_ID", userId)
                            .putString("USER_EMAIL", email)
                            .apply();

                    Toast.makeText(
                            LoginActivity.this,
                            "Login successful. User ID: " + userId,
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent = new Intent(
                            LoginActivity.this,
                            DashboardActivity.class
                    );

                    startActivity(intent);
                    finish();

                } else {

                    String errorMessage =
                            "Login failed. HTTP " + response.code();

                    try {
                        if (response.errorBody() != null) {
                            String serverError =
                                    response.errorBody().string();

                            if (!serverError.isEmpty()) {
                                errorMessage += "\n" + serverError;
                            }
                        }
                    } catch (Exception e) {
                        errorMessage += "\nCould not read server error";
                    }

                    Toast.makeText(
                            LoginActivity.this,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<JsonObject> call,
                    Throwable t) {

                Toast.makeText(
                        LoginActivity.this,
                        "Connection failed: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}