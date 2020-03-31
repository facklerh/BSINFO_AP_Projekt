package com.example.pixelgame;

import android.util.Log;

import com.example.pixelgame.Data.AppData;
import com.example.pixelgame.GameObjects.Bullet;

import java.io.IOException;
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

public class Messenger {
    private static final String TAG = "WRITING_ERROR";
    private final OutputStream out;

    public Messenger(OutputStream out) {
        if (out == null)
            throw new IllegalArgumentException();
        this.out = out;
    }

    private void sendStatus(int status) {
        try {
            out.write(CONNECTED);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendStatus: " + status);
            e.printStackTrace();
        }
    }

    public void sendConnected() {
        sendStatus(CONNECTED);
    }

    public void sendDisconnected() {
        sendStatus(DISCONNECTED);
    }

    public void sendReady() {
        sendStatus(READY_FOR_GAME);
    }

    public void sendInGame() {
        sendStatus(IN_GAME);
    }

    public void sendPause() {
        sendStatus(PAUSE);
    }

    public void sendResume() {
        sendStatus(RESUME);
    }

    public void sendError() {
        sendStatus(ERROR);
    }

    public void requestName() {
        sendStatus(REQ_NAME);
    }

    public void requestVersion() {
        sendStatus(REQ_VERSION);
    }

    public void sendName() {
        byte[] nameBytes;
        try {
            nameBytes = AppData.getPlayerData().getPlayerName().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "sendName: does not support UTF-8");
            // TODO: decide to process with default encoding or to interrupt
            // nameBytes = AppData.getPlayerData().getPlayerName().getBytes();
            // or
            e.printStackTrace();
            return;
        }
        try {
            out.write(NAME);
            out.write(nameBytes.length);
            out.write(nameBytes);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendName");
            e.printStackTrace();
        }
    }

    public void sendBullet(Bullet bullet) {
        try {
            out.write(BULLET);
            out.write(bullet.toBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
