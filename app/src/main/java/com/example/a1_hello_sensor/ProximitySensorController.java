package com.example.a1_hello_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.a1_hello_sensor.FlashlightController;

public class ProximitySensorController implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private boolean isSensorActivated = false;
    private FlashlightController flashlightController;
    private ProximitySensorListener listener;


    public ProximitySensorController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        flashlightController = new FlashlightController(context);
    }

    public void registerListener(ProximitySensorListener listener) {
        this.listener = listener;
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorActivated = true;
            if (listener != null) {
                listener.onProximityActivated();
            }
        }
    }

    public void unregisterListener() {
        if (proximitySensor != null) {
            sensorManager.unregisterListener(this);
            isSensorActivated = false;
            if (listener != null) {
                listener.onProximityDeactivated();
            }
        }
    }

    public boolean isSensorActivated() {
        return isSensorActivated;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];

            if (distance < 2.0) {
                flashlightController.turnOnFlashlight();
            } else {
                flashlightController.turnOffFlashlight();
            }

            if (listener != null) {
                listener.onProximityChanged(distance);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    // Interface to communicate events to the UI
    public interface ProximitySensorListener {
        void onProximityActivated();

        void onProximityDeactivated();

        void onProximityChanged(float distance);
    }
}