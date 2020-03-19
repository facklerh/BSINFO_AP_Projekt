package com.example.pixelgame.Games;

import android.content.Context;
import android.view.SurfaceView;

public abstract class GamePanel extends SurfaceView {
    protected GameThread thread;

    public GamePanel(Context context) {
        super(context);
    }

    public abstract void pause();

    public abstract void resume();
}
