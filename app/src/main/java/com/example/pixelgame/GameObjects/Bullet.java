package com.example.pixelgame.GameObjects;

import com.example.pixelgame.GameObjects.Forms.Shape;
import com.example.pixelgame.GameObjects.Forms.Square;
import com.example.pixelgame.Rules;

import static com.example.pixelgame.MsgUtil.concatBytes;
import static com.example.pixelgame.MsgUtil.getByteSection;
import static com.example.pixelgame.MsgUtil.intFromBytes;
import static com.example.pixelgame.MsgUtil.intToBytes;

public class Bullet extends GameObject implements Byteable {
    public static final int SPEED_BYTE_LENGTH = 8; // this does not include shape
    private static final int STANDARD_SIZE = 20;

    public Bullet(int x, int y, int xSpeed, int ySpeed) {
        super(new Square(x, y, STANDARD_SIZE));
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public Bullet(Shape shape, int xSpeed, int ySpeed) {
        super(shape);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getDamage() {
        return Rules.BULLET_DAMAGE;
    }

    public boolean willNotAppearOnEnemyScreen() {
        return shape.isOutOfScreenHorizontal();
    }

    public static Bullet fromBytes(byte[] speedBytes, byte[] shapeBytes) {
        if (speedBytes.length != SPEED_BYTE_LENGTH || shapeBytes.length < Shape.MIN_BYTE_SIZE) {
            throw new IllegalArgumentException();
        }
        Shape shape = Shape.fromBytes(shapeBytes);
        int xSpeed = intFromBytes(getByteSection(speedBytes, 0, 4));
        int ySpeed = intFromBytes(getByteSection(speedBytes, 4, 4));
        return new Bullet(shape, xSpeed, ySpeed);
    }

    @Override
    public byte[] toBytes() {
        byte[] shapeBytes = this.shape.toBytes();
        byte[] xSpeedBytes = intToBytes(-this.xSpeed);
        byte[] ySpeedBytes = intToBytes(-this.ySpeed);
        // byte[] colorBytes = intToBytes(this.color.getColor());
        return concatBytes(xSpeedBytes, ySpeedBytes, /*colorBytes,*/ shapeBytes);
    }
}
