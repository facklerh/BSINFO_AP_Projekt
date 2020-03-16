package com.example.pixelgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int RECTANGLE_SIZE = 200;
    public static final int rectangle_HalfSize = RECTANGLE_SIZE /2;
    public static final int BORDERDIST = 1;

    private GameThread gameThread;
    private DrawFigure player2;
    private Point playerPoint;
    private PlayerObject player;
    private Ammunition_Shot shot;

    private List<Ammunition_Shot> shots = new ArrayList<>();

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);
        System.out.println("Init " + playerPoint);
        //player = new PlayerRectangle(Color.RED);
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
        Log.wtf("TAG", event.toString());
        switch (event.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                break;
                //Log.wtf("TAG", event.toString());
            case MotionEvent.ACTION_UP:
                //Log.wtf("TAG", event.toString());
                shots.add(new Ammunition_Shot(1, playerPoint.x, playerPoint.y, RECTANGLE_SIZE));
                long startTime = System.currentTimeMillis();
                // bis endtime 300ms
                //shot = new Ammunition_Shot(1, playerPoint.x, playerPoint.y, RECTANGLE_SIZE);

                //player.getDamage(10);
                //Log.wtf("TAG", "Pushed: + " + player.getHealth());
                return true;
        }
        //playerPoint.set((int)event.getX(), (int)event.getY());
        return true;
    }

    public void update (Canvas canvas) {
        float s_roll = 0;
        float s_pitch = 0;
        float a = 1.01f;
        s_roll = a * GyroscopeUnit.roll;
        s_pitch = a * GyroscopeUnit.pitch;
        //playerPoint.set((int)s_pitch, (int)s_roll);

        if (playerPoint != null) {

            playerPoint.offset((int) s_pitch, (int) s_roll);

            if (playerPoint.x <= rectangle_HalfSize) {
                playerPoint.x = rectangle_HalfSize + BORDERDIST;

            }
            if (playerPoint.y <= rectangle_HalfSize) {
                playerPoint.y = rectangle_HalfSize + BORDERDIST;
            }
            if (playerPoint.x >= canvas.getWidth() - (rectangle_HalfSize)) {
                playerPoint.x = canvas.getWidth() - (rectangle_HalfSize + BORDERDIST);
            }
            if (playerPoint.y >= canvas.getHeight() - (rectangle_HalfSize)) {
                playerPoint.y = canvas.getHeight() - (rectangle_HalfSize + BORDERDIST);
            }
            //canvas.drawRect(canvas.getWidth() / 2 - rect_width + s_pitch, canvas.getHeight() / 2 + rect_height + s_roll, canvas.getWidth() / 2 + rect_width + s_pitch, canvas.getHeight() / 2 - rect_height + s_roll, paint);
            System.out.println(playerPoint);
            System.out.println(playerPoint.x);
            System.out.println(playerPoint.y);

            //if collision, call getDamage(), if return value < 0 -> gameover
            player.update(playerPoint);
            //shot.update(shot.point);
            for (Ammunition_Shot shot : shots) {
                shot.update(shot.point);
            }
            // bei kollision objekt zerstören
        }
        else {
            int x = canvas.getWidth() / 2;
            int y = canvas.getHeight() / 2;
            //player = new DrawFigure(new Rect(x - rectangle_HalfSize, y - rectangle_HalfSize, x + rectangle_HalfSize,  y + rectangle_HalfSize), Color.BLACK);
            //player = new PlayerRectangle(Color.RED, new Rect((x - rectangle_HalfSize, y - rectangle_HalfSize, x + rectangle_HalfSize,  y + rectangle_HalfSize), Color.RED));
            player = new PlayerRectangle(Color.RED, new Rect(x - rectangle_HalfSize, y - rectangle_HalfSize, x + rectangle_HalfSize,  y + rectangle_HalfSize));
            playerPoint = new Point(x, y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
        for (Ammunition_Shot shot : shots) {
            shot.draw(canvas);
        }
        //shot.draw(canvas);
    }
}
