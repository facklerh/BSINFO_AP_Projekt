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
        byte ERROR = 5;
        byte PLACEHOLDER = 6;
        byte PLACEHOLDER1 = 7;
        byte PLACEHOLDER2 = 8;
        byte PLACEHOLDER3 = 9;
        byte PLACEHOLDER4 = 10;

        // Request opcodes

        byte REQ_VERSION = 32;
        byte REQ_NAME = 33;
        byte REQ_PH = 33;
        byte REQ_PH1 = 33;
        byte REQ_PH2 = 33;
        byte REQ_PH3 = 33;
        byte REQ_PH4 = 33;

        // Data opcode
        byte NAME_START = 64;
        byte NAME_END = 65;
        byte BULLET = 66;
        byte PLACEHOLDER5 = 7;
        byte PLACEHOLDER6 = 8;
        byte PLACEHOLDER7 = 9;
        byte PLACEHOLDER8 = 10;
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
