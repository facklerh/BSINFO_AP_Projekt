package com.example.pixelgame.BluetoothThreads;

import java.io.Closeable;
import java.io.IOException;

public abstract class BluetoothThread<Socket extends Closeable> extends Thread {
    final Socket socket;


    protected BluetoothThread(Socket socket) {
        this.socket = socket;
    }

    public void cancel(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
