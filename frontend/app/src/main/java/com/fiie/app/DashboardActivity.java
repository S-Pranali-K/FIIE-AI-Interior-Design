package com.fiie.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;

public class DashboardActivity extends AppCompatActivity {

    private Button btnNewDesign;

    private View btnProjects;
    private View btnAnalyze;
    private View btnSaved;

    private TextView tvDashboardTitle;
    private TextView tvDashboardSubtitle;

    private long userId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * ---------------------------------------------------------
         * Apply saved theme before loading the dashboard
         * ---------------------------------------------------------
         */
        applySavedTheme();

        setContentView(R.layout.activity_dashboard);

        /*
         * ---------------------------------------------------------
         * Dashboard background
         * ---------------------------------------------------------
         */
        setupDashboardBackground();

        /*
         * ---------------------------------------------------------
         * Get logged-in user's session data
         * ---------------------------------------------------------
         */
        SharedPreferences preferences =
                getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

        userId = preferences.getLong("USER_ID", -1);
        userEmail = preferences.getString("USER_EMAIL", "");

        /*
         * ---------------------------------------------------------
         * Check whether a user is logged in
         * ---------------------------------------------------------
         */
        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Session expired. Please login again.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(
                    DashboardActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();
            return;
        }

        /*
         * ---------------------------------------------------------
         * Connect dashboard views
         * ---------------------------------------------------------
         */
        tvDashboardTitle =
                findViewById(R.id.tvDashboardTitle);

        tvDashboardSubtitle =
                findViewById(R.id.tvDashboardSubtitle);

        btnNewDesign =
                findViewById(R.id.btnNewDesign);

        /*
         * Quick action cards
         */
        btnProjects =
                findViewById(R.id.glassCardProjects);

        btnAnalyze =
                findViewById(R.id.glassCardAnalyze);

        btnSaved =
                findViewById(R.id.glassCardSaved);

        /*
         * ---------------------------------------------------------
         * Display logged-in user information
         * ---------------------------------------------------------
         */
        setupUserHeader();

        /*
         * ---------------------------------------------------------
         * Start New Design
         * ---------------------------------------------------------
         */
        if (btnNewDesign != null) {

            btnNewDesign.setOnClickListener(v -> {

                Intent intent = new Intent(
                        DashboardActivity.this,
                        NewProjectActivity.class
                );

                intent.putExtra("USER_ID", userId);

                startActivity(intent);
            });
        }

        /*
         * ---------------------------------------------------------
         * Quick Action — Projects
         * ---------------------------------------------------------
         */
        if (btnProjects != null) {

            btnProjects.setOnClickListener(v -> {

                Toast.makeText(
                        DashboardActivity.this,
                        "Your projects will appear here.",
                        Toast.LENGTH_SHORT
                ).show();
            });
        }

        /*
         * ---------------------------------------------------------
         * Quick Action — Analyze
         * ---------------------------------------------------------
         */
        if (btnAnalyze != null) {

            btnAnalyze.setOnClickListener(v -> {

                Intent intent = new Intent(
                        DashboardActivity.this,
                        AIAnalysisActivity.class
                );

                intent.putExtra("USER_ID", userId);

                startActivity(intent);
            });
        }

        /*
         * ---------------------------------------------------------
         * Quick Action — Saved
         * ---------------------------------------------------------
         */
        if (btnSaved != null) {

            btnSaved.setOnClickListener(v -> {

                Toast.makeText(
                        DashboardActivity.this,
                        "Saved designs will appear here.",
                        Toast.LENGTH_SHORT
                ).show();
            });
        }

        /*
         * ---------------------------------------------------------
         * Header Menu
         * ---------------------------------------------------------
         */
        setupMenu();

        /*
         * ---------------------------------------------------------
         * Search
         * ---------------------------------------------------------
         */
        setupSearch();

        /*
         * ---------------------------------------------------------
         * Bottom Navigation
         * ---------------------------------------------------------
         */
        setupBottomNavigation();
    }


    // =============================================================
    // THEME
    // =============================================================

    private void applySavedTheme() {

        SharedPreferences preferences =
                getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

        String theme =
                preferences.getString("APP_THEME", "system");

        switch (theme) {

            case "light":
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
                break;

            case "dark":
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );
                break;

            default:
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                );
                break;
        }
    }


    private void showThemeMenu(View anchor) {

        PopupMenu popupMenu =
                new PopupMenu(this, anchor);

        popupMenu.getMenu().add("Light");
        popupMenu.getMenu().add("Dark");
        popupMenu.getMenu().add("System default");

        popupMenu.setOnMenuItemClickListener(item -> {

            String selectedTheme =
                    item.getTitle().toString();

            SharedPreferences preferences =
                    getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

            if (selectedTheme.equals("Light")) {

                preferences.edit()
                        .putString("APP_THEME", "light")
                        .apply();

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );

            } else if (selectedTheme.equals("Dark")) {

                preferences.edit()
                        .putString("APP_THEME", "dark")
                        .apply();

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );

            } else {

                preferences.edit()
                        .putString("APP_THEME", "system")
                        .apply();

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                );
            }

            return true;
        });

        popupMenu.show();
    }


    // =============================================================
    // MENU
    // =============================================================

    private void setupMenu() {

        View btnMenu =
                findViewById(R.id.btnMenu);

        if (btnMenu == null) {
            return;
        }

        btnMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(
                            DashboardActivity.this,
                            btnMenu
                    );

            popupMenu.getMenu().add("New Design");
            popupMenu.getMenu().add("Projects");
            popupMenu.getMenu().add("Saved Designs");
            popupMenu.getMenu().add("Theme");
            popupMenu.getMenu().add("Settings");

            popupMenu.setOnMenuItemClickListener(item -> {

                String selected =
                        item.getTitle().toString();

                switch (selected) {

                    case "New Design":

                        Intent newDesignIntent =
                                new Intent(
                                        DashboardActivity.this,
                                        NewProjectActivity.class
                                );

                        newDesignIntent.putExtra(
                                "USER_ID",
                                userId
                        );

                        startActivity(newDesignIntent);

                        return true;


                    case "Projects":

                        Toast.makeText(
                                DashboardActivity.this,
                                "Your projects will appear here.",
                                Toast.LENGTH_SHORT
                        ).show();

                        return true;


                    case "Saved Designs":

                        Toast.makeText(
                                DashboardActivity.this,
                                "Saved designs will appear here.",
                                Toast.LENGTH_SHORT
                        ).show();

                        return true;


                    case "Theme":

                        showThemeMenu(btnMenu);

                        return true;


                    case "Settings":

                        Intent settingsIntent =
                                new Intent(
                                        DashboardActivity.this,
                                        SettingsActivity.class
                                );

                        startActivity(settingsIntent);

                        return true;


                    default:

                        return false;
                }
            });

            popupMenu.show();
        });
    }


    // =============================================================
    // SEARCH
    // =============================================================

    private void setupSearch() {

        View searchBar =
                findViewById(R.id.searchBar);

        if (searchBar == null) {
            return;
        }

        searchBar.setOnClickListener(v -> {

            final EditText searchInput =
                    new EditText(DashboardActivity.this);

            searchInput.setSingleLine(true);
            searchInput.setHint(
                    "Search your designs..."
            );

            searchInput.setPadding(
                    20,
                    0,
                    20,
                    0
            );

            /*
             * Use a simple dialog so the existing
             * dashboard XML remains unchanged.
             */
            androidx.appcompat.app.AlertDialog dialog =
                    new androidx.appcompat.app.AlertDialog.Builder(
                            DashboardActivity.this
                    )
                            .setTitle("Search FIIE")
                            .setView(searchInput)
                            .setNegativeButton(
                                    "Cancel",
                                    null
                            )
                            .setPositiveButton(
                                    "Search",
                                    null
                            )
                            .create();

            dialog.setOnShowListener(d -> {

                dialog.getButton(
                        androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE
                ).setOnClickListener(button -> {

                    String query =
                            searchInput
                                    .getText()
                                    .toString()
                                    .trim();

                    if (query.isEmpty()) {

                        Toast.makeText(
                                DashboardActivity.this,
                                "Please enter something to search.",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    performDashboardSearch(query);

                    dialog.dismiss();
                });

                searchInput.requestFocus();

                dialog.getWindow()
                        .setSoftInputMode(
                                android.view.WindowManager.LayoutParams
                                        .SOFT_INPUT_STATE_ALWAYS_VISIBLE
                        );
            });

            dialog.show();
        });
    }


    private void performDashboardSearch(String query) {

        String search =
                query.toLowerCase().trim();

        if (search.contains("new") ||
                search.contains("design")) {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            NewProjectActivity.class
                    );

            intent.putExtra(
                    "USER_ID",
                    userId
            );

            startActivity(intent);

            return;
        }

        if (search.contains("analy")) {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            AIAnalysisActivity.class
                    );

            intent.putExtra(
                    "USER_ID",
                    userId
            );

            startActivity(intent);

            return;
        }

        if (search.contains("project")) {

            Toast.makeText(
                    this,
                    "Your projects will appear here.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (search.contains("saved")) {

            Toast.makeText(
                    this,
                    "Saved designs will appear here.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (search.contains("profile") ||
                search.contains("setting")) {

            Intent intent =
                    new Intent(
                            DashboardActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);

            return;
        }

        Toast.makeText(
                this,
                "No matching FIIE feature found.",
                Toast.LENGTH_SHORT
        ).show();
    }


    // =============================================================
    // DASHBOARD BACKGROUND
    // =============================================================

    private void setupDashboardBackground() {

        ImageView background =
                findViewById(R.id.ivDashboardBackground);

        if (background == null) {
            return;
        }

        /*
         * Android 12+ supports native RenderEffect blur.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            background.setRenderEffect(
                    RenderEffect.createBlurEffect(
                            8f,
                            8f,
                            Shader.TileMode.CLAMP
                    )
            );
        }
    }


    // =============================================================
    // USER HEADER
    // =============================================================

    private void setupUserHeader() {

        if (tvDashboardTitle == null) {
            return;
        }

        SharedPreferences preferences =
                getSharedPreferences("FIIE_PREFS", MODE_PRIVATE);

        String userName =
                preferences.getString("USER_NAME", "");

        if (userName != null &&
                !userName.trim().isEmpty()) {

            tvDashboardTitle.setText(
                    "Welcome back, " + userName
            );

        } else if (userEmail != null &&
                !userEmail.trim().isEmpty()) {

            String emailName = userEmail;

            int atIndex =
                    emailName.indexOf("@");

            if (atIndex > 0) {

                emailName =
                        emailName.substring(
                                0,
                                atIndex
                        );
            }

            tvDashboardTitle.setText(
                    "Welcome back, " + emailName
            );

        } else {

            tvDashboardTitle.setText(
                    "Welcome back"
            );
        }

        if (tvDashboardSubtitle != null) {

            tvDashboardSubtitle.setText(
                    "Let's create a functional and personalized space."
            );
        }
    }


    // =============================================================
    // BOTTOM NAVIGATION
    // =============================================================

    private void setupBottomNavigation() {

        View navHome =
                findViewById(R.id.navHome);

        View navAnalyze =
                findViewById(R.id.navAnalyze);

        View navCreate =
                findViewById(R.id.navCreate);

        View navSaved =
                findViewById(R.id.navSaved);

        View navProfile =
                findViewById(R.id.navProfile);


        // ---------------------------------------------------------
        // HOME
        // ---------------------------------------------------------

        if (navHome != null) {

            navHome.setOnClickListener(v -> {

                // Already on Dashboard.
            });
        }


        // ---------------------------------------------------------
        // ANALYZE
        // ---------------------------------------------------------

        if (navAnalyze != null) {

            navAnalyze.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                DashboardActivity.this,
                                AIAnalysisActivity.class
                        );

                intent.putExtra(
                        "USER_ID",
                        userId
                );

                startActivity(intent);
            });
        }


        // ---------------------------------------------------------
        // CREATE
        // ---------------------------------------------------------

        if (navCreate != null) {

            navCreate.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                DashboardActivity.this,
                                NewProjectActivity.class
                        );

                intent.putExtra(
                        "USER_ID",
                        userId
                );

                startActivity(intent);
            });
        }


        // ---------------------------------------------------------
        // SAVED
        // ---------------------------------------------------------

        if (navSaved != null) {

            navSaved.setOnClickListener(v -> {

                Toast.makeText(
                        DashboardActivity.this,
                        "Saved designs will appear here.",
                        Toast.LENGTH_SHORT
                ).show();
            });
        }


        // ---------------------------------------------------------
        // PROFILE → SETTINGS
        // ---------------------------------------------------------

        if (navProfile != null) {

            navProfile.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                DashboardActivity.this,
                                SettingsActivity.class
                        );

                startActivity(intent);
            });
        }
    }
}