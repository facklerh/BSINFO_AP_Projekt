package com.example.pixelgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public abstract class BaseView extends AppCompatActivity {

    // Lifecycle activities

    @Override
    protected void onResume() {
        super.onResume();
        setUI_fullscreen();
    }

    private void setUI_fullscreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bluetooth.interruptAll();
        AppData.save();
    }

    // helpful Methods

    public void launchActivity(Class cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public Button initNavigationButton(int btnID, final Class cls){
        return initButton(btnID,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(cls);
            }
        });
    }

    public Button initButton(int btnID, View.OnClickListener onClick){
        Button button = findViewById(btnID);
        if(button!=null){
            button.setOnClickListener(onClick);
        }
        return button;
    }

    void showErrorDialog(){
        showErrorDialog(R.string.errM_default);
    }

    void showErrorDialog(int errorMessage){
        showErrorDialog(R.string.error,errorMessage);
    }

    void showErrorDialog(int errorTitle, int errorMessage){
        showErrorDialog(errorTitle,errorMessage,R.string.ok, null);
    }

    void showErrorDialog(int errorTitle, int errorMessage, int buttonMessage, DialogInterface.OnClickListener onClick){
        new AlertDialog.Builder(this)
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(buttonMessage, onClick)
                .create()
                .show();
    }

    void showExitDialog(int errorTitle, int errorMessage){
        showErrorDialog(errorTitle,errorMessage,R.string.exit, exitOnClick());
    }

    DialogInterface.OnClickListener exitOnClick(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        };
    }
}
