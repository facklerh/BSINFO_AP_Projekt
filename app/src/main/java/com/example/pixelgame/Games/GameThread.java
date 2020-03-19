package com.example.pixelgame.Games;

import android.util.Log;

import static com.example.pixelgame.Rules.FPS.MAX_MILLIS_PER_FRAME;
import static com.example.pixelgame.Rules.FPS.SECOND_IN_MILLIS;

public abstract class GameThread extends Thread {

    private boolean pause = false;

    public abstract void onPause();

    public abstract void onResume();

    protected void addDelay(long duration) {
        Log.i("Performance", SECOND_IN_MILLIS / duration + " fps");
        if (duration < MAX_MILLIS_PER_FRAME) {
            try {
                Thread.sleep(MAX_MILLIS_PER_FRAME - duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Performance", "performance bad! need improvement");
        }
    }
}
