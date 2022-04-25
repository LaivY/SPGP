package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;

public class Controller extends GameObject {
    private boolean isActive;
    private Player player;
    private PointF currPosition;

    Controller() {
        isActive = false;
        player = null;
        currPosition = new PointF();
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
                if (player != null) {
                    float dx = currPosition.x - position.x;
                    float dy = currPosition.y - position.y;
                    player.setDirection(dx, dy);
                }
                return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isActive)
            super.draw(canvas);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
