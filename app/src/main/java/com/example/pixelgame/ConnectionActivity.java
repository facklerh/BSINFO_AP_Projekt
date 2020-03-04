package com.example.pixelgame;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Closeable;
import java.io.IOException;

public class ConnectionActivity extends BaseView {

    private static final String TAG = "CONNECTION";
    private MobileDeviceListAdapter devices;
    ListView listView;

    AcceptThread acceptThread;
    ConnectThread connectThread;

    // Broadcasting Variables

    private static final IntentFilter DEVICE_FOUND = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private final BroadcastReceiver discoveringReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Bluetooth.enable();
        initViews();
        registerReceiver(Bluetooth.statusReceiver, Bluetooth.statusChanged);
    }

    private void initViews() {
        initListAdapter();
        initListView();
        initButtons();
    }

    private void initListAdapter() {
        devices = new MobileDeviceListAdapter(this);
        devices.addAll(Bluetooth.getBonded());
        Bluetooth.discover();
    }

    private void initButtons() {
        Button discover = findViewById(R.id.btn_discover);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bluetooth.discover();
            }
        });
        Button reset = findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListAdapter();
            }
        });
    }

    private void initListView() {
        listView = findViewById(R.id.list_mobile_device);
        listView.setAdapter(devices);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (acceptThread == null && connectThread == null) {
                    tryConnection(devices.getItem(position));
                }
            }
        });
    }

    private void tryConnection(BluetoothDevice device) {
        if (device != null) {
            Bluetooth.cancelDiscovery();
            awaitConnection(device);
            connectTo(device);
        }
    }

    private void awaitConnection(BluetoothDevice pairingDevice) {
        if (acceptThread == null) {
            Log.i(TAG, "await connection of" + pairingDevice.getName());
            acceptThread = new AcceptThread(pairingDevice);
            acceptThread.start();
        }
    }

    private void connectTo(BluetoothDevice pairingDevice) {
        if (connectThread == null) {
            Log.i(TAG, "connect to " + pairingDevice.getName());
            connectThread = new ConnectThread(pairingDevice);
            connectThread.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(discoveringReceiver, DEVICE_FOUND);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(discoveringReceiver);
        killThreads();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(Bluetooth.statusReceiver);
        super.onDestroy();
    }

    private synchronized void launchWaitingArea(BluetoothSocket socket) {
        killThreads();
        Bluetooth.setConnection(socket);
        launchActivity(WaitingArea.class);
    }

    private synchronized void enable() {
        killThreads();
        Bluetooth.discover();
    }

    private synchronized void killThreads() {
        if (acceptThread != null) {
            try {
                acceptThread.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            acceptThread = null;
        }
        if (connectThread != null) {
            try {
                connectThread.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectThread = null;
        }
    }

    private static abstract class BluetoothThread<Socket extends Closeable> extends Thread implements Closeable {
        Socket socket = null;

        @Override
        public void close() throws IOException {
            socket.close();
            interrupt();
        }
    }

    private class AcceptThread extends BluetoothThread<BluetoothServerSocket> {

        final BluetoothDevice allowed;

        AcceptThread(BluetoothDevice allowed) {
            this.allowed = allowed;
        }

        @Override
        public void run() {
            initServerSocket();
            listen();
            enable();
        }

        private void initServerSocket() {
            try {
                socket = Bluetooth.getServerSocket();
            } catch (IOException e) {
                showMessage(R.string.errM_missingSocket);
            }
        }

        private void listen() {
            BluetoothSocket connectedSocket;
            try {
                connectedSocket = socket.accept(5000);
                if (connectedSocket != null) {
                    final BluetoothDevice remote = connectedSocket.getRemoteDevice();
                    if (remote.equals(allowed)) {
                        launchWaitingArea(connectedSocket);
                    } else {
                        Log.d(TAG, "found illegal device: " + remote.getName());
                    }
                } else {
                    Log.d(TAG, "could not find device in time");
                }
            } catch (IOException e) {
                Log.d(TAG, "unknown error");
                e.printStackTrace();
            }
        }
    }

    private class ConnectThread extends BluetoothThread<BluetoothSocket> {
        ConnectThread(BluetoothDevice toConnect) {
            BluetoothSocket test = null;
            try {
                test = Bluetooth.getConnectionSocket(toConnect);
            } catch (IOException e) {
                Log.d(TAG, "Did not receive bluetooth socket.");
            }
            socket = test;
        }

        @Override
        public void run() {
            if (socket != null) {
                try {
                    socket.connect();
                    launchWaitingArea(socket);
                } catch (IOException e) {
                    Log.d(TAG, "Could not connect to device");
                }
            }
        }
    }
}

