package com.example.pixelgame;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Bluetooth.isNotSupported()) {
            showExitDialog(R.string.error_BT, R.string.errM_noBTSupport);
        } else {
            loadData();
            initButtons();
            launchActivity(GyroscopeUnit.class);
        }
    }

    private void loadData() {
        AppData.initSaveDirectory(getFilesDir());
        boolean dataExistent = AppData.load();
        if (!dataExistent) {
            initData();
        }
    }

    private void initData() {
        final EditText input = new EditText(this);
        final Context toastContext = this;

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.req_name)
                .setView(input)
                .setPositiveButton(R.string.ok, null)
                .create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = input.getText().toString();
                if (playerName.isEmpty()) {
                    Toast.makeText(toastContext, R.string.errM_nameEmpty, Toast.LENGTH_SHORT).show();
                } else {
                    AppData.init(playerName);
                    dialog.dismiss();
                }
            }
        });
    }

    private void initButtons() {
        initNavigationButton(R.id.btn_play, GyroscopeUnit.class);
        initNavigationButton(R.id.btn_settings, Settings.class);
        initNavigationButton(R.id.btn_statistic, Statistic.class);
    }
}
