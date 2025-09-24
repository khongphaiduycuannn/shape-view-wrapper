package com.ndmquan.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.shape.builder.TextColorBuilder;
import com.hjq.shape.view.ShapeButton;
import com.hjq.shape.view.ShapeTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShapeButton shapeButton = findViewById(R.id.btn_main_test);
        shapeButton.setOnClickListener(v -> {
            shapeButton.getShapeDrawableBuilder()
                    .setSolidColor(0xFF000000)
                    .setStrokeColor(0xFF5A8DDF)
                    .intoBackground();

            shapeButton.getTextColorBuilder()
                    .setTextColor(0xFFFFFFFF)
                    .intoTextColor();

            shapeButton.setText(getString(R.string.demo_color_changed));
        });

        ShapeTextView gradientHorizontalTextView = findViewById(R.id.tv_main_gradient_horizontal);
        gradientHorizontalTextView.setOnClickListener(v -> {
            TextColorBuilder textColorBuilder = gradientHorizontalTextView.getTextColorBuilder();
            if (textColorBuilder.isTextGradientColorsEnable()) {
                textColorBuilder.clearTextGradientColor();
            } else {
                textColorBuilder.setTextGradientColors(new int[] {0xFF49DAFA, 0xFFED58FF})
                    .intoTextColor();
            }
        });

        ShapeTextView gradientVerticalTextView = findViewById(R.id.tv_main_gradient_vertical);
        gradientVerticalTextView.setOnClickListener(v -> {
            TextColorBuilder textColorBuilder = gradientVerticalTextView.getTextColorBuilder();
            if (textColorBuilder.isTextGradientColorsEnable()) {
                textColorBuilder.clearTextGradientColor();
            } else {
                textColorBuilder.setTextGradientColors(new int[] {0xFF49DAFA, 0xFFED58FF})
                    .intoTextColor();
            }
        });

        ShapeTextView strokeTextView = findViewById(R.id.tv_main_stroke);
        strokeTextView.setOnClickListener(v -> {
            TextColorBuilder textColorBuilder = strokeTextView.getTextColorBuilder();
            if (textColorBuilder.isTextStrokeColorEnable()) {
                textColorBuilder.clearTextStrokeColor();
            } else {
                textColorBuilder.setTextStrokeColor(0xFF000000)
                    .intoTextColor();
            }
        });

        ShapeTextView gradientAndStrokeTextView = findViewById(R.id.tv_main_gradient_and_stroke);
        gradientAndStrokeTextView.setOnClickListener(v -> {
            TextColorBuilder textColorBuilder = gradientAndStrokeTextView.getTextColorBuilder();
            if (textColorBuilder.isTextGradientColorsEnable() && textColorBuilder.isTextStrokeColorEnable()) {
                textColorBuilder.clearTextGradientColor();
                textColorBuilder.clearTextStrokeColor();
            } else {
                textColorBuilder.setTextGradientColors(new int[] {0xFFFEFA54, 0xFFF08833})
                    .setTextStrokeColor(0xFF000000)
                    .intoTextColor();
            }
        });
    }
}