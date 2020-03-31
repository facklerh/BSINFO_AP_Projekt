package com.example.pixelgame.Games.ShootingGame;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.pixelgame.Games.GamePanel;

public class ShootingGamePanel extends GamePanel {
    public ShootingGamePanel(Context context) {
        super(context, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
