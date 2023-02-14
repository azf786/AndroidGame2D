package com.example.androidgame2d.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Circle extends GameObject {

    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);

        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
    }

    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if (distance < distanceToCollision) {
            return true;
        } else {
            return false;
        }
    }

    private double getRadius() {
        return this.radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
