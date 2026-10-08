package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private TextView tvProfileUserId;

    private TextView tvProfileNameInfo;
    private TextView tvProfileEmailInfo;

    private LinearLayout layoutEditProfile;
    private LinearLayout layoutSettings;
    private LinearLayout layoutChangePassword;

    private SharedPreferences preferences;

    // ==================== FIIE COLORS ====================

    private final int COLOR_CREAM = Color.rgb(255, 249, 243);
    private final int COLOR_INPUT = Color.rgb(248, 241, 234);
    private final int COLOR_BORDER = Color.rgb(216, 197, 181);
    private final int COLOR_TEXT = Color.rgb(47, 42, 38);
    private final int COLOR_HINT = Color.rgb(117, 109, 101);
    private final int COLOR_BUTTON = Color.rgb(142, 111, 90);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        // ==================== PROFILE VIEWS ====================

        tvProfileName =
                findViewById(R.id.tvProfileName);

        tvProfileEmail =
                findViewById(R.id.tvProfileEmail);

        tvProfileUserId =
                findViewById(R.id.tvProfileUserId);

        tvProfileNameInfo =
                findViewById(R.id.tvProfileNameInfo);

        tvProfileEmailInfo =
                findViewById(R.id.tvProfileEmailInfo);

        // ==================== ACCOUNT BUTTONS ====================

        layoutEditProfile =
                findViewById(R.id.layoutEditProfile);

        layoutSettings =
                findViewById(R.id.layoutSettings);

        layoutChangePassword =
                findViewById(R.id.layoutChangePassword);

        // ==================== SHARED PREFERENCES ====================

        preferences =
                getSharedPreferences(
                        "FIIE_PREFS",
                        MODE_PRIVATE
                );

        // ==================== LOAD USER DATA ====================

        loadUserData();

        // ==================== BACK BUTTON ====================

        findViewById(R.id.btnProfileBack)
                .setOnClickListener(v -> finish());

        // ==================== EDIT PROFILE ====================

        layoutEditProfile.setOnClickListener(
                v -> showEditProfileDialog()
        );

        // ==================== SETTINGS ====================

        layoutSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        // ==================== CHANGE PASSWORD ====================

        layoutChangePassword.setOnClickListener(
                v -> showChangePasswordDialog()
        );
    }

    // ============================================================
    // LOAD USER DATA
    // ============================================================

    private void loadUserData() {

        String userName =
                preferences.getString(
                        "USER_NAME",
                        "User"
                );

        String userEmail =
                preferences.getString(
                        "USER_EMAIL",
                        "Not available"
                );

        long userId =
                preferences.getLong(
                        "USER_ID",
                        -1
                );

        tvProfileName.setText(userName);
        tvProfileEmail.setText(userEmail);

        tvProfileNameInfo.setText(userName);
        tvProfileEmailInfo.setText(userEmail);

        tvProfileUserId.setText(
                userId != -1
                        ? String.valueOf(userId)
                        : "Not available"
        );
    }

    // ============================================================
    // CREATE FIIE DIALOG TITLE
    // ============================================================

    private TextView createDialogTitle(String title) {

        TextView textView =
                new TextView(this);

        textView.setText(title);

        textView.setTextSize(22);

        textView.setTextColor(COLOR_TEXT);

        textView.setGravity(Gravity.CENTER_VERTICAL);

        textView.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

        textView.setPadding(
                0,
                10,
                0,
                20
        );

        return textView;
    }

    // ============================================================
    // STYLE EDIT TEXT
    // ============================================================

    private void styleEditText(EditText editText) {

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(COLOR_INPUT);

        background.setCornerRadius(
                14 * getResources()
                        .getDisplayMetrics()
                        .density
        );

        background.setStroke(
                1,
                COLOR_BORDER
        );

        editText.setBackground(background);

        editText.setTextColor(COLOR_TEXT);

        editText.setHintTextColor(COLOR_HINT);

        editText.setTextSize(15);

        editText.setPadding(
                20,
                0,
                20,
                0);

        editText.setSingleLine(true);
    }

    // ============================================================
    // ADD INPUT FIELD
    // ============================================================

    private void addInputField(
            LinearLayout container,
            EditText editText,
            int bottomMargin
    ) {

        styleEditText(editText);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        54 * (int) getResources()
                                .getDisplayMetrics()
                                .density
                );

        params.bottomMargin =
                bottomMargin;

        container.addView(
                editText,
                params
        );
    }

    // ============================================================
    // STYLE DIALOG WINDOW
    // ============================================================

    private void styleDialogWindow(
            AlertDialog dialog
    ) {

        dialog.show();

        if (dialog.getWindow() != null) {

            dialog.getWindow()
                    .setBackgroundDrawable(
                            new ColorDrawable(
                                    Color.TRANSPARENT
                            )
                    );

            int screenWidth =
                    getResources()
                            .getDisplayMetrics()
                            .widthPixels;

            dialog.getWindow()
                    .setLayout(
                            (int) (screenWidth * 0.88),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
        }

        dialog.getButton(
                AlertDialog.BUTTON_POSITIVE
        ).setTextColor(COLOR_BUTTON);

        dialog.getButton(
                AlertDialog.BUTTON_NEGATIVE
        ).setTextColor(COLOR_HINT);

        dialog.getButton(
                AlertDialog.BUTTON_POSITIVE
        ).setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );
    }

    // ============================================================
    // EDIT PROFILE
    // ============================================================

    private void showEditProfileDialog() {

        LinearLayout container =
                new LinearLayout(this);

        container.setOrientation(
                LinearLayout.VERTICAL
        );

        container.setPadding(
                32,
                24,
                32,
                8
        );

        GradientDrawable containerBackground =
                new GradientDrawable();

        containerBackground.setColor(
                COLOR_CREAM
        );

        containerBackground.setCornerRadius(
                28 * getResources()
                        .getDisplayMetrics()
                        .density
        );

        containerBackground.setStroke(
                1,
                COLOR_BORDER
        );

        container.setBackground(
                containerBackground
        );

        // ==================== TITLE ====================

        container.addView(
                createDialogTitle(
                        "Edit Profile"
                )
        );

        // ==================== NAME ====================

        EditText nameInput =
                new EditText(this);

        nameInput.setHint("Full Name");

        nameInput.setText(
                preferences.getString(
                        "USER_NAME",
                        ""
                )
        );

        addInputField(
                container,
                nameInput,
                14
        );

        // ==================== EMAIL ====================

        EditText emailInput =
                new EditText(this);

        emailInput.setHint(
                "Email Address"
        );

        emailInput.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        emailInput.setText(
                preferences.getString(
                        "USER_EMAIL",
                        ""
                )
        );

        addInputField(
                container,
                emailInput,
                4
        );

        // ==================== DIALOG ====================

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setView(container)
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Save",
                                null
                        )
                        .create();

        dialog.setOnShowListener(d -> {

            styleDialogWindow(dialog);

            dialog.getButton(
                    AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener(v -> {

                String newName =
                        nameInput
                                .getText()
                                .toString()
                                .trim();

                String newEmail =
                        emailInput
                                .getText()
                                .toString()
                                .trim();

                if (newName.isEmpty()) {

                    nameInput.setError(
                            "Enter your name"
                    );

                    return;
                }

                if (newEmail.isEmpty()) {

                    emailInput.setError(
                            "Enter your email"
                    );

                    return;
                }

                // Save updated information

                preferences.edit()
                        .putString(
                                "USER_NAME",
                                newName
                        )
                        .putString(
                                "USER_EMAIL",
                                newEmail
                        )
                        .apply();

                // Refresh profile

                loadUserData();

                Toast.makeText(
                        ProfileActivity.this,
                        "Profile updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                dialog.dismiss();
            });
        });

        dialog.show();
    }

    // ============================================================
    // CHANGE PASSWORD
    // ============================================================

    private void showChangePasswordDialog() {

        LinearLayout container =
                new LinearLayout(this);

        container.setOrientation(
                LinearLayout.VERTICAL
        );

        container.setPadding(
                32,
                24,
                32,
                8
        );

        GradientDrawable containerBackground =
                new GradientDrawable();

        containerBackground.setColor(
                COLOR_CREAM
        );

        containerBackground.setCornerRadius(
                28 * getResources()
                        .getDisplayMetrics()
                        .density
        );

        containerBackground.setStroke(
                1,
                COLOR_BORDER
        );

        container.setBackground(
                containerBackground
        );

        // ==================== TITLE ====================

        container.addView(
                createDialogTitle(
                        "Change Password"
                )
        );

        // ==================== CURRENT PASSWORD ====================

        EditText oldPassword =
                new EditText(this);

        oldPassword.setHint(
                "Current Password"
        );

        oldPassword.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        addInputField(
                container,
                oldPassword,
                14
        );

        // ==================== NEW PASSWORD ====================

        EditText newPassword =
                new EditText(this);

        newPassword.setHint(
                "New Password"
        );

        newPassword.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        addInputField(
                container,
                newPassword,
                14
        );

        // ==================== CONFIRM PASSWORD ====================

        EditText confirmPassword =
                new EditText(this);

        confirmPassword.setHint(
                "Confirm New Password"
        );

        confirmPassword.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        addInputField(
                container,
                confirmPassword,
                4
        );

        // ==================== DIALOG ====================

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setView(container)
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .setPositiveButton(
                                "Change",
                                null
                        )
                        .create();

        dialog.setOnShowListener(d -> {

            styleDialogWindow(dialog);

            dialog.getButton(
                    AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener(v -> {

                String oldPass =
                        oldPassword
                                .getText()
                                .toString();

                String newPass =
                        newPassword
                                .getText()
                                .toString();

                String confirmPass =
                        confirmPassword
                                .getText()
                                .toString();

                if (oldPass.isEmpty()) {

                    oldPassword.setError(
                            "Enter current password"
                    );

                    return;
                }

                if (newPass.length() < 6) {

                    newPassword.setError(
                            "Password must contain at least 6 characters"
                    );

                    return;
                }

                if (!newPass.equals(confirmPass)) {

                    confirmPassword.setError(
                            "Passwords do not match"
                    );

                    return;
                }

                /*
                 * Frontend/local password update.
                 *
                 * When the real backend authentication API
                 * is connected, this section should call the
                 * backend change-password API instead.
                 */

                preferences.edit()
                        .putString(
                                "USER_PASSWORD",
                                newPass
                        )
                        .apply();

                Toast.makeText(
                        ProfileActivity.this,
                        "Password changed successfully",
                        Toast.LENGTH_SHORT
                ).show();

                dialog.dismiss();
            });
        });

        dialog.show();
    }
}