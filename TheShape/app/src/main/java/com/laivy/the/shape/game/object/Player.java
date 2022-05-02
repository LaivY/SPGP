package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.GameScene;

public class Player extends GameObject {
    private final float MAX_ROTATE_DEGREE = 180.0f;
    private boolean isMove;
    private int hp;
    private int exp;
    private int level;
    private int dmg;
    private float fireSpeed;
    private float fireTimer;
    private float targetRotateDegree;
    private float currRotateDegree;
    private float speed;
    private PointF direction;

    public Player() {
        isMove = false;
        hp = R.dimen.PLAYER_HP;
        exp = 0;
        level = 1;
        dmg = R.dimen.PLAYER_DMG;
        fireSpeed = Metrics.getFloat(R.dimen.PLAYER_FIRE_SPEED);
        fireTimer = 0.0f;
        targetRotateDegree = 0.0f;
        currRotateDegree = 0.0f;
        speed = Metrics.getFloat(R.dimen.PLAYER_SPEED);
        direction = new PointF(0.0f, 0.0f);
        hitBox = new RectF(-20.0f, -20.0f, 20.0f, 20.0f);
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
        if (targetRotateDegree != 0.0f) {
            int sign = (int) (targetRotateDegree / Math.abs(targetRotateDegree));
            currRotateDegree += sign * MAX_ROTATE_DEGREE * deltaTime;
            targetRotateDegree += -sign * MAX_ROTATE_DEGREE * deltaTime;

            if (sign > 0) {
                targetRotateDegree = Math.max(0.0f, targetRotateDegree);
            }
            else {
                targetRotateDegree = Math.min(0.0f, targetRotateDegree);
            }

            // 현재 각도를 0 ~ 360으로 제한
            if (currRotateDegree < 0.0f)
                currRotateDegree = 360.0f - currRotateDegree;
            else if (currRotateDegree > 360.0f)
                currRotateDegree -= 360.0f;

            // 방향 최신화
            matrix.reset();
            matrix.postRotate(currRotateDegree);
            float[] pos = { 0.0f, -1.0f };
            matrix.mapPoints(pos);
            direction.x = pos[0];
            direction.y = pos[1];
        }

        // 이동, dstRect 최신화
        if (isMove) {
            position.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
            hitBox.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
            super.update(deltaTime);
        }

        // 총 발사
        if (fireTimer >= fireSpeed) fire();
        fireTimer += deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(hitBox, paint);
        canvas.save();
        canvas.rotate(currRotateDegree, position.x, position.y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void fire() {
        if (direction.x == 0.0f && direction.y == 0.0f)
            return;

        Bullet bullet = new Bullet();
        bullet.setBitmap(R.mipmap.bullet);
        bullet.setPosition(position.x, position.y);
        bullet.setDirection(direction.x, direction.y);
        GameScene.getInstance().add(GameScene.eLayer.BULLET, bullet);

        fireTimer = 0.0f;
    }

    public void setTargetRotateDegree(float degree) {
        // 현재 각도에서 몇도 회전해야되는 지 계산
        float delta = Math.abs(currRotateDegree - degree);
        if (delta > 180.0f) {
            delta = 360.0f - delta;
            if (currRotateDegree < degree)
                delta = -delta;
        }
        else {
            if (currRotateDegree > degree)
                delta = -delta;
        }
        targetRotateDegree = delta;
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

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public void addExp(int exp) {
        this.exp += exp;
    }
}
