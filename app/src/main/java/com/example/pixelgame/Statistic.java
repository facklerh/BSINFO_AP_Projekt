package com.example.pixelgame;

import android.os.Bundle;
import android.widget.TextView;

public class Statistic extends BaseView {

    PlayerData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        data = AppData.getPlayerData();
        TextView name = findViewById(R.id.playerName);
        name.setText(data.playerName);
    }
}
