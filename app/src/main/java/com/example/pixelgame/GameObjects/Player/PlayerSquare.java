package com.example.pixelgame.GameObjects.Player;

import android.graphics.Paint;
import android.graphics.Point;

import com.example.pixelgame.GameObjects.Bullet;
import com.example.pixelgame.GameObjects.Forms.Square;

import static com.example.pixelgame.Rules.Size;
import static com.example.pixelgame.Rules.getBorderCenter;

public class PlayerSquare extends Player {

    public PlayerSquare(Paint color) {
        this(new Square(getBorderCenter(), Size.SQUARE), color);
    }

    private PlayerSquare(Square square, Paint color) {
        super(square, color, 60);
    }

    @Override
    public Bullet shoot(byte strength) {
        final int halfSide = 4;
        final int y = this.shape.highest() - halfSide;
        final Square shape = new Square(new Point(this.shape.getCenterX(), y), halfSide * 2);
        final byte ySpeed = 3;
        return new Bullet(shape, this.xSpeed, ySpeed, strength);
    }
}
