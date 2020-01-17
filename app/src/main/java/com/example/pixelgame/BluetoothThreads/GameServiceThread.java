package com.example.pixelgame.BluetoothThreads;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GameServiceThread extends BluetoothThread<BluetoothSocket> {
    // BluetoothDevice connectedDevice;

    public final InputStream input;
    public final OutputStream output;

    public GameServiceThread(BluetoothSocket socket) {
        super(socket);
        // this.connectedDevice = this.socket.getRemoteDevice();
        InputStream getInput=null;
        OutputStream getOutput=null;
        try {
            getInput = socket.getInputStream();
            getOutput = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input=getInput;
        output=getOutput;
    }

    @Override
    public void run() {
        super.run();

        /**
         * TODO:
         * await for Players to start
         * sending and receiving data (shots and so on)
         * await end of game
         */
    }
}
