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

public class Messenger {
    private static final String TAG = "WRITING_ERROR";
    private final OutputStream out;

    public Messenger(OutputStream out) {
        if (out == null)
            throw new IllegalArgumentException();
        this.out = out;
    }

    public void sendConnected() {
        try {
            out.write(CONNECTED);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendConnected");
            e.printStackTrace();
        }
    }

    public void sendReady() {
        try {
            out.write(READY_FOR_GAME);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendReady");
            e.printStackTrace();
        }
    }

    public void sendInGame() {
        try {
            out.write(IN_GAME);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendInGame");
            e.printStackTrace();
        }
    }

    public void sendDisconnected() {
        try {
            out.write(DISCONNECTED);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendDisconnected");
            e.printStackTrace();
        }
    }

    public void sendPause() {
        try {
            out.write(PAUSE);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendPause");
            e.printStackTrace();
        }
    }

    public void sendError() {
        try {
            out.write(ERROR);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "sendError");
            e.printStackTrace();
        }
    }

    public void requestName() {
        try {
            out.write(REQ_NAME);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "requestName");
            e.printStackTrace();
        }
    }

    public void requestVersion() {
        try {
            out.write(REQ_VERSION);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, "requestVersion");
            e.printStackTrace();
        }
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
