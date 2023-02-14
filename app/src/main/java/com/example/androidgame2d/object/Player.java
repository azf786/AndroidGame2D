package com.example.androidgame2d.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.androidgame2d.GameLoop;
import com.example.androidgame2d.R;
import com.example.androidgame2d.graphics.Sprite;

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private final Joystick joystick;
    private Sprite sprite;

    public Player(Context context, double positionX, double positionY, double radius, Joystick joystick, Sprite sprite) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        this.joystick = joystick;

        this.sprite = sprite;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, (int) getPositionX() - 64, (int) getPositionY() - 64);
    }

    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        positionX += velocityX;
        positionY += velocityY;
    }
}