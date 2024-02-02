package com.example.a1_hello_sensor;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class FlashlightController {

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn = false;

    public FlashlightController(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            isFlashlightOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isFlashlightOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    public void toggleFlashlight() {
        if (isFlashlightOn) {
            turnOffFlashlight();
        } else {
            turnOnFlashlight();
        }
    }
    public boolean isFlashlightOn() {
        return isFlashlightOn;
    }
}
