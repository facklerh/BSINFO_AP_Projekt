package com.example.pixelgame;

import java.nio.ByteBuffer;

public final class MsgUtil {
    private MsgUtil() {
    }

    public interface Opcodes {
        interface General {

        }

        interface ShootingGame {

        }

        // State opcodes
        int CONNECTED = 0;
        int DISCONNECTED = 1;
        int READY_FOR_GAME = 2;
        int IN_GAME = 3;
        int PAUSE = 4;
        int RESUME = 5;
        int PLACEHOLDER0 = 6;
        int PLACEHOLDER1 = 7;
        int PLACEHOLDER2 = 8;
        int PLACEHOLDER3 = 9;
        int PLACEHOLDER4 = 10;

        // Error opcodes
        int ERROR = 16;

        // Request opcodes

        int REQ_VERSION = 32;
        int REQ_NAME = 33;
        int REQ_PH = 34;
        int REQ_PH1 = 35;
        int REQ_PH2 = 36;
        int REQ_PH3 = 37;
        int REQ_PH4 = 38;

        // Data opcode
        int NAME = 65;
        int BULLET = 66;
        int PLACEHOLDER5 = 67;
        int PLACEHOLDER6 = 68;
        int PLACEHOLDER7 = 69;
        int PLACEHOLDER8 = 70;


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
