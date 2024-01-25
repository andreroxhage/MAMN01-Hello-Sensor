package com.example.a1_hello_sensor;

import android.graphics.Color;
import android.view.View;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import android.widget.TextView;
import android.widget.ProgressBar;

public class UIController {

    private Chip activateGyroscopeChip;
    private Chip activateAccelerometerChip;
    private TextView gyroscopeTextView;
    private TextView accelerometerTextView;
    private TextView warningTextView;
    private TextView square;
    private ProgressBar shakeMeter;

    public UIController(View rootView) {
        activateGyroscopeChip = rootView.findViewById(R.id.activateGyroscopeChip);
        gyroscopeTextView = rootView.findViewById(R.id.gyroscopeTextView);
        warningTextView = rootView.findViewById(R.id.warningTextView);


        square = rootView.findViewById(R.id.square);

        activateAccelerometerChip = rootView.findViewById(R.id.activateAccelerometerChip);
        accelerometerTextView = rootView.findViewById(R.id.accelerometerTextView);
        shakeMeter = rootView.findViewById(R.id.shakeBar);
    }

    public void setGyroscopeChipText(String text) {
        activateGyroscopeChip.setText(text);
    }

    public void setAccelerometerChipText(String text) {
        activateAccelerometerChip.setText(text);
    }

    public void updateGyroscopeText(float[] values) {
        StringBuilder builder = new StringBuilder();
        for (float value : values) {
            builder.append(String.format("%.2f cm, ", value));
        }
        // Remove the trailing comma and space
        builder.setLength(builder.length() - 2);

        gyroscopeTextView.setText(builder.toString());
    }

    public void setAccelerometerChipClickListener(View.OnClickListener clickListener) {
        activateAccelerometerChip.setOnClickListener(clickListener);
    }

    public void setGyroscopeChipClickListener(View.OnClickListener clickListener) {
        activateGyroscopeChip.setOnClickListener(clickListener);
    }

    // Notify UI about sensor activation
    public void onGyroscopeActivated() {
        setGyroscopeChipText("Deactivate Gyroscope Sensor");
    }

    // Notify UI about sensor deactivation
    public void onGyroscopeDeactivated() {
        setGyroscopeChipText("Activate Gyroscope Sensor");
        gyroscopeTextView.setText("");

    }

    // Notify UI about sensor activation
    public void onAccelerometerActivated() {
        setAccelerometerChipText("Deactivate Accelerometer Sensor");
    }

    // Notify UI about sensor deactivation
    public void onAccelerometerDeactivated() {
        setAccelerometerChipText("Activate Accelerometer Sensor");
        accelerometerTextView.setText("");
    }

    // Notify UI about proximity change
    public void onGyroscopeChanged(float[] values) {
        updateGyroscopeText(values);

        for(float n : values) {
            if(n > 0.5) {
                warningTextView.setText("Slow down!");

            } else {
                warningTextView.setText("");
            }
        }
    }

    // Notify UI about accelerometer change
    public void onAccelerometerChanged(double change, float sides, float upDown) {
        updateAccelerometerText(change);
        shakeMeter.setProgress((int) change);

        // Square
        // Assuming square is an instance of View
        square.setRotationX(upDown * 3f);
        square.setRotationY(sides * 3f);
        square.setRotation(-sides);
        square.setTranslationX(sides * -10);
        square.setTranslationY(upDown * 10);

        // Changes the color of the square if it's completely flat
        int color = ((int) upDown == 0 && (int) sides == 0) ? Color.GREEN : Color.RED;
        square.setBackgroundColor(color);

        square.setText("up/down " + (int) upDown + "\nleft/right " + (int) sides);

    }

    private void updateAccelerometerText(double change) {
        // Assuming you have a TextView for displaying accelerometer data
        accelerometerTextView.setText(String.format("Acceleration: " + (int) change));
    }

}