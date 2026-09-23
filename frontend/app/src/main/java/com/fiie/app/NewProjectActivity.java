package com.fiie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewProjectActivity extends AppCompatActivity {

    private Button btnSelectRoomImage;
    private Button btnContinuePreferences;
    private ImageView ivRoomPreview;

    private TextView tvImageStatus;

    private Uri selectedImageUri;

    private static final int IMAGE_PICKER_REQUEST = 100;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_project);

        // ---------------------------------------------------------
        // 1. Find views
        // ---------------------------------------------------------

        btnSelectRoomImage =
                findViewById(R.id.btnSelectRoomImage);

        btnContinuePreferences =
                findViewById(R.id.btnContinuePreferences);

        ivRoomPreview =
                findViewById(R.id.ivRoomPreview);

        /*
         * Optional status text.
         *
         * The XML can contain this view for the redesigned UI.
         * If it is not present, the rest of the screen still works.
         */
        tvImageStatus =
                findViewById(R.id.tvImageStatus);

        // ---------------------------------------------------------
        // 2. Get logged-in user ID
        // ---------------------------------------------------------

        SharedPreferences preferences =
                getSharedPreferences(
                        "FIIE_PREFS",
                        MODE_PRIVATE
                );

        userId =
                preferences.getLong(
                        "USER_ID",
                        -1
                );

        // ---------------------------------------------------------
        // 3. Check session
        // ---------------------------------------------------------

        if (userId == -1) {

            Toast.makeText(
                    this,
                    "Session expired. Please login again.",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent =
                    new Intent(
                            NewProjectActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);
            finish();
            return;
        }

        // ---------------------------------------------------------
        // 4. Initial UI state
        // ---------------------------------------------------------

        if (ivRoomPreview != null) {

            ivRoomPreview.setVisibility(
                    View.GONE
            );
        }

        updateImageStatus();

        // ---------------------------------------------------------
        // 5. Select room image
        // ---------------------------------------------------------

        if (btnSelectRoomImage != null) {

            btnSelectRoomImage.setOnClickListener(
                    v -> openImagePicker()
            );
        }

        // ---------------------------------------------------------
        // 6. Continue to preferences / survey
        // ---------------------------------------------------------

        if (btnContinuePreferences != null) {

            btnContinuePreferences.setOnClickListener(
                    v -> continueToSurvey()
            );
        }
    }

    // =============================================================
    // IMAGE PICKER
    // =============================================================

    private void openImagePicker() {

        Intent intent =
                new Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                );

        intent.setType("image/*");

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        /*
         * Request persistent read permission where supported.
         * This helps the selected URI remain accessible to the
         * next stage of the application.
         */
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        );

        startActivityForResult(
                intent,
                IMAGE_PICKER_REQUEST
        );
    }

    // =============================================================
    // IMAGE PICKER RESULT
    // =============================================================

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode != IMAGE_PICKER_REQUEST) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        if (data == null || data.getData() == null) {
            return;
        }

        selectedImageUri =
                data.getData();

        // ---------------------------------------------------------
        // Persist URI permission when available
        // ---------------------------------------------------------

        try {

            final int takeFlags =
                    data.getFlags()
                            & Intent.FLAG_GRANT_READ_URI_PERMISSION;

            getContentResolver().takePersistableUriPermission(
                    selectedImageUri,
                    takeFlags
            );

        } catch (SecurityException ignored) {

            /*
             * Some document providers do not support
             * persistable permissions. The selected image
             * can still be used during the current flow.
             */
        }

        // ---------------------------------------------------------
        // Display image preview
        // ---------------------------------------------------------

        if (ivRoomPreview != null) {

            ivRoomPreview.setImageURI(
                    selectedImageUri
            );

            ivRoomPreview.setVisibility(
                    View.VISIBLE
            );
        }

        updateImageStatus();

        Toast.makeText(
                NewProjectActivity.this,
                "Room image selected successfully.",
                Toast.LENGTH_SHORT
        ).show();
    }

    // =============================================================
    // IMAGE STATUS
    // =============================================================

    private void updateImageStatus() {

        if (tvImageStatus == null) {
            return;
        }

        if (selectedImageUri != null) {

            tvImageStatus.setText(
                    "Room image ready for analysis"
            );

        } else {

            tvImageStatus.setText(
                    "Add a room image to continue"
            );
        }
    }

    // =============================================================
    // CONTINUE TO SURVEY
    // =============================================================

    private void continueToSurvey() {

        Intent intent =
                new Intent(
                        NewProjectActivity.this,
                        SurveyActivity.class
                );

        // ---------------------------------------------------------
        // Pass selected image
        // ---------------------------------------------------------

        if (selectedImageUri != null) {

            intent.putExtra(
                    "room_image_uri",
                    selectedImageUri.toString()
            );
        }

        // ---------------------------------------------------------
        // Pass logged-in user ID
        // ---------------------------------------------------------

        intent.putExtra(
                "USER_ID",
                userId
        );

        startActivity(intent);
    }
}