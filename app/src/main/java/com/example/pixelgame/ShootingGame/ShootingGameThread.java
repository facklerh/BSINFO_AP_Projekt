package com.example.pixelgame.ShootingGame;

import android.util.Log;

import com.example.pixelgame.GameObjects.GameObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShootingGameThread extends Thread {

    private final static long FPS = 30;
    public static final int SECOND_IN_MILLIS = 1000;
    private final static long MILLIS_PER_FRAME = SECOND_IN_MILLIS / FPS;

    boolean won = false;
    boolean lost = false;

    GameObject player;
    List<GameObject> ownShots;
    List<GameObject> enemyShots;

    OutputStream output;

    public ShootingGameThread(OutputStream output, GameObject player) {
        this.output = output;
        this.player = player;

        ownShots = new ArrayList<>(10);
        enemyShots = new ArrayList<>(10);
    }

    @Override
    public void run() {
        while (!won || !lost) {
            final long tickStart = System.currentTimeMillis();
            update();
            render();
            final long tick = System.currentTimeMillis() - tickStart;
            addDelay(tick);
        }
    }

    private void update() {
        // TODO: update player position
        // TODO: update
    }

    private void render() {

    }

    private void addDelay(long duration) {
        Log.i("Performance", SECOND_IN_MILLIS / duration + " fps");
        if (duration < MILLIS_PER_FRAME) {
            try {
                Thread.sleep(MILLIS_PER_FRAME - duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Performance", "performance bad! need improvement");
        }
    }
}
