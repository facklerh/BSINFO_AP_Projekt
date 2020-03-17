package com.example.pixelgame.GameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PlayerRectangle extends PlayerObject {

    public PlayerRectangle(int color, Rect rect) {
        super(color, 60, 6);
        rectangle = rect;
    }

    public void shoot() {
        // new Rectangle...
        //direction, position, power

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    /*@Override
    public void update() {

    }*/

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }
}
