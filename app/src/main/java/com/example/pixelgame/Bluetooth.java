package com.example.pixelgame;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.pixelgame.BluetoothThreads.AcceptThread;
import com.example.pixelgame.BluetoothThreads.ConnectThread;
import com.example.pixelgame.BluetoothThreads.GameServiceThread;

import java.io.IOException;

import static com.example.pixelgame.AppData.*;

public class Bluetooth {
    private static final BluetoothAdapter adapter =BluetoothAdapter.getDefaultAdapter();

    private static AcceptThread acceptThread;
    private static ConnectThread connectThread;
    private static GameServiceThread gameServiceThread;

    static boolean isNotSupported(){
        return adapter == null;
    }

    public static void enable(){
        if(!adapter.isEnabled()){
            adapter.enable();
        }
    }

    public static AcceptThread awaitConnection(){
        if(acceptThread!=null){
            acceptThread.cancel();
            acceptThread=null;
        }
        try {
            acceptThread=new AcceptThread(adapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, APP_UUID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acceptThread;
    }

    public static void interruptListening(){
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread.interrupt();
            acceptThread=null;
        }
    }

    public static ConnectThread tryConnection(BluetoothDevice device){
        BluetoothSocket conSocket=null;
        try {
            conSocket=device.createRfcommSocketToServiceRecord(APP_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectThread = new ConnectThread(conSocket);
        connectThread.start();
        return connectThread;
    }

    public static void interruptConnectionTry(){
        if (connectThread != null) {
            connectThread.cancel();
            connectThread.interrupt();
            connectThread=null;
        }
    }

    public static void cancelConnection(){
        if (gameServiceThread != null) {
            gameServiceThread.cancel();
            gameServiceThread.interrupt();
            gameServiceThread=null;
        }
    }

    public static void interruptAll(){
        interruptListening();
        interruptConnectionTry();
        cancelConnection();
    }
}
