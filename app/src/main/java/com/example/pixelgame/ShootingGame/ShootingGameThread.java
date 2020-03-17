package com.example.pixelgame.ShootingGame;

import android.util.Log;

import java.io.OutputStream;

public class GameThread extends Thread {

    private final static long FPS = 30;
    public static final int SECOND_IN_MILLIS = 1000;
    private final static long MILLIS_PER_FRAME = SECOND_IN_MILLIS / FPS;

    OutputStream output;

    boolean won = false;
    boolean lost = false;

    @Override
    public void run() {
        while (!won || !lost) {
            final long tickStart = System.currentTimeMillis();
            update();
            final long tick = System.currentTimeMillis() - tickStart;
            addDelay(tick);
            render();
        }
    }

    private void addDelay(long tickStart) {
        Log.i("Performance", SECOND_IN_MILLIS / tickStart + " fps");
        if (tick < MILLIS_PER_FRAME) {
            try {
                Thread.sleep(MILLIS_PER_FRAME - tick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {

    }

    private void render() {

    }
}
