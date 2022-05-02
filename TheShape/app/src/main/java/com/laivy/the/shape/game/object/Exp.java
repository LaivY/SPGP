package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Utility;

public class Exp extends GameObject {
    private int exp;
    private float timer;
    private float speed;
    private PointF direction;

    public Exp() {
        exp = 0;
        timer = 0.5f;
        speed = 30.0f;
        direction = new PointF();
        direction.x = Utility.getRandom(-1.0f, 1.0f);
        direction.y = Utility.getRandom(-1.0f, 1.0f);

        float length = direction.length();
        direction.x = direction.x / length;
        direction.y = direction.y / length;

        hitBox = new RectF(-50.0f, -50.0f, 50.0f, 50.0f);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;
        if (timer < 0.0f) return;

        position.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
        hitBox.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
        super.update(deltaTime);

        timer -= deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;
        canvas.drawRect(hitBox, paint);
        super.draw(canvas);
    }
}
