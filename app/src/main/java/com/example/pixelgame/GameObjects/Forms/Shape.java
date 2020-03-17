package com.example.pixelgame.GameObjects.Forms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class Shape {
    public static final String TAG = "Collision";

    Point center;
    float degree;

    public Shape(Point center) {
        this(center, 0);
    }

    public Shape(Point center, float degree) {
        this.center = center;
        this.degree = degree;
    }

    public void rotate(float offset) {
        this.degree += offset;
    }

    public void rotateTo(float degree) {
        this.degree = degree;
    }

    public void move(Point vector) {
        move(vector.x, vector.y);
    }

    public void move(int xVector, int yVector) {
        center.offset(xVector, yVector);
    }

    public void moveTo(int x, int y) {
        moveTo(new Point(x, y));
    }

    public void moveTo(Point point) {
        this.center = point;
    }

    public static int squaredDistance(Point a, Point b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return dx * dx + dy * dy;
    }

    abstract public ShapeType getType();

    abstract public boolean detectCollision(Shape other);

    abstract public void draw(Canvas canvas, Paint color);

    abstract public int highest();

    abstract public int lowest();

    abstract public int rightest();

    abstract public int leftest();
}
