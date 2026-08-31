package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvAlreadyAccount;

    // YOUR LAPTOP IP ADDRESS
    private static final String REGISTER_URL =
            "http://10.163.120.75:8080/api/auth/register";

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

        btnRegister.setOnClickListener(v -> validateRegistration());

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

        // Validate full name
        if (name.isEmpty()) {
            etName.setError("Full name is required");
            etName.requestFocus();
            return;
        }

        // Validate email
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

        // Validate password
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

        // Validate confirm password
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

        // All validation successful.
        // Send name, email and password to Spring Boot.
        registerUser(name, email, password);
    }

    private void registerUser(
            String name,
            String email,
            String password) {

        btnRegister.setEnabled(false);

        new Thread(() -> {

            HttpURLConnection connection = null;

            try {

                URL url = new URL(REGISTER_URL);

                connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");

                connection.setRequestProperty(
                        "Content-Type",
                        "application/json"
                );

                connection.setRequestProperty(
                        "Accept",
                        "text/plain"
                );

                connection.setDoOutput(true);

                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                // Backend RegisterRequest expects:
                // name, email and password.
                //
                // Confirm password is NOT sent to backend.
                String json =
                        "{\"name\":\"" + name +
                                "\",\"email\":\"" + email +
                                "\",\"password\":\"" + password + "\"}";

                OutputStream outputStream =
                        connection.getOutputStream();

                outputStream.write(
                        json.getBytes("UTF-8")
                );

                outputStream.flush();
                outputStream.close();

                int responseCode =
                        connection.getResponseCode();

                InputStream inputStream;

                if (responseCode >= 200 &&
                        responseCode < 300) {

                    inputStream =
                            connection.getInputStream();

                } else {

                    inputStream =
                            connection.getErrorStream();
                }

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        inputStream
                                )
                        );

                StringBuilder response =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String result =
                        response.toString();

                runOnUiThread(() -> {

                    btnRegister.setEnabled(true);

                    if (responseCode == 200 &&
                            result.equals(
                                    "Registration successful"
                            )) {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Registration successful",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Go to Login screen
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class
                        );

                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(
                                RegisterActivity.this,
                                result.isEmpty()
                                        ? "Registration failed"
                                        : result,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

            } catch (Exception e) {

                runOnUiThread(() -> {

                    btnRegister.setEnabled(true);

                    Toast.makeText(
                            RegisterActivity.this,
                            "Cannot connect to server: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }

        }).start();
    }
}