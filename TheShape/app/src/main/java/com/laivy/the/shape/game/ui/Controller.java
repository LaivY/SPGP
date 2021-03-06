package com.laivy.the.shape.game.ui;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Player;

public class Controller extends GameObject {
    public static final float RADIUS = Metrics.height * 0.14f;
    private boolean isActive;
    private final PointF currPosition;
    private GameObject stick;

    public Controller() {
        isActive = false;
        currPosition = new PointF();
        stick = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (GameScene.getInstance().getPlayer().getHp() == 0)
            return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isActive = false;
                return true;
            case MotionEvent.ACTION_DOWN:
                isActive = true;
                setPosition(event.getX(), event.getY());
                currPosition.set(event.getX(), event.getY());
                stick.setPosition(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isActive) return true;
                currPosition.set(event.getX(), event.getY());
                if (stick != null) {
                    // 스틱이 시작 위치에서 일정 거리 안에서만 움직이도록 해야함
                    float dx = currPosition.x - position.x;
                    float dy = currPosition.y - position.y;
                    float length = (float) Math.sqrt(dx * dx + dy * dy);
                    if (length >= RADIUS / 2.0f) {
                        dx /= length;
                        dy /= length;
                        dx *= RADIUS / 2.0f;
                        dy *= RADIUS / 2.0f;
                    }
                    stick.setPosition(position.x + dx, position.y + dy);
                }

                Player player = GameScene.getInstance().getPlayer();
                if (player != null) {
                    float dx = currPosition.x - position.x;
                    float dy = currPosition.y - position.y;
                    float t = (float) Math.toDegrees(Math.atan2(dx, dy));
                    player.setTargetRotateDegree(Math.abs(t - 180.0f));
                }
                return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (stick != null)
            stick.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isActive) {
            super.draw(canvas);
            if (stick != null)
                stick.draw(canvas);
        }
    }

    public void setStick(GameObject stick) {
        this.stick = stick;
    }
}
