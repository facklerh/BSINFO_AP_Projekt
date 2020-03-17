package com.example.pixelgame.GameObjects;

import android.graphics.Canvas;
import android.graphics.Point;

public interface GameObject {

    public void draw(Canvas canvas);

    public void update(Point point);
}
