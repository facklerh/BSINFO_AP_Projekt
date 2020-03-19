package com.example.pixelgame.Views.Game;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pixelgame.Bluetooth;
import com.example.pixelgame.Data.GameStatistic;
import com.example.pixelgame.Games.GamePanel;
import com.example.pixelgame.Views.BaseView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GameActivity extends BaseView {

    InputStream input;
    OutputStream output;
    private SensorManager sensorManager;
    private Sensor sensor;

    private GameStatistic enemyStatistic;
    private GamePanel panel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!Bluetooth.connected())
            finish();

        super.onCreate(savedInstanceState);

        try {
            input = Bluetooth.getInputStream();
            output = Bluetooth.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
        initBluetoothListener();
        initEnemyStatistic();
        initSensor();
        initSelectionScreen();
    }

    private void initBluetoothListener() {

    }

    private void initEnemyStatistic() {

    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    private boolean registerRotationListener() {
        if (sensorManager != null && sensor != null && rListener != null) {
            return sensorManager.registerListener(rListener, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
        return false;
    }

    private void unregisterRotationListener() {
        if (sensorManager != null && rListener != null) {
            sensorManager.unregisterListener(rListener);
        }
    }

    private void initSelectionScreen() {

    }

    private void initGameScreen() {
        setContentView(panel);
    }
}
