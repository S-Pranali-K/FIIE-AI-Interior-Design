package com.fiie.app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public final class ThemeManager {

    private static final String PREFS_NAME = "FIIE_PREFS";
    private static final String KEY_THEME = "APP_THEME";

    public static final String SYSTEM = "system";
    public static final String LIGHT = "light";
    public static final String DARK = "dark";

    private ThemeManager() {
        // Prevent object creation
    }

    public static void applySavedTheme(Context context) {

        SharedPreferences preferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String theme =
                preferences.getString(KEY_THEME, SYSTEM);

        applyTheme(theme);
    }

    public static void applyTheme(String theme) {

        if (LIGHT.equals(theme)) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );

        } else if (DARK.equals(theme)) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );

        } else {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            );
        }
    }

    public static void saveTheme(Context context, String theme) {

        context.getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                ).edit()
                .putString(KEY_THEME, theme)
                .apply();

        applyTheme(theme);
    }

    public static String getSavedTheme(Context context) {

        return context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
        ).getString(KEY_THEME, SYSTEM);
    }
}