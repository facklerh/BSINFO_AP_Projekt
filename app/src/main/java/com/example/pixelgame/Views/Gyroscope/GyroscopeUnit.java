package com.example.pixelgame.Views.Gyroscope;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.example.pixelgame.Views.BaseView;

public class GyroscopeUnit extends BaseView {

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                if (sensorEvent.values.length > 4) {
                    float[] truncatedRotationVector = new float[4];
                    System.arraycopy(sensorEvent.values, 0, truncatedRotationVector, 0, 4);
                    update(truncatedRotationVector);
                } else {
                    update(sensorEvent.values);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static final int EVENT_COUNT = 200;
    private static final int RADS_TO_DEGS = -57;

    private SensorManager sensorManager;
    private Sensor sensor;

    public static float pitch = 0;
    public static float roll = 0;

    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GamePanel(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onStart() {
        // Drawing with CANVAS
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisY = SensorManager.AXIS_Y;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisY, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        pitch = orientation[1] * RADS_TO_DEGS;
        roll = orientation[2] * RADS_TO_DEGS;
        pitch = Math.round(pitch * 1000) / 1000;
        roll = Math.round(roll * 1000) / 1000;
        Log.wtf(TAG, "Pitch: " + pitch);
        Log.wtf(TAG, "Roll: " + roll);
        Log.wtf(TAG, "--------------------------");
    }
}
