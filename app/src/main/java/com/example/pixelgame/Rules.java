package com.example.pixelgame;

import android.content.res.Resources;

public class Rules {

    public static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    // Border

    public static int BORDER_LEFT = 0;
    public static int BORDER_RIGHT = SCREEN_WIDTH;
    public static int BORDER_TOP = 0;
    public static int BORDER_BOTTOM = SCREEN_HEIGHT;

    public static void setBordersDefault() {
        setBorders(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT);
    }

    public static void setBorders(int left, int right, int top, int bottom) {
        setBorderLeft(left);
        setBorderRight(right);
        setBorderTop(top);
        setBorderBottom(bottom);
    }

    public static void setBorderLeft(int borderLeft) {
        BORDER_LEFT = borderLeft;
    }

    public static void setBorderRight(int borderRight) {
        BORDER_RIGHT = borderRight;
    }

    public static void setBorderTop(int borderTop) {
        BORDER_TOP = borderTop;
    }

    public static void setBorderBottom(int borderBottom) {
        BORDER_BOTTOM = borderBottom;
    }

    // FPS
    public interface FPS {
        long MAX_FPS = 30;
        long SECOND_IN_MILLIS = 1000;
        long MAX_MILLIS_PER_FRAME = SECOND_IN_MILLIS / MAX_FPS;
    }
}
