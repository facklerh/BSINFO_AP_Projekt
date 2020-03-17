package com.example.pixelgame;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class GyroscopeUnit extends BaseView implements SensorEventListener {

    public static final int EVENT_COUNT = 200;
    private static final int RADS_TO_DEGS = -57;

    MyCanvas myCanvas;
    private SensorManager sensorManager;
    private Sensor sensor;
    GyroControl gyroControl;

    public static float pitch = 0;
    public static float roll = 0;

    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCanvas = new MyCanvas(this);
        myCanvas.setBackgroundColor(Color.GRAY);
        setContentView(new GamePanel(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        gyroControl = new GyroControl(sensorManager, sensor, TAG);
    }

    @Override
    protected void onStart() {
        // Drawing with CANVAS
        sensorManager.registerListener(this, sensor, 10000);
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
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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

    private void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisY = SensorManager.AXIS_Y;
        float[] adjustedRotationMAtrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisY, adjustedRotationMAtrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMAtrix, orientation);
        pitch = orientation[1] * RADS_TO_DEGS;
        roll = orientation[2] * RADS_TO_DEGS;
        pitch = Math.round(pitch * 1000) / 1000;
        roll = Math.round(roll * 1000) / 1000;
        Log.wtf(TAG, "Pitch: " + pitch);
        Log.wtf(TAG, "Roll: " + roll);
        Log.wtf(TAG, "--------------------------");
    }
//                SensorManager.getRotationMatrixFromVector(gyroControl.rotation, sensorEvent.values);
//                StringBuilder sb= new StringBuilder();
//                for (float value : sensorEvent.values) {
//                    sb.append(String.format("%.2f",value)).append(",\t");
//                }
//                Log.wtf(TAG, "Values: " + sb.toString());
//                Log.wtf(TAG, "-------------------------------------");
    // gyroControl.print();

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
