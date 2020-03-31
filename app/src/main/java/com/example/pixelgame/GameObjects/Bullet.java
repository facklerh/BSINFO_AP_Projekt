package com.example.pixelgame.GameObjects;

import com.example.pixelgame.GameObjects.Forms.Shape;
import com.example.pixelgame.GameObjects.Forms.Square;

import static com.example.pixelgame.MsgUtil.concatBytes;
import static com.example.pixelgame.MsgUtil.getByteSection;

public class Bullet extends GameObject implements Byteable {
    public static final int REMAINING_BYTE_LENGTH = 3; // x and y Speed + strength | this does not include shape
    private static final int STANDARD_SIZE = 20;

    public final byte strength;

    public Bullet(int x, int y, byte xSpeed, byte ySpeed, byte strength) {
        super(new Square(x, y, STANDARD_SIZE));
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.strength = strength;
    }

    public Bullet(Shape shape, byte xSpeed, byte ySpeed, byte strength) {
        super(shape);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.strength = strength;
    }

    public boolean willNotAppearOnEnemyScreen() {
        return shape.isOutOfScreenHorizontal();
    }

    public static Bullet fromBytes(byte[] bulletBytes) {
        byte[] remainingBytes = getByteSection(bulletBytes, 0, REMAINING_BYTE_LENGTH);
        byte[] shapeBytes = getByteSection(bulletBytes, REMAINING_BYTE_LENGTH);
        return fromBytes(remainingBytes, shapeBytes);
    }

    public static Bullet fromBytes(byte[] remainingBytes, byte[] shapeBytes) {
        if (remainingBytes.length != REMAINING_BYTE_LENGTH || shapeBytes.length < Shape.MIN_BYTE_SIZE) {
            throw new IllegalArgumentException();
        }
        Shape shape = Shape.fromBytes(shapeBytes);
        byte xSpeed = remainingBytes[0];
        byte ySpeed = remainingBytes[1];
        byte strength = remainingBytes[2];
        return new Bullet(shape, xSpeed, ySpeed, strength);
    }

    @Override
    public byte[] toBytes() {
        byte[] shapeBytes = this.shape.toBytes();
        byte[] remainingBytes = new byte[]{xSpeed, ySpeed, strength};
        // byte[] colorBytes = intToBytes(this.color.getColor());
        return concatBytes(remainingBytes, /*colorBytes,*/ shapeBytes);
    }
}
