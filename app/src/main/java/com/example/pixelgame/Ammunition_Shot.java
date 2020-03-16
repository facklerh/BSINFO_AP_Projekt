package com.example.pixelgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Ammunition_Shot implements GameObject{

    Rect shot;
    public Point point;
    int color = Color.BLACK;
    private final int strenght;
    private int xTarget;
    private int yTarget;
    private int xPosition;
    private int yPosition;
    private int rectangle_size;
    private int AMMUNITION_SIZE = 50;

    public Ammunition_Shot(int strength, int xPosition, int yPosition, int rectangle_size) {
        this.strenght = strength;
        //this.xTarget = xTarget;
        //this.yTarget = yTarget;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rectangle_size = rectangle_size;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(shot, paint);
    }

    @Override
    public void update(Point pointe) {
    if (point != null) {
        point.offset(0, -10);
        shot.set(point.x - shot.width()/2, point.y - shot.height()/2, point.x + shot.width()/2, point.y + shot.height()/2);
    }
    else {
        //xPosition = xPosition + //halbe munitionsgröße;
        //yPosition = yPosition + ;
        shot = new Rect(xPosition - AMMUNITION_SIZE, yPosition - (rectangle_size/2) -AMMUNITION_SIZE, xPosition + AMMUNITION_SIZE, yPosition - rectangle_size/2);
        point = new Point(xPosition , yPosition - rectangle_size/2 - AMMUNITION_SIZE/2);
    }
    }
}
