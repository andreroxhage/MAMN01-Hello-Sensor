package com.example.a1_hello_sensor;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        ProximitySensorController.ProximitySensorListener,
        AccelerometerSensorController.AccelerometerSensorListener {

    private UIController uiController;
    private ProximitySensorController proximitySensorController;
    private AccelerometerSensorController accelerometerSensorController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiController = new UIController(findViewById(android.R.id.content));
        proximitySensorController = new ProximitySensorController(this);
        accelerometerSensorController = new AccelerometerSensorController(this);

        // Set click listeners for each sensor chip
        uiController.setProximityChipClickListener(v -> onProximitySensorChipClick(v));
        uiController.setAccelerometerChipClickListener(v -> onAccelerometerSensorChipClick(v));

        // Set initial UI state

        // Register sensors if they were activated
        if (proximitySensorController.isSensorActivated()) {
            proximitySensorController.registerListener(this);
        }

        if (accelerometerSensorController.isSensorActivated()) {
            accelerometerSensorController.registerListener(this);
        }
    }

    @Override
    public void onAccelerometerActivated() {
        uiController.onAccelerometerActivated();
    }

    @Override
    public void onAccelerometerDeactivated() {
        uiController.onAccelerometerDeactivated();
    }


    @Override
    public void onProximityActivated() {
        uiController.onProximityActivated();
    }

    @Override
    public void onProximityDeactivated() {
        uiController.onProximityDeactivated();
    }

    @Override
    public void onAccelerometerChanged(double change) {
        uiController.onAccelerometerChanged(change);
    }

    @Override
    public void onProximityChanged(float distance) {
        uiController.onProximityChanged(distance);
    }

    // Proximity sensor chip click handler
    public void onProximitySensorChipClick(View view) {
        toggleProximitySensor();
    }

    // Accelerometer sensor chip click handler
    public void onAccelerometerSensorChipClick(View view) {
        toggleAccelerometerSensor();
    }

    // Toggle the state of the proximity sensor
    private void toggleProximitySensor() {
        if (proximitySensorController.isSensorActivated()) {
            proximitySensorController.unregisterListener();
            Snackbar.make(getWindow().getDecorView(), "Proximity Sensor Deactivated", Snackbar.LENGTH_SHORT).show();
        } else {
            proximitySensorController.registerListener(this);
            Snackbar.make(getWindow().getDecorView(), "Proximity Sensor Activated", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Toggle the state of the accelerometer sensor
    private void toggleAccelerometerSensor() {
        if (accelerometerSensorController.isSensorActivated()) {
            accelerometerSensorController.unregisterListener();
            Snackbar.make(getWindow().getDecorView(), "Accelerometer Sensor Deactivated", Snackbar.LENGTH_SHORT).show();
        } else {
            accelerometerSensorController.registerListener(this);
            Snackbar.make(getWindow().getDecorView(), "Accelerometer Sensor Activated", Snackbar.LENGTH_SHORT).show();
        }
    }

}