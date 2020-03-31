package com.example.pixelgame.Games;

import android.graphics.Canvas;
import android.util.Log;

import com.example.pixelgame.Messenger;

import static com.example.pixelgame.Rules.FPS.MAX_MILLIS_PER_FRAME;
import static com.example.pixelgame.Rules.FPS.SECOND_IN_MILLIS;

public abstract class GameThread extends Thread {
    protected final Messenger messenger;
    private final Canvas canvas;
    private final long millisPerFrame;

    protected boolean won = false;
    protected boolean lost = false;

    private boolean pause = false;

    protected GameThread(Canvas canvas, Messenger messenger) {
        this(canvas, messenger, MAX_MILLIS_PER_FRAME);
    }

    protected GameThread(Canvas canvas, Messenger messenger, long millisPerFrame) {
        this.canvas = canvas;
        this.messenger = messenger;
        this.millisPerFrame = millisPerFrame;
    }

    @Override
    public void run() {
        while (!won || !lost) {
            final long tickStart = System.currentTimeMillis();
            update();
            render();
            final long tick = System.currentTimeMillis() - tickStart;
            addDelay(tick);
            checkPause();
        }

        if (lost) {
            onDefeat();
        } else {
            onVictory();
        }
    }

    protected abstract void update();

    private void render() {
        render(canvas);
    }

    protected abstract void render(Canvas canvas);

    protected void addDelay(long duration) {
        Log.i("Performance", SECOND_IN_MILLIS / duration + " fps");
        if (duration < millisPerFrame) {
            try {
                Thread.sleep(millisPerFrame - duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Performance", "performance bad! need improvement");
        }
    }

    private void checkPause() {
        synchronized (this) {
            while (pause) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onPause() {
        this.pause = true;
    }

    public void onResume() {
        this.pause = false;
        synchronized (this) {
            notify();
        }
    }

    protected abstract void onVictory();

    protected abstract void onDefeat();
}
