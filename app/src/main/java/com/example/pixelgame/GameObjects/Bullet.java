package com.example.pixelgame.GameObjects;

import android.graphics.Paint;

import com.example.pixelgame.GameObjects.Forms.Shape;
import com.example.pixelgame.GameObjects.Forms.Square;

public class Bullet extends GameObject {
    public Bullet(int xSpeed, int ySpeed) {
        super(new Square(0, 0, 4));
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public Bullet(Shape shape, int xSpeed, int ySpeed) {
        super(shape);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public Bullet(Shape shape, Paint color, int xSpeed, int ySpeed) {
        super(shape, color);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
}
