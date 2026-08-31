package com.fiie.app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvAlreadyAccount;


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

        if (name.isEmpty()) {
            etName.setError("Full name is required");
            etName.requestFocus();
            return;
        }

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

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        Toast.makeText(
                RegisterActivity.this,
                "Registration details validated successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}