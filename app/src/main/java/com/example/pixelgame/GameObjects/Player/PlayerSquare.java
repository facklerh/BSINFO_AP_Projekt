package com.example.pixelgame.GameObjects.Player;

import android.graphics.Paint;
import android.graphics.Point;

import com.example.pixelgame.GameObjects.Forms.Square;

public class PlayerSquare extends Player {

    public PlayerSquare(Point screenCenter, Paint color) {
        this(new Square(screenCenter, 25), color);
    }

    private PlayerSquare(Square square, Paint color) {
        super(square, color, 60);
    }
}
