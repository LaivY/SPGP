package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.laivy.the.shape.framework.Metrics;

public class Camera {
    private final PointF position;

    public Camera() {
        position = new PointF();
    }

    public void update(float deltaTime) {
        PointF playerPosition = GameScene.getInstance().getPlayer().getPosition();
        float dx = playerPosition.x - position.x;
        float dy = playerPosition.y - position.y;

        float DELAY = 0.3f;
        position.x += dx * deltaTime / DELAY;
        position.y += dy * deltaTime / DELAY;
    }

    public void on(Canvas canvas) {
        canvas.save();
        canvas.translate(
            -position.x + Metrics.width / 2.0f,
            -position.y + Metrics.height / 2.0f
        );
    }

    public void off(Canvas canvas) {
        canvas.restore();
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public PointF getPosition() {
        return position;
    }
}
