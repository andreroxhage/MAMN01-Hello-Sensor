package com.example.a1_hello_sensor;

import android.hardware.SensorEventListener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

public class GyroscopeSensorController implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private boolean isGyroscopeActivated = false;
    private GyroscopeListener listener;

    public GyroscopeSensorController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void registerListener(GyroscopeListener listener) {
        this.listener = listener;
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isGyroscopeActivated = true;
            if (listener != null) {
                listener.onGyroscopeActivated();
            }
        }
    }

    public void unregisterListener() {
        if (gyroscopeSensor != null) {
            sensorManager.unregisterListener(this, gyroscopeSensor);
            isGyroscopeActivated = false;
            if (listener != null) {
                listener.onGyroscopeDeactivated();
            }
        }
    }

    public boolean isGyroscopeActivated() {
        return isGyroscopeActivated;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && listener != null) {
            listener.onGyroscopeChanged(event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    public boolean isSensorActivated() {
        return isGyroscopeActivated;
    }

    // Interface to communicate events to the UI
    public interface GyroscopeListener {
        void onGyroscopeActivated();

        void onGyroscopeDeactivated();

        void onGyroscopeChanged(float[] values);
    }
}
