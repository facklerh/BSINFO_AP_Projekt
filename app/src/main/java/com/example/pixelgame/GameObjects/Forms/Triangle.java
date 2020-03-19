package com.example.pixelgame.GameObjects.Forms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

public class Triangle extends Shape {
    public static final int BYTE_SIZE = 13; // type as byte(1) + relative x as int(4) + radius int(4)

    Point top;
    Point left;
    Point right;


    public Triangle(Point center) {
        super(center);
    }

    @Override
    public ShapeType getType() {
        return ShapeType.Tri;
    }

    @Override
    public boolean detectCollision(Shape other) {
        switch (other.getType()) {
            case Circle:
            case Tri:
            case Rect:
            default:
                Log.e(TAG, "Triangle collision with " + other.getType().name() + " not supported yet");
                throw new IllegalStateException();
        }
    }

    @Override
    public void draw(Canvas canvas, Paint color) {
        //canvas.rotate(degree);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        path.moveTo(top.x, top.y);
        path.lineTo(right.x, right.y);
        path.lineTo(left.x, left.y);
        path.close();

        canvas.drawPath(path, color);

        //canvas.rotate(-degree);
    }

    @Override
    public int highest() {
        return top.y;
    }

    @Override
    public int lowest() {
        return right.y;
    }

    @Override
    public int rightest() {
        return right.x;
    }

    @Override
    public int leftest() {
        return left.x;
    }

    @Override
    public void move(Point vector) {
        move(vector.x, vector.y);
    }

    @Override
    public void move(int xVector, int yVector) {
        super.move(xVector, yVector);
        top.offset(xVector, yVector);
        left.offset(xVector, yVector);
        right.offset(xVector, yVector);
    }

    @Override
    public void moveTo(Point point) {
        move(center.x - point.x, center.y - point.y);
    }

    @Override
    public void moveTo(int x, int y) {
        super.moveTo(center.x - x, center.y - y);
    }

    public static Triangle fromBytes(byte[] bytes) {
        throw new IllegalStateException();
        // return null;
    }

    @Override
    public byte[] toBytes() {
        throw new IllegalStateException();
        // byte[] superBytes = super.toBytes();
        // return concatBytes();
    }
}
