package com.example.pixelgame;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.pixelgame.Views.Game.RotationListener;

import static android.content.Context.SENSOR_SERVICE;

public final class GyroSensor {
    public static SensorManager sensorManager = null;
    public static Sensor gyroSensor = null;

    private GyroSensor() {
    }

    public static boolean initGyroSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        if (sensorManager == null)
            return false;
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        return gyroSensor != null;
    }

    public static boolean registerRotationListener(RotationListener listener) {
        if (sensorManager != null && gyroSensor != null && listener != null) {
            return sensorManager.registerListener(listener, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
        }
        return false;
    }

    public static void unregisterRotationListener(RotationListener listener) {
        if (sensorManager != null && listener != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}
