package com.example.pixelgame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class GyroControl {

    private SensorManager sensorManager;
    private Sensor sensor;
    String TAG;

    public final float[] rotation = new float[16];

    public GyroControl(SensorManager sensorManager, Sensor sensor, String TAG) {
        rotation[0] = 1;
        rotation[4] = 1;
        rotation[8] = 1;
        rotation[12] = 1;
    }

    public void print (){
            /*
            * Log.wtf(TAG, "0: "+ rotation[0]);
            Log.wtf(TAG, "1: "+ rotation[4]);
            Log.wtf(TAG, "2: "+ rotation[8]);
            Log.wtf(TAG, "3: "+ rotation[12]);*/

//        for (int i = 0; i < 4; i++) {
//            Log.wtf(TAG,""+rotation[i]+"\t"+rotation[i+4]+"\t"+rotation[i+8]+"\t"+rotation[i+12]);
//        }
//        Log.wtf(TAG,"____________________________________");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
