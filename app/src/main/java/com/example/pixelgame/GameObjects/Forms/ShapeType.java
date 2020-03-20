package com.example.pixelgame.GameObjects.Forms;

public enum ShapeType {
    Circle, Rect, Square, Tri;

    public static ShapeType fromByte(byte ordinal) {
        if (ordinal < 0 || ordinal > values().length)
            return null;
        return values()[((int) ordinal)];
    }

    public byte asByte() {
        return ((byte) this.ordinal());
    }
}
