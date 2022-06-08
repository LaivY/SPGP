package com.laivy.the.shape.game.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

public class TextObject extends GameObject {
    private final Paint paint;
    private String text;
    private float lifeTime;
    private float timer;

    public TextObject() {
        paint = new Paint();
        text = "DEFAULT_TEXT";
        timer = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;

        timer += deltaTime;
        if (timer >= lifeTime) {
            isValid = false;
            GameScene.getInstance().remove(GameScene.eLayer.TEXT, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, position.x, position.y, paint);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setTextSize(float size) {
        paint.setTextSize(size);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }
}
