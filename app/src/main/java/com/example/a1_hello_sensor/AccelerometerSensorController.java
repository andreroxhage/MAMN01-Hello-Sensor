package com.example.a1_hello_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

public class AccelerometerSensorController implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerActivated = false;
    private AccelerometerListener listener;
    private double accelerationPreviousValue;
    private double accelerationCurrentValue;
    private FlashlightController flashlightController;
    private Vibrator vibrator;

    public AccelerometerSensorController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        flashlightController = new FlashlightController(context);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void registerListener(AccelerometerListener listener) {
        this.listener = listener;
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isAccelerometerActivated = true;
            if (listener != null) {
                listener.onAccelerometerActivated();
            }
        }
    }

    public void unregisterListener() {
        if (accelerometerSensor != null) {
            sensorManager.unregisterListener(this);
            isAccelerometerActivated = false;
            if (listener != null) {
                listener.onAccelerometerDeactivated();
            }
        }
    }

    public boolean isAccelerometerActivated() {
        return isAccelerometerActivated;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values != null && event.values.length >= 3) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Absolute single value for change in acceleration
                accelerationCurrentValue = Math.sqrt((x * x + y * y + z * z));
                double change = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
                accelerationPreviousValue = accelerationCurrentValue;


                // Inform listeners && let flashlight blink once
                if (listener != null) {
                    listener.onAccelerometerChanged(change, x, y);
                }

                if((int) x == 0 && (int) y == 0) {
                    vibrator.vibrate(200);
                    flashlightController.toggleFlashlight();
                    flashlightController.toggleFlashlight();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    // Interface to communicate events to the UI
    public interface AccelerometerListener {
        void onAccelerometerActivated();

        void onAccelerometerDeactivated();

        void onAccelerometerChanged(double change, float x, float y);
    }

    public boolean isSensorActivated() {
        return isAccelerometerActivated;
    }
}