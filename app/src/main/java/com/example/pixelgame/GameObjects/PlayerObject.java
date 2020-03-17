package com.example.pixelgame.GameObjects;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class PlayerObject implements GameObject {

    private float health;
    private int shots;
    public int color;
    Point playerpoint;
    Rect rectangle;
    final private float max_health;

    public PlayerObject(int color, int health, int shots) {   //Rectangle zu flexibler Form
        this.color = color;
        max_health = this.health = health;
        this.shots = shots;
    }

    public float getDamage(int damage) {
        health = health - damage;
        changeColor();
        return health;
    }

    public float getHealth() {
        return health;
    }

    public void changeColor() {
        float factor = this.health / this.max_health;
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        color = Color.argb(alpha, red, green, blue);
    }

}
