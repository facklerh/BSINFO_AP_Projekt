package com.example.pixelgame;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.pixelgame.Views.BaseView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import static android.bluetooth.BluetoothClass.Device.Major.PHONE;
import static com.example.pixelgame.Data.AppData.APP_NAME;
import static com.example.pixelgame.Data.AppData.APP_UUID;

public final class Bluetooth {
    // BluetoothAdapter things
    private Bluetooth() {
    }

    private static final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    public static boolean isNotSupported() {
        return adapter == null;
    }

    public static void enable() {
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
    }

    public static void discover() {
        adapter.startDiscovery();
    }

    public static void cancelDiscovery() {
        adapter.cancelDiscovery();
    }

    public static Set<BluetoothDevice> getBonded() {
        return adapter.getBondedDevices();
    }

    public static synchronized BluetoothServerSocket getServerSocket() throws IOException {
        return adapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, APP_UUID);
    }

    public static synchronized BluetoothSocket getConnectionSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(APP_UUID);
    }

    private static BluetoothSocket connection = null;

    public static boolean connected() {
        return connection != null && connection.isConnected();
    }

    public static synchronized void setConnection(BluetoothSocket socket) {
        if (connected()) {
            // TODO: ???
        } else if (socket != null && socket.isConnected()) {
            connection = socket;
        }
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }

    public static BluetoothDevice getConnectedDevice() {
        if (connection != null) {
            return connection.getRemoteDevice();
        }
        return null;
    }

    public static String getConnectedID() {
        if (connection != null) {
            return connection.getRemoteDevice().getAddress();
        }
        return null;
    }

    public static InputStream getInputStream() throws IOException {
        if (connection != null) {
            return connection.getInputStream();
        }
        return null;
    }

    public static OutputStream getOutputStream() throws IOException {
        if (connection != null) {
            return connection.getOutputStream();
        }
        return null;
    }

    // End BluetoothAdapter

    // BroadcastReceiver

    public static final IntentFilter statusChanged = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

    public static final BroadcastReceiver statusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                BaseView view = ((BaseView) context);
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (mode) {
                    case (BluetoothAdapter.STATE_ON):
                        Log.i(APP_NAME, "Bluetooth on");
                        break;
                    case (BluetoothAdapter.STATE_OFF):
                        Log.i(APP_NAME, "Bluetooth off");
                        view.showErrorDialog(R.string.error_BT, R.string.errM_BTTurnedOff, R.string.enable, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bluetooth.enable();
                            }
                        });
                        break;
                    case (BluetoothAdapter.STATE_TURNING_OFF):
                        Log.i(APP_NAME, "Bluetooth turning off");
                        break;
                    case (BluetoothAdapter.STATE_TURNING_ON):
                        Log.i(APP_NAME, "Bluetooth turning on");
                        break;
                    default:
                        Log.e(APP_NAME, "Bluetooth ERROR");

                }
            }
        }
    };

    // End BroadcastReceiver

    // util

    public static boolean isMobileDevice(BluetoothDevice device) {
        return device.getBluetoothClass().getMajorDeviceClass() == PHONE && device.getName() != null && !device.getName().isEmpty();
    }
}
