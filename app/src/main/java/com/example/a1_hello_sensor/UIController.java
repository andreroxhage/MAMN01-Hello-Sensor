package com.example.a1_hello_sensor;

import android.graphics.Color;
import android.view.View;
import com.google.android.material.chip.Chip;
import android.widget.TextView;
import android.widget.ProgressBar;

public class UIController {

    private Chip activateProximityChip;
    private Chip activateAccelerometerChip;
    private TextView proximityTextView;
    private TextView accelerometerTextView;
    private TextView square;
    private ProgressBar shakeMeter;
    private ProximitySensorController.ProximitySensorListener proximitySensorListener;
    private AccelerometerSensorController.AccelerometerSensorListener accelerometerSensorListener;

    public UIController(View rootView) {
        activateProximityChip = rootView.findViewById(R.id.activateProximityChip);
        proximityTextView = rootView.findViewById(R.id.proximityTextView);

        square = rootView.findViewById(R.id.square);

        activateAccelerometerChip = rootView.findViewById(R.id.activateAccelerometerChip);
        accelerometerTextView = rootView.findViewById(R.id.accelerometerTextView);
        shakeMeter = rootView.findViewById(R.id.shakeBar);
    }

    public void setProximityChipText(String text) {
        activateProximityChip.setText(text);
    }

    public void setAccelerometerChipText(String text) {
        activateAccelerometerChip.setText(text);
    }

    public void updateProximityText(float distance) {
        proximityTextView.setText(String.format("%.2f cm", distance));
    }

    public void setProximityChipClickListener(View.OnClickListener clickListener) {
        activateProximityChip.setOnClickListener(clickListener);
    }

    public void setAccelerometerChipClickListener(View.OnClickListener clickListener) {
        activateAccelerometerChip.setOnClickListener(clickListener);
    }

    // Set the listener for proximity sensor events
    public void setProximitySensorListener(ProximitySensorController.ProximitySensorListener listener) {
        this.proximitySensorListener = listener;
    }

    // Notify UI about sensor activation
    public void onProximityActivated() {
        setProximityChipText("Deactivate Proximity Sensor");
    }

    // Notify UI about sensor deactivation
    public void onProximityDeactivated() {
        setProximityChipText("Activate Proximity Sensor");
    }

    // Notify UI about sensor activation
    public void onAccelerometerActivated() {
        setAccelerometerChipText("Deactivate Accelerometer Sensor");
    }

    // Notify UI about sensor deactivation
    public void onAccelerometerDeactivated() {
        setAccelerometerChipText("Activate Accelerometer Sensor");
    }

    // Notify UI about proximity change
    public void onProximityChanged(float distance) {
        updateProximityText(distance);
    }

    // Set the listener for accelerometer sensor events
    public void setAccelerometerSensorListener(AccelerometerSensorController.AccelerometerSensorListener listener) {
        this.accelerometerSensorListener = listener;
    }

    // Notify UI about accelerometer change
    public void onAccelerometerChanged(double change) {
        updateAccelerometerText(change);
        shakeMeter.setProgress((int) change);
    }

    private void updateAccelerometerText(double change) {
        // Assuming you have a TextView for displaying accelerometer data
        accelerometerTextView.setText(String.format("Acceleration: " + (int) change));
    }
}
