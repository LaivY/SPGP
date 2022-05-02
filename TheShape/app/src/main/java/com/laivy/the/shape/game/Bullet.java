package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;

public class Bullet extends GameObject {
    private int dmg;
    private float angle;
    private float speed;
    private PointF direction;

    public Bullet() {
        dmg = 1;
        angle = 0.0f;
        speed = 1500.0f;
        direction = new PointF();
        hitBox = new RectF(-5.0f, -5.0f, 5.0f, 5.0f);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;

        position.offset(
            direction.x * speed * deltaTime,
            direction.y * speed * deltaTime
        );
        hitBox.offset(
            direction.x * speed * deltaTime,
            direction.y * speed * deltaTime
        );
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;
        canvas.drawRect(hitBox, paint);
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
