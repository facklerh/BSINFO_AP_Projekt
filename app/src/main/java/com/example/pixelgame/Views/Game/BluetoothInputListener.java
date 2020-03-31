package com.example.pixelgame.Views.Game;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

abstract class BluetoothInputListener extends Thread {
    InputStream input;

    BluetoothInputListener(InputStream input) {
        this.input = input;
    }

    int read() {
        try {
            return input.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    byte[] read(int length) {
        byte[] result = new byte[length];
        try {
            int nrReadBytes = input.read(result);
            if (nrReadBytes == length)
                return result;

            Log.e("INPUT_STREAM", "read wrong amounts of bytes | expected: " + length + " got: " + nrReadBytes);
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
