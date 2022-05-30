package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

public class Bullet extends GameObject {
    private int damage;
    private float lifeTime;
    private float degree;
    private float speed;
    private PointF direction;

    public Bullet() {
        damage = 1;
        lifeTime = 1.0f;
        degree = 0.0f;
        speed = 2000.0f;
        direction = new PointF();
        hitBox = new RectF(-5.0f, -50.0f, 5.0f, 50.0f);
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

        lifeTime -= deltaTime;
        if (lifeTime < 0.0f) {
            isValid = false;
            GameScene.getInstance().remove(GameScene.eLayer.BULLET, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;
        //canvas.drawRect(hitBox, paint);
        canvas.save();
        canvas.rotate(degree, position.x, position.y);
        super.draw(canvas);
        canvas.restore();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDirection(float x, float y) {
        direction.set(x, y);
        degree = (float) Math.toDegrees(Math.atan(x / -y));
        if (y > 0) {
            degree += 180.0f;
        }

        matrix.reset();
        matrix.postRotate(degree, hitBox.centerX(), hitBox.centerY());
        matrix.mapRect(hitBox);
    }

    int getDamage() {
        return damage;
    }

    PointF getDirection() {
        return direction;
    }
}
