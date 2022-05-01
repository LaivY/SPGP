package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;

public class Controller extends GameObject {
    private final float CONTROLLER_STICK_RADIUS = 80.0f;
    private boolean isActive;
    private Player player;
    private PointF currPosition;
    private GameObject stick;

    Controller() {
        isActive = false;
        player = null;
        currPosition = new PointF();
        stick = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isActive = false;
                return true;
            case MotionEvent.ACTION_DOWN:
                isActive = true;
                setPosition(event.getX(), event.getY());
                currPosition.set(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isActive) return true;
                currPosition.set(event.getX(), event.getY());
                if (stick != null) {
                    // 스틱이 시작 위치에서 일정 거리 안에서만 움직이도록 해야함
                    float dx = currPosition.x - position.x;
                    float dy = currPosition.y - position.y;
                    float length = (float) Math.sqrt(dx * dx + dy * dy);
                    if (length >= CONTROLLER_STICK_RADIUS) {
                        dx /= length;
                        dy /= length;
                        dx *= CONTROLLER_STICK_RADIUS;
                        dy *= CONTROLLER_STICK_RADIUS;
                    }
                    stick.setPosition(position.x + dx, position.y + dy);
                }

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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setStick(GameObject stick) {
        this.stick = stick;
    }
}
