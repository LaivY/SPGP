package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class Player extends GameObject {
    private final float MAX_ROTATE_DEGREE = 90.0f;

    private int hp;
    private int exp;
    private int dmg;
    private float remainRotateDegree;
    private float currRotateDegree;
    private float speed;
    private PointF direction;

    public Player() {
        hp = R.dimen.PLAYER_HP;
        exp = 0;
        dmg = R.dimen.PLAYER_DMG;
        speed = Metrics.getFloat(R.dimen.PLAYER_SPEED);
        direction = new PointF(0.0f, -1.0f);
    }

    @Override
    public void update(float deltaTime) {
        // 회전해야할 각도가 남아있다면 회전
        if (remainRotateDegree > 0.0f) {
            remainRotateDegree -= MAX_ROTATE_DEGREE * deltaTime;
            currRotateDegree += MAX_ROTATE_DEGREE * deltaTime;
        }

        // 이동
        position.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);

        // dstRect 최신화
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(currRotateDegree, position.x, position.y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public void setDirection(float x, float y) {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        direction.set(x, y);
    }
}
