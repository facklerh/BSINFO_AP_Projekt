package com.example.pixelgame.Games.ShootingGame;

import android.graphics.Canvas;

import com.example.pixelgame.GameObjects.Bullet;
import com.example.pixelgame.GameObjects.Player.Player;
import com.example.pixelgame.Games.GameThread;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShootingGameThread extends GameThread {

    boolean won = false;
    boolean lost = false;

    Player player;
    List<Bullet> ownShots;
    List<Bullet> enemyShots;

    Canvas canvas;

    OutputStream output;

    public ShootingGameThread(OutputStream output, Player player) {
        this.output = output;
        this.player = player;

        ownShots = new ArrayList<>(10);
        enemyShots = new ArrayList<>(10);
    }

    @Override
    public void run() {
        while (!won || !lost) {
            final long tickStart = System.currentTimeMillis();
            update();
            render();
            final long tick = System.currentTimeMillis() - tickStart;
            addDelay(tick);
        }
    }

    private void update() {
        player.update();
        for (Bullet shot : enemyShots) {
            shot.update();
            if (player.detectCollision(shot)) {
                player.inflictDamage(shot.getDamage());
            }
        }
        for (Bullet ownShot : ownShots) {
            ownShot.update();
            // TODO: Check if shot left screen in enemy screen dir
        }
    }

    private void render() {
        player.draw(canvas);
        for (Bullet shot : ownShots) {
            shot.draw(canvas);
        }
        for (Bullet shot : enemyShots) {
            shot.draw(canvas);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
