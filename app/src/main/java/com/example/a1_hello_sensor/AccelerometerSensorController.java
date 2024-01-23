package com.example.a1_hello_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerSensorController implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isSensorActivated = false;
    private AccelerometerSensorListener listener;
    private double accelerationPreviousValue;
    private double accelerationCurrentValue;
    private FlashlightController flashlightController;
    private static final double ACCELERATION_THRESHOLD = 2.0;
    private static final long TIME_DELAY_THRESHOLD = 2000; // in milliseconds
    private long lastToggleTimestamp = 0;


    public AccelerometerSensorController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        flashlightController = new FlashlightController(context);
    }

    public void registerListener(AccelerometerSensorListener listener) {
        this.listener = listener;
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorActivated = true;
            if (listener != null) {
                listener.onAccelerometerActivated();
            }
        }
    }

    public void unregisterListener() {
        if (accelerometerSensor != null) {
            sensorManager.unregisterListener(this);
            isSensorActivated = false;
            if (listener != null) {
                listener.onAccelerometerDeactivated();
            }
        }
    }

    public boolean isSensorActivated() {
        return isSensorActivated;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Absolute single value for change in acceleration
            //https://www.youtube.com/watch?v=zUzZ67grYt8
            accelerationCurrentValue = Math.sqrt((x * x + y * y + z * z));
            double changeAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            //Toggle flashlight if acceleration is above threshold
            // Apply a threshold, time delay, and debouncing
            handleAccelerationChange(changeAcceleration);

            // Inform listeners
            if (listener != null) {
                listener.onAccelerometerChanged(changeAcceleration);
            }
        }
    }

    private void handleAccelerationChange(double changeAcceleration) {
        if (changeAcceleration > ACCELERATION_THRESHOLD) {
            long currentTimestamp = System.currentTimeMillis();

            // Apply a time delay
            if (currentTimestamp - lastToggleTimestamp > TIME_DELAY_THRESHOLD) {
                // Debouncing - only toggle if the flashlight state is different
                flashlightController.toggleFlashlight();

                // Update timestamp and flashlight state
                lastToggleTimestamp = currentTimestamp;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    // Interface to communicate events to the UI
    public interface AccelerometerSensorListener {
        void onAccelerometerActivated();

        void onAccelerometerDeactivated();

        void onAccelerometerChanged(double change);
    }

}
