package com.example.pixelgame;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WaitingArea extends BaseView {

    InputStream input;
    OutputStream output;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!Bluetooth.connected())
            finish();

        super.onCreate(savedInstanceState);

        try {
            input = Bluetooth.getInputStream();
            output = Bluetooth.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }
}
