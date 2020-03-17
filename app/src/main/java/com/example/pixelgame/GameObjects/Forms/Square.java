package com.example.pixelgame.GameObjects.Forms;

import android.graphics.Point;

public class Square extends Rectangle {
    public Square(int x, int y, int side) {
        super(new Point(x, y), side, side);
    }

    public Square(Point center, int side) {
        super(center, side, side);
    }
}
