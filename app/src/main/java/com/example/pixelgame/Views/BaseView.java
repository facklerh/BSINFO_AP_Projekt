package com.example.pixelgame.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pixelgame.Data.AppData;
import com.example.pixelgame.R;

public abstract class BaseView extends AppCompatActivity {

    private float swipeStartY = 0f;
    private float swipeEndY = 0f;

    // Lifecycle activities


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUI_fullscreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUI_fullscreen();
    }

    private void setUI_fullscreen() {
        hideDecorView();
        hideActionBar();
    }

    private void hideDecorView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            int bitMask =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                bitMask |= View.SYSTEM_UI_FLAG_IMMERSIVE;
            }
            decorView.setSystemUiVisibility(bitMask);
        }
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onStop() {
        AppData.save();
        super.onStop();
    }

    // Listener

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                swipeStartY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                swipeEndY = event.getY();
                if (upSwipe()) {
                    setUI_fullscreen();
                }
        }
        return true;
    }

    private boolean upSwipe() {
        return swipeEndY < swipeStartY;
    }


    // helpful Methods

    public void launchActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        launchActivity(intent);
    }

    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    public Button initNavigationButton(int btnID, final Class cls) {
        return initButton(btnID, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(cls);
            }
        });
    }

    public Button initButton(int btnID, View.OnClickListener onClick) {
        Button button = findViewById(btnID);
        if (button != null) {
            button.setOnClickListener(onClick);
        }
        return button;
    }

    void showMessage(int msgID) {
        showMessage(msgID, Toast.LENGTH_SHORT);
    }

    void showMessage(String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    void showMessage(int msgID, int showTime) {
        Toast.makeText(this, msgID, showTime).show();
    }

    void showMessage(String msg, int showTime) {
        Toast.makeText(this, msg, showTime).show();
    }

    void showErrorDialog() {
        showErrorDialog(R.string.errM_default);
    }

    void showErrorDialog(int errorMessage) {
        showErrorDialog(R.string.error, errorMessage);
    }

    void showErrorDialog(int errorTitle, int errorMessage) {
        showErrorDialog(errorTitle, errorMessage, R.string.ok, null);
    }

    public void showErrorDialog(int errorTitle, int errorMessage, int buttonMessage, DialogInterface.OnClickListener onClick) {
        new AlertDialog.Builder(this)
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(buttonMessage, onClick)
                .create()
                .show();
    }

    void showExitDialog(int errorTitle, int errorMessage) {
        showErrorDialog(errorTitle, errorMessage, R.string.exit, exitOnClick());
    }

    DialogInterface.OnClickListener exitOnClick() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        };
    }
}
