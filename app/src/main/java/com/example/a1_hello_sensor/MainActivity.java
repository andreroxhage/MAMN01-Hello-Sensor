package com.example.a1_hello_sensor;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        AccelerometerSensorController.AccelerometerListener, GyroscopeSensorController.GyroscopeListener {

    private AccelerometerSensorController accelerometerController;
    private GyroscopeSensorController gyroscopeController;
    private UIController uiController;
    private  MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.sound); // https://stackoverflow.com/questions/18459122/play-sound-on-button-click-android

        uiController = new UIController(findViewById(android.R.id.content));
        gyroscopeController = new GyroscopeSensorController(this);
        accelerometerController = new AccelerometerSensorController(this);

        // Set click listeners for each sensor chip
        uiController.setGyroscopeChipClickListener(v -> onActivateGyroscopeChipClick(v));
        uiController.setAccelerometerChipClickListener(v -> onActivateAccelerometerChipClick(v));

        // Register sensors if they were activated
        if (gyroscopeController.isSensorActivated()) {
            gyroscopeController.registerListener(this);
        }

        if (accelerometerController.isSensorActivated()) {
            accelerometerController.registerListener(this);
        }
    }

    // Accelerometer sensor chip click handler
    private void toggleAccelerometerSensor() {
        if (accelerometerController.isAccelerometerActivated()) {
            accelerometerController.unregisterListener();
            Snackbar.make(getWindow().getDecorView(), "Accelerometer Sensor Deactivated", Snackbar.LENGTH_SHORT).show();
        } else {
            accelerometerController.registerListener(this);
            Snackbar.make(getWindow().getDecorView(), "Accelerometer Sensor Activated", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Gyroscope sensor chip click handler
    private void toggleGyroscopeSensor() {
        if (gyroscopeController.isGyroscopeActivated()) {
            gyroscopeController.unregisterListener();
            Snackbar.make(getWindow().getDecorView(), "Gyroscope Sensor Deactivated", Snackbar.LENGTH_SHORT).show();
        } else {
            gyroscopeController.registerListener(this);
            Snackbar.make(getWindow().getDecorView(), "Gyroscope Sensor Activated", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Update the UI on accelerometer changes
    @Override
    public void onAccelerometerActivated() {
        uiController.onAccelerometerActivated();
    }

    @Override
    public void onAccelerometerDeactivated() {
        uiController.onAccelerometerDeactivated();
    }

    @Override
    public void onAccelerometerChanged(double change, float x, float y) {
        uiController.onAccelerometerChanged(change, x, y);
    }

    public void onActivateAccelerometerChipClick(View view) {
        toggleAccelerometerSensor();
    }

    // Update the UI on gyroscope changes
    @Override
    public void onGyroscopeActivated() {
        uiController.onGyroscopeActivated();
    }

    @Override
    public void onGyroscopeDeactivated() {
        uiController.onGyroscopeDeactivated();
    }

    @Override
    public void onGyroscopeChanged(float[] values) {
        mp.start();
        uiController.onGyroscopeChanged(values);

    }

    public void onActivateGyroscopeChipClick(View view) {
        toggleGyroscopeSensor();
    }
}
