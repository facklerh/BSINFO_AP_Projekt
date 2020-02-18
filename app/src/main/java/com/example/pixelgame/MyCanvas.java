package com.example.pixelgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MyCanvas extends View {

    Paint paint;
    Rect rect;
    public Canvas canvas;

    int rect_height = 30;
    int rect_width = 30;

    long time_last = 0;
    long time_now = 0;

    public MyCanvas (Context context) {
        super (context);
        paint = new Paint();
        rect = new Rect();
    }










    public void getPitchAndRoll() {
        Log.wtf("#############", "CanvasPitch: " + GyroscopeUnit.pitch);
        Log.wtf("#############", "CanvasRoll: " + GyroscopeUnit.roll);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float a = 1.01f;  // Acceleration     v=a*t
        float v_X;  // Speed
        float v_Y;
        float s_roll = 0;
        float s_pitch = 0;
        int t;  // Time

        this.canvas = canvas;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        long tInMs = askTime();
        long delta_t = tInMs - time_last;
        s_roll = a * GyroscopeUnit.roll * delta_t; // Unterschied zwischen jetzt und letztem draw
        s_pitch = a * GyroscopeUnit.pitch * delta_t;
        int count = 0;

        //draw
        //canvas.drawRect(0, 0, 50, 50, paint);
        if (count <= 100) {
            canvas.drawRect(canvas.getWidth() / 2 - rect_width + s_pitch, canvas.getHeight() / 2 + rect_height + s_roll, canvas.getWidth() / 2 + rect_width + s_pitch, canvas.getHeight() / 2 - rect_height + s_roll, paint);
            count = 0;
        }
        else {
            count++;
        }
        time_last = tInMs;

        invalidate();

        //canvas.drawRect(canvas.getWidth()/2-rect_width+s_pitch, canvas.getHeight()/2+rect_height + s_roll, canvas.getWidth()/2+rect_width + s_pitch,canvas.getHeight()/2-rect_height + s_roll, paint);
        //nach Hinten : Roll +90
        //nach Vorne : Roll -90
        //nach rechts : Pitch +90
        //nach links : Pitch -90


        //draw

        //canvas.drawRect(canvas.getWidth()/2-rect_width, canvas.getHeight()/2+rect_height, canvas.getWidth()/2+rect_width, canvas.getHeight()/2-rect_height, paint);
        //canvas.drawRect(0, 0, 50, 50, paint);
//      canvas.drawText();
    }

    public long askTime () {
        Calendar calendar = Calendar.getInstance();
        Long timeInMs = calendar.getTimeInMillis();
        Log.wtf("---------", "Time in MS: " + timeInMs);
        return timeInMs;
    }
}
