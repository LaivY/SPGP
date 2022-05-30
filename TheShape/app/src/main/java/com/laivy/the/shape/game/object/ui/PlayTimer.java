package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class PlayTimer extends GameObject {
    float timer;

    public PlayTimer() {
        timer = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        int min = (int) (timer / 60);
        int sec = (int) (timer % 60);
        String text = "";
        if (min < 10)
            text += "0";
        text += min + " : ";
        if (sec < 10)
            text += "0";
        text += sec;

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, Metrics.width / 2.0f, Metrics.height * 0.07f, paint);
    }
}
