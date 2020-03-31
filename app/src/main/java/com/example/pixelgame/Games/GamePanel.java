package com.example.pixelgame.Games;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    protected final GameThread thread;

    public GamePanel(Context context, GameThread thread) {
        super(context);
        this.thread = thread;
    }

    public void pause() {
        thread.onPause();
    }

    public void resume() {
        thread.onResume();
    }
}
