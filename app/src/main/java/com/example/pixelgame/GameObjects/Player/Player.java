package com.example.pixelgame.GameObjects.Player;

import android.graphics.Paint;
import android.util.Log;

import com.example.pixelgame.GameObjects.Bullet;
import com.example.pixelgame.GameObjects.Forms.Shape;
import com.example.pixelgame.GameObjects.GameObject;

import static com.example.pixelgame.Rules.BORDER_BOTTOM;
import static com.example.pixelgame.Rules.BORDER_LEFT;
import static com.example.pixelgame.Rules.BORDER_RIGHT;
import static com.example.pixelgame.Rules.BORDER_TOP;
import static com.example.pixelgame.Rules.MAX_SPEED;

public abstract class Player extends GameObject {

    final private int maxHealth;
    private int health;
    // private int shots;
    // private int shotCoolDown;

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

    public void inflictDamage(int damage) {
        if (damage > 0) {
            health -= Math.min(damage, health);
            updateColor();
        } else {
            Log.e("ILLEGAL_ARGUMENT", "inflictDamage: tried to inflict invalid damage: " + damage);
        }
    }

    private void updateColor() {
        color.setAlpha(255 * health / maxHealth);
    }

    abstract public Bullet shoot(byte strength);

    @Override
    public void update() {
        super.update();
        if (shape.leftest() < BORDER_LEFT) {
            shape.move(BORDER_LEFT - shape.leftest(), 0);
        }
        if (shape.rightest() > BORDER_RIGHT) {
            shape.move(BORDER_RIGHT - shape.rightest(), 0);
        }
        if (shape.highest() < BORDER_TOP) {
            shape.move(0, BORDER_TOP - shape.highest());
        }
        if (shape.lowest() > BORDER_BOTTOM) {
            shape.move(0, BORDER_BOTTOM - shape.lowest());
        }
    }

    public void updateSpeed(byte xSpeed, byte ySpeed) {
        this.xSpeed = xSpeed < -MAX_SPEED ? -MAX_SPEED : (xSpeed > MAX_SPEED ? MAX_SPEED : xSpeed);
        this.ySpeed = ySpeed < -MAX_SPEED ? -MAX_SPEED : (ySpeed > MAX_SPEED ? MAX_SPEED : ySpeed);
    }
}
