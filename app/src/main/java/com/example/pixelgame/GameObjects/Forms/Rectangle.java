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

public class Rectangle extends Shape {
    public static final int BYTE_SIZE = 13; // type as byte(1) + relative x as int(4) + widthHalf int(4) + heightHalf int(4)

    final int widthHalf;
    final int heightHalf;

    public Rectangle(Point center, int width, int height) {
        this(center, width, height, 0);
    }

    public Rectangle(Point center, int width, int height, float degree) {
        super(center, degree);
        this.widthHalf = width / 2;
        this.heightHalf = height / 2;
    }

    public int highest() {
        return center.y - heightHalf;
    }

    public int lowest() {
        return center.y + heightHalf;
    }

    public int rightest() {
        return center.x + widthHalf;
    }

    public int leftest() {
        return center.x - widthHalf;
    }

    @Override
    public ShapeType getType() {
        return ShapeType.Rect;
    }

    @Override
    public boolean detectCollision(Shape other) {
        switch (other.getType()) {
            case Circle:
                Log.e(TAG, "Rectangle collision with circle not supported yet");
                return false;
            case Tri:
                Log.e(TAG, "Circle collision with triangle not supported yet");
                return false;
            case Rect:
                Rectangle rect = ((Rectangle) other);
                if (rect.rightest() < this.leftest()
                        || rect.leftest() > this.rightest()
                        || rect.highest() > this.lowest()
                        || rect.lowest() < this.highest()) {
                    return false;
                }
                return true;
            default:
                Log.e(TAG, "Circle collision with " + other.getType().name() + " not supported yet");
                return false;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint color) {
        // canvas.rotate(degree);
        canvas.drawRect(center.x - widthHalf, center.y - heightHalf, center.x + widthHalf, center.y + heightHalf, color);
        // canvas.rotate(-degree);
    }

    public static Rectangle fromBytes(byte[] bytes) {
        float relativeX = floatFromBytes(getByteSection(bytes, 1, 5));
        int widthHalf = intFromBytes(getByteSection(bytes, 5, 9));
        int heightHalf = intFromBytes(getByteSection(bytes, 9, BYTE_SIZE));
        return new Rectangle(new Point((int) (relativeX * SCREEN_WIDTH), -widthHalf), widthHalf * 2, heightHalf * 2);
    }

    @Override
    public byte[] toBytes() {
        byte[] superBytes = super.toBytes();
        byte[] widthHalfBytes = intToBytes(widthHalf);
        byte[] heightHalfBytes = intToBytes(heightHalf);
        return concatBytes(superBytes, widthHalfBytes, heightHalfBytes);
    }
}
