package com.example.pixelgame.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.pixelgame.GameObjects.Forms.Shape;

public abstract class GameObject {
    public final Shape shape;
    public Paint color;

    public int xSpeed;
    public int ySpeed;

    public GameObject(Shape shape) {
        this(shape, new Paint(Color.BLACK));
    }

    public GameObject(Shape shape, Paint color) {
        this.shape = shape;
        this.color = color;
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
