package com.example.pixelgame.BluetoothThreads;

import android.bluetooth.BluetoothSocket;


import java.io.IOException;

public class ConnectThread extends BluetoothThread<BluetoothSocket> {

    public ConnectThread(BluetoothSocket socket){
        super(socket);
    }

    @Override
    public void run() {
        try {
            socket.connect();
        } catch (IOException e) {
            cancel();
            e.printStackTrace();
        }
        new GameServiceThread(socket).start();
    }
}
