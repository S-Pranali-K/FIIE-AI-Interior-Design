package com.fiie.app;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup rgTheme;
    private RadioButton rbSystem;
    private RadioButton rbLight;
    private RadioButton rbDark;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        rgTheme = findViewById(R.id.rgTheme);

        rbSystem = findViewById(R.id.rbSystem);
        rbLight = findViewById(R.id.rbLight);
        rbDark = findViewById(R.id.rbDark);

        btnBack.setOnClickListener(v -> finish());

        /*
         * Load the currently saved appearance.
         */
        loadSelectedTheme();

        /*
         * Change appearance when the user selects
         * System Default, Light, or Dark.
         */
        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbSystem) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.SYSTEM
                );

            } else if (checkedId == R.id.rbLight) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.LIGHT
                );

            } else if (checkedId == R.id.rbDark) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.DARK
                );
            }

            /*
             * Recreate SettingsActivity so all views are
             * immediately inflated using the new theme.
             */
            recreate();
        });
    }

    private void loadSelectedTheme() {

        String theme =
                ThemeManager.getSavedTheme(this);

        /*
         * Temporarily prevent the listener from reacting
         * while restoring the saved radio selection.
         */
        rgTheme.setOnCheckedChangeListener(null);

        if (ThemeManager.LIGHT.equals(theme)) {

            rbLight.setChecked(true);

        } else if (ThemeManager.DARK.equals(theme)) {

            rbDark.setChecked(true);

        } else {

            rbSystem.setChecked(true);
        }

        /*
         * Restore the listener after setting the selection.
         */
        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbSystem) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.SYSTEM
                );

            } else if (checkedId == R.id.rbLight) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.LIGHT
                );

            } else if (checkedId == R.id.rbDark) {

                ThemeManager.saveTheme(
                        SettingsActivity.this,
                        ThemeManager.DARK
                );
            }

            recreate();
        });
    }
}