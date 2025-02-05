package com.example.pixelgame.Views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.example.pixelgame.Bluetooth;
import com.example.pixelgame.Data.AppData;
import com.example.pixelgame.GyroSensor;
import com.example.pixelgame.R;
import com.example.pixelgame.Views.Gyroscope.GyroscopeUnit;

public class MainActivity extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!GyroSensor.initGyroSensor(this)) {
            showExitDialog(R.string.error_Sensor, R.string.errM_noSensorSupport);
        } else if (Bluetooth.isNotSupported()) {
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
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(31)});

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
                    showMessage(R.string.errM_nameEmpty);
                } else if (playerName.getBytes().length > Byte.MAX_VALUE) {
                    showMessage(R.string.errM_nameTooLong);
                } else {
                    AppData.init(playerName);
                    dialog.dismiss();
                }
            }
        });
    }

    private void initButtons() {
        initNavigationButton(R.id.btn_play, ConnectionActivity.class);
        initNavigationButton(R.id.btn_settings, Settings.class);
        initNavigationButton(R.id.btn_statistic, StatisticActivity.class);
    }
}
