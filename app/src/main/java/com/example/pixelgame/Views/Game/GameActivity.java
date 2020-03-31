package com.example.pixelgame.Views.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.pixelgame.Bluetooth;
import com.example.pixelgame.Data.AppData;
import com.example.pixelgame.Data.GameStatistic;
import com.example.pixelgame.Games.GamePanel;
import com.example.pixelgame.GyroSensor;
import com.example.pixelgame.Messenger;
import com.example.pixelgame.Views.BaseView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static com.example.pixelgame.MsgUtil.Opcodes.BULLET;
import static com.example.pixelgame.MsgUtil.Opcodes.CONNECTED;
import static com.example.pixelgame.MsgUtil.Opcodes.DISCONNECTED;
import static com.example.pixelgame.MsgUtil.Opcodes.ERROR;
import static com.example.pixelgame.MsgUtil.Opcodes.IN_GAME;
import static com.example.pixelgame.MsgUtil.Opcodes.NAME;
import static com.example.pixelgame.MsgUtil.Opcodes.PAUSE;
import static com.example.pixelgame.MsgUtil.Opcodes.READY_FOR_GAME;
import static com.example.pixelgame.MsgUtil.Opcodes.REQ_NAME;
import static com.example.pixelgame.MsgUtil.Opcodes.REQ_VERSION;
import static com.example.pixelgame.MsgUtil.Opcodes.RESUME;

public class GameActivity extends BaseView {

    byte status = DISCONNECTED;
    byte opponentStatus = DISCONNECTED;

    InputStream input;
    OutputStream output;

    private Messenger messenger;
    private BluetoothInputReader inputReader;

    private GameStatistic enemyStatistic;
    private GamePanel panel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!Bluetooth.connected())
            finish();

        super.onCreate(savedInstanceState);

        initStreams();
        initEnemyStatistic();
        initSensor();
        initSelectionScreen();
    }

    private void initStreams() {
        try {
            input = Bluetooth.getInputStream();
            output = Bluetooth.getOutputStream();
            status = CONNECTED;
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
        initBluetoothListener();
        initMessenger();
    }

    private void initMessenger() {
        messenger = new Messenger(output);
    }

    private void initBluetoothListener() {
        inputReader = new BluetoothInputReader(input);
        inputReader.start();
    }

    private void initEnemyStatistic() {
        enemyStatistic = AppData.getPlayerData().get(Bluetooth.getConnectedID());
        if (enemyStatistic == null) {
            messenger.requestName();
        }
    }

    private void initSensor() {
        GyroSensor.initGyroSensor(this);
    }

    private void initSelectionScreen() {

    }

    private void initGameScreen() {
        // panel = new selected game
        setContentView(panel);
    }

    private class BluetoothInputReader extends BluetoothInputListener {
        private BluetoothInputReader(InputStream input) {
            super(input);
        }

        @Override
        public void run() {
            int opcode = read();
            switch (opcode) {
                case CONNECTED:
                    opponentStatus = CONNECTED;
                    break;
                case DISCONNECTED:
                    opponentStatus = DISCONNECTED;
                    break;
                case READY_FOR_GAME:
                    opponentStatus = READY_FOR_GAME;
                    break;
                case IN_GAME:
                    opponentStatus = IN_GAME;
                    break;
                case PAUSE:
                    if (status == IN_GAME) {
                        status = opponentStatus = PAUSE;
                        panel.pause();
                    }
                    break;
                case RESUME:
                    if (status == PAUSE) {
                        status = opponentStatus = IN_GAME;
                        panel.resume();
                    }
                    break;
                case ERROR:
                    //TODO: implement error behavior
                case REQ_NAME:
                    messenger.sendName();
                    break;
                case REQ_VERSION:
                    Log.e("Unsupported", "version number not available right now");
                    throw new IllegalArgumentException();
                case NAME:
                    int nameLength = read();
                    byte[] nameBytes = read(nameLength);
                    String name;
                    try {
                        name = new String(nameBytes, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Log.i("READ_INPUT", "case Name: does not support UTF-8");
                        e.printStackTrace();
                        break;
                    }
                    if (enemyStatistic == null) {
                        final String connectedID = Bluetooth.getConnectedID();
                        AppData.getPlayerData().addStatistic(connectedID, name);
                        enemyStatistic = AppData.getPlayerData().get(connectedID);
                    } else if (!enemyStatistic.name.equals(name)) {
                        enemyStatistic.setName(name);
                    }
                    break;
                case BULLET:
                    if (status == IN_GAME) {
                        //Todo: let game panel add bullet
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
