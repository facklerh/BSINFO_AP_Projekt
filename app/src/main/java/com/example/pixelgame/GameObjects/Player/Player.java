package com.example.pixelgame.GameObjects.Player;

import android.graphics.Paint;

import com.example.pixelgame.GameObjects.Bullet;
import com.example.pixelgame.GameObjects.Forms.Shape;
import com.example.pixelgame.GameObjects.GameObject;

public abstract class Player extends GameObject {

    final private int maxHealth;
    private int health;
    private int shots;

    public Player(Shape shape, Paint color, int health) {
        super(shape, color);
        this.maxHealth = this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float inflictDamage(int damage) {
        health -= Math.min(damage, health);
        updateColor();
        return health;
    }

    public Bullet shot() {
        return null;
    }

    public void updateSpeed(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed < 0 ? Math.max(xSpeed, -MAX_SPEED) : Math.min(xSpeed, MAX_SPEED);
        this.ySpeed = ySpeed < 0 ? Math.max(ySpeed, -MAX_SPEED) : Math.min(ySpeed, MAX_SPEED);
    }

    public void updateColor() {
        color.setAlpha(255 * health / maxHealth);
    }
}
