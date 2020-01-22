package com.example.pixelgame.BluetoothThreads;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;


import java.io.IOException;

public class AcceptThread extends BluetoothThread<BluetoothServerSocket> {


    public AcceptThread(BluetoothServerSocket bluetoothServerSocket) {
        super(bluetoothServerSocket);
    }

    @Override
    public void run() {
        BluetoothSocket connectedSocket=null;
        BluetoothDevice device=null;
        try {
            connectedSocket=this.socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (connectedSocket!=null){
            new GameServiceThread(connectedSocket).start();
        }
    }
}
