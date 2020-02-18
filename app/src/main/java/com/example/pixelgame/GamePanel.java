package com.example.pixelgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private DrawFigure player;
    private Point playerPoint;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);
        player = new DrawFigure(new Rect(100, 100, 200, 200), Color.BLACK);
        playerPoint = new Point(150, 150);
        //gameThread = new GameThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        playerPoint.set((int)event.getX(), (int)event.getY());
        return true;
    }

    public void update () {
        float s_roll = 0;
        float s_pitch = 0;
        float a = 1.1f;
        s_roll = a * GyroscopeUnit.roll;
        s_pitch = a * GyroscopeUnit.pitch;
        //playerPoint.set((int)s_pitch, (int)s_roll);
        playerPoint.offset((int)s_pitch, (int)s_roll);
        //canvas.drawRect(canvas.getWidth() / 2 - rect_width + s_pitch, canvas.getHeight() / 2 + rect_height + s_roll, canvas.getWidth() / 2 + rect_width + s_pitch, canvas.getHeight() / 2 - rect_height + s_roll, paint);
        player.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
    }
}
