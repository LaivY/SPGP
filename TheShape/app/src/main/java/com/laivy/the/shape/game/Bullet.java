package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.laivy.the.shape.framework.GameObject;

public class Bullet extends GameObject {
    private int dmg;
    private float angle;
    private float speed;
    private PointF direction;

    public Bullet() {
        dmg = 1;
        angle = 0.0f;
        speed = 2000.0f;
        direction = new PointF();
    }

    @Override
    public void update(float deltaTime) {
        position.offset(
            direction.x * speed * deltaTime,
            direction.y * speed * deltaTime
        );
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, position.x, position.y);
        super.draw(canvas);
        canvas.restore();
    }

    void setDirection(float x, float y) {
        direction.set(x, y);
        angle = (float) Math.toDegrees(Math.atan(x / -y));
        if (y > 0) {
            angle += 180.0f;
        }
    }
}
