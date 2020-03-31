package com.example.pixelgame;

import java.nio.ByteBuffer;

public final class MsgUtil {
    private MsgUtil() {
    }

    public interface Opcodes {
        // State opcodes
        byte CONNECTED = 0;
        byte DISCONNECTED = 1;
        byte READY_FOR_GAME = 2;
        byte IN_GAME = 3;
        byte PAUSE = 4;
        byte PLACEHOLDER = 5;
        byte PLACEHOLDER0 = 6;
        byte PLACEHOLDER1 = 7;
        byte PLACEHOLDER2 = 8;
        byte PLACEHOLDER3 = 9;
        byte PLACEHOLDER4 = 10;

        // Error opcodes
        byte ERROR = 16;

        // Request opcodes

        byte REQ_VERSION = 32;
        byte REQ_NAME = 33;
        byte REQ_PH = 34;
        byte REQ_PH1 = 35;
        byte REQ_PH2 = 36;
        byte REQ_PH3 = 37;
        byte REQ_PH4 = 38;

        // Data opcode
        byte NAME = 127;
        byte BULLET = 66;
        byte PLACEHOLDER5 = 67;
        byte PLACEHOLDER6 = 68;
        byte PLACEHOLDER7 = 69;
        byte PLACEHOLDER8 = 70;


    }

    public static byte[] addOpcode(byte op, byte[] bytes) {
        final byte[] result = new byte[bytes.length + 1];
        result[0] = op;
        System.arraycopy(bytes, 0, result, 1, bytes.length);
        return result;
    }

    public static byte[] concatBytes(byte[]... byteArrays) {
        int completeLength = 0;
        for (byte[] byteArray : byteArrays) {
            completeLength += byteArray.length;
        }
        byte[] result = new byte[completeLength];
        int curIdx = 0;
        for (byte[] byteArray : byteArrays) {
            System.arraycopy(byteArray, 0, result, curIdx, byteArray.length);
            curIdx += byteArray.length;
        }
        return result;
    }

    public static byte[] getByteSection(byte[] bytes, int start) {
        if (start >= bytes.length)
            throw new IllegalArgumentException();
        return getByteSection(bytes, start, bytes.length - start);
    }

    public static byte[] getByteSection(byte[] bytes, int start, int length) {
        if (start < 0 || start + length > bytes.length)
            throw new IllegalArgumentException();
        byte[] result = new byte[length];
        System.arraycopy(bytes, start, result, 0, length);
        return result;
    }

    public static byte[] intToBytes(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public static int intFromBytes(byte[] bytes) {
        if (bytes.length != 4)
            throw new IllegalArgumentException();
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] floatToBytes(float i) {
        return ByteBuffer.allocate(4).putFloat(i).array();
    }

    public static float floatFromBytes(byte[] bytes) {
        if (bytes.length != 4)
            throw new IllegalArgumentException();
        return ByteBuffer.wrap(bytes).getFloat();
    }
}
