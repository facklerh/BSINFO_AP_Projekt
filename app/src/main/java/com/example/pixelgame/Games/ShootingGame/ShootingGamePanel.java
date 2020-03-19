package com.example.pixelgame.Games.ShootingGame;

import android.content.Context;

import com.example.pixelgame.Games.GamePanel;

public class ShootingGamePanel extends GamePanel {
    public ShootingGamePanel(Context context) {
        super(context);
        thread = new ShootingGameThread(null, null);
    }
}
