package com.example.pixelgame.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.pixelgame.GameObjects.Forms.Shape;

public abstract class GameObject {
    public final Shape shape;
    public Paint color;

    public int xSpeed = 0;
    public int ySpeed = 0;
    public static final int MAX_SPEED = 15;
    public static final int MAX_SPEED_SQUARED = MAX_SPEED * MAX_SPEED;

    public GameObject(Shape shape) {
        this(shape, new Paint(Color.BLACK));
    }

    public GameObject(Shape shape, Paint color) {
        this.shape = shape;
        this.color = color;
    }

    public boolean isVisible() {
        return shape.isVisible();
    }

    public boolean detectCollision(GameObject other) {
        return detectCollision(other.shape);
    }

    public boolean detectCollision(Shape other) {
        return shape.detectCollision(other);
    }

    public void update() {
        shape.move(xSpeed, ySpeed);
    }

    public void draw(Canvas canvas) {
        shape.draw(canvas, color);
    }

}
