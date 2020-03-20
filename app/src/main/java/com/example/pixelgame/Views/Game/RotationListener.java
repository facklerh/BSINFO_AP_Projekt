package com.example.pixelgame.Views.Game;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.pixelgame.GameObjects.Player.Player;

import static com.example.pixelgame.Rules.WORLD_AXIS_X;
import static com.example.pixelgame.Rules.WORLD_AXIS_Y;

public class RotationListener implements SensorEventListener {
    private static final int RADIANS_TO_DEGREES = -57;
    Player player;

    public RotationListener(Player player) {
        if (player == null)
            throw new IllegalArgumentException();
        this.player = player;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            if (sensorEvent.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(sensorEvent.values, 0, truncatedRotationVector, 0, 4);
                updatePlayerSpeed(truncatedRotationVector);
            } else {
                updatePlayerSpeed(sensorEvent.values);
            }
        }
    }

    private void updatePlayerSpeed(float[] vector) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vector);
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, WORLD_AXIS_X, WORLD_AXIS_Y, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        // TODO: ensure pitch and roll matches byte range from -128 to 127
        final byte pitch = (byte) (Math.round(orientation[1] * RADIANS_TO_DEGREES));
        final byte roll = (byte) (Math.round(orientation[2] * RADIANS_TO_DEGREES));
        player.updateSpeed(pitch, roll);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}