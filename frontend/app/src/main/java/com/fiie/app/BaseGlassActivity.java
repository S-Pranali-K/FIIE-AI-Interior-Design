package com.fiie.app;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BaseGlassActivity extends AppCompatActivity {

    public void applyGlassBlur(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && view != null) {
            view.setRenderEffect(
                    RenderEffect.createBlurEffect(
                            30f,
                            30f,
                            Shader.TileMode.CLAMP
                    )
            );
        }
    }
}