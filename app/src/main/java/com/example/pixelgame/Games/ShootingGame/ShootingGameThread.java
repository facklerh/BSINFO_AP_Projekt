package com.example.pixelgame.Games.ShootingGame;

import android.graphics.Canvas;

import com.example.pixelgame.GameObjects.Bullet;
import com.example.pixelgame.GameObjects.Player.Player;
import com.example.pixelgame.Games.GameThread;
import com.example.pixelgame.Messenger;

import java.util.ArrayList;
import java.util.List;

public class ShootingGameThread extends GameThread {
    Player player;
    List<Bullet> ownShots;
    List<Bullet> enemyShots;

    public ShootingGameThread(Canvas canvas, Messenger messenger, Player player) {
        super(canvas, messenger);
        this.player = player;

        ownShots = new ArrayList<>(10);
        enemyShots = new ArrayList<>(10);
    }

    @Override
    protected void update() {
        player.update();
        updateEnemyShots();
        updateOwnShots();
    }

    private void updateEnemyShots() {
        for (int i = enemyShots.size() - 1; i >= 0; i--) {
            final Bullet shot = enemyShots.get(i);
            shot.update();
            if (player.detectCollision(shot)) {
                player.inflictDamage(shot.strength);
                enemyShots.remove(i);
                if (player.isDead()) {
                    lost = true;
                }
            }
        }
    }

    private void updateOwnShots() {
        for (int i = ownShots.size() - 1; i >= 0; i--) {
            final Bullet shot = ownShots.get(i);
            shot.update();
            if (!shot.isVisible()) {
                if (shot.willAppearOnEnemyScreen())
                    messenger.sendBullet(shot);
                ownShots.remove(i);
            }
        }
    }

    @Override
    protected void render(Canvas canvas) {
        player.draw(canvas);
        for (Bullet shot : ownShots) {
            shot.draw(canvas);
        }
        for (Bullet shot : enemyShots) {
            shot.draw(canvas);
        }
    }

    @Override
    protected void onVictory() {

    }

    @Override
    protected void onDefeat() {

    }
}
