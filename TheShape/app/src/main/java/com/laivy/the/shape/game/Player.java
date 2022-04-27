package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class Player extends GameObject {
    private final float MAX_ROTATE_DEGREE = 90.0f;
    private boolean isMove;
    private int hp;
    private int exp;
    private int dmg;
    private float fireSpeed;
    private float fireTimer;
    private float remainRotateDegree;
    private float currRotateDegree;
    private float speed;
    private PointF direction;

    public Player() {
        isMove = false;
        hp = R.dimen.PLAYER_HP;
        exp = 0;
        dmg = R.dimen.PLAYER_DMG;
        fireSpeed = Metrics.getFloat(R.dimen.PLAYER_FIRE_SPEED);
        fireTimer = 0.0f;
        speed = Metrics.getFloat(R.dimen.PLAYER_SPEED);
        direction = new PointF(0.0f, 0.0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isMove = false;
                break;
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                break;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        // 회전해야할 각도가 남아있다면 회전
        if (remainRotateDegree > 0.0f) {
            remainRotateDegree -= MAX_ROTATE_DEGREE * deltaTime;
            currRotateDegree += MAX_ROTATE_DEGREE * deltaTime;
        }

        // 이동, dstRect 최신화
        if (isMove) {
            position.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
            super.update(deltaTime);
        }

        // 총 발사
        if (fireTimer >= fireSpeed) fire();
        fireTimer += deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(currRotateDegree, position.x, position.y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void fire() {
        if (direction.x == 0.0f && direction.y == 0.0f)
            return;

        Bullet bullet = new Bullet();
        bullet.setBitmap(R.mipmap.player);
        bullet.setPosition(position.x, position.y);
        bullet.setDirection(direction.x, direction.y);
        GameScene.getInstance().add(bullet);

        fireTimer = 0.0f;
    }

    public void setDirection(float x, float y) {
        if (x == 0.0f && y == 0.0f) {
            direction.set(0.0f, 0.0f);
            return;
        }

        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        direction.set(x, y);
    }
}
