package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.laivy.the.shape.framework.GameObject;

public class Enemy extends GameObject {
    static private Paint paint;
    private int hp;
    private float speed;
    private float rotate;
    private RectF hitBox;
    private Player player;

    public Enemy() {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
        }

        hp = 10;
        speed = 1.0f;
        rotate = 0.0f;
        hitBox = new RectF(-100.0f, -100.0f, 100.0f, 100.0f);
        player = null;
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;

        PointF playerPosition = player.getPosition();
        float dx = playerPosition.x - position.x;
        float dy = playerPosition.y - position.y;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;
        position.offset(dx * speed, dy * speed);
        //hitBox.offset(dx * speed, dy * speed);

        rotate = (float) Math.toDegrees(Math.atan2(dx, dy));
        rotate = Math.abs(rotate - 180.0f);

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(rotate, position.x, position.y);
        super.draw(canvas);
        canvas.drawRect(hitBox, paint);
        canvas.restore();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
