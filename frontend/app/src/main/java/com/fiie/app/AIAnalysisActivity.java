package com.fiie.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AIAnalysisActivity extends AppCompatActivity {

    private ProgressBar progressAnalysis;
    private TextView tvProgress;
    private TextView tvAnalysisMessage;

    private int progress = 0;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ai_analysis);

        progressAnalysis = findViewById(R.id.progressAnalysis);
        tvProgress = findViewById(R.id.tvProgress);
        tvAnalysisMessage = findViewById(R.id.tvAnalysisMessage);

        startAnalysis();
    }

    private void startAnalysis() {

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                progress += 5;

                progressAnalysis.setProgress(progress);
                tvProgress.setText(progress + "%");

                if (progress < 100) {

                    if (progress < 30) {
                        tvAnalysisMessage.setText(
                                "Processing room image..."
                        );
                    } else if (progress < 60) {
                        tvAnalysisMessage.setText(
                                "Understanding room and furniture..."
                        );
                    } else if (progress < 80) {
                        tvAnalysisMessage.setText(
                                "Analyzing functional requirements..."
                        );
                    } else {
                        tvAnalysisMessage.setText(
                                "Preparing design recommendations..."
                        );
                    }

                    handler.postDelayed(this, 150);

                } else {

                    tvAnalysisMessage.setText(
                            "Analysis completed!"
                    );

                    handler.postDelayed(() -> {

                        Intent intent = new Intent(
                                AIAnalysisActivity.this,
                                DesignResultsActivity.class
                        );

                        startActivity(intent);
                        finish();

                    }, 800);
                }
            }

        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}