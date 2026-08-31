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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private static final String API_URL =
            "http://10.163.120.75:8080/api/auth/login";

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

        loginUser(email, password);
    }

    private void loginUser(String email, String password) {

        btnLogin.setEnabled(false);

        new Thread(() -> {

            HttpURLConnection connection = null;

            try {

                URL url = new URL(API_URL);

                connection = (HttpURLConnection) url.openConnection();

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

                String json =
                        "{\"email\":\"" + email +
                                "\",\"password\":\"" + password + "\"}";

                OutputStream outputStream =
                        connection.getOutputStream();

                outputStream.write(json.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseCode =
                        connection.getResponseCode();

                InputStream inputStream;

                if (responseCode >= 200 && responseCode < 300) {
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(inputStream)
                        );

                StringBuilder response =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String result = response.toString();

                runOnUiThread(() -> {

                    btnLogin.setEnabled(true);

                    if (responseCode == 200 &&
                            result.equals("Login successful")) {

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
                                result.isEmpty()
                                        ? "Login failed"
                                        : result,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

            } catch (Exception e) {

                runOnUiThread(() -> {

                    btnLogin.setEnabled(true);

                    Toast.makeText(
                            LoginActivity.this,
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