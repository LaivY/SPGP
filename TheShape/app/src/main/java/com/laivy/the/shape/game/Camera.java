package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.object.Player;

public class Camera {
    private final float DELAY = 0.3f;
    private Player player;
    private PointF position;

    public Camera() {
        player = null;
        position = new PointF();
    }

    public void update(float deltaTime) {
        if (player == null)
            return;

        PointF playerPosition = player.getPosition();
        float dx = playerPosition.x - position.x;
        float dy = playerPosition.y - position.y;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public PointF getPosition() {
        return position;
    }
}
