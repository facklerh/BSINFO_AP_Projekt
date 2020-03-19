package com.example.pixelgame.GameObjects.Forms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import static com.example.pixelgame.MsgUtil.concatBytes;
import static com.example.pixelgame.MsgUtil.floatFromBytes;
import static com.example.pixelgame.MsgUtil.getByteSection;
import static com.example.pixelgame.MsgUtil.intFromBytes;
import static com.example.pixelgame.MsgUtil.intToBytes;
import static com.example.pixelgame.Rules.SCREEN_WIDTH;

public class Circle extends Shape {
    public static final int BYTE_SIZE = 13; // type as byte(1) + relative x as int(4) + radius int(4)

    final int radius;

    public Circle(int x, int y, int radius) {
        this(new Point(x, y), radius);
    }

    public Circle(Point center, int radius) {
        super(center);
        this.radius = radius;
    }

    int squaredR() {
        return radius * radius;
    }

    @Override
    public ShapeType getType() {
        return ShapeType.Circle;
    }

    @Override
    public boolean detectCollision(Shape other) {
        switch (other.getType()) {
            case Circle:
                Circle circle = ((Circle) other);
                return squaredDistance(center, circle.center) <= squaredR() + circle.squaredR();
            case Tri:
                Log.e(TAG, "Circle collision with triangle not supported yet");
                return false;
            case Rect:
                Log.e(TAG, "Circle collision with rectangle not supported yet");
                return false;
            default:
                Log.e(TAG, "Circle collision with " + other.getType().name() + " not supported yet");
                return false;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint color) {
        canvas.drawCircle(center.x, center.y, radius, color);
    }

    @Override
    public int highest() {
        return center.y - radius;
    }

    @Override
    public int lowest() {
        return center.y + radius;
    }

    @Override
    public int rightest() {
        return center.x + radius;
    }

    @Override
    public int leftest() {
        return center.x + radius;
    }

    public static Circle fromBytes(byte[] bytes) {
        float relativeX = floatFromBytes(getByteSection(bytes, 1, 5));
        int radius = intFromBytes(getByteSection(bytes, 5, 9));
        return new Circle(new Point((int) (relativeX * SCREEN_WIDTH), -radius), radius);
    }

    @Override
    public byte[] toBytes() {
        byte[] superBytes = super.toBytes();
        byte[] radiusBytes = intToBytes(radius);
        return concatBytes(superBytes, radiusBytes);
    }
}
