package com.laivy.the.shape.game.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.GameScene;

public class Result extends GameObject {
    RectF outlineRect;
    final float width;
    final float height;

    public Result() {
        width = Metrics.width * 0.4f;
        height = Metrics.height * 0.5f;
        dstRect.set(-width / 2.0f, -height / 2.0f, width / 2.0f, height / 2.0f);
        dstRect.offset(Metrics.width / 2.0f, Metrics.height / 2.0f);

        float outlineThick = 10.0f;
        outlineRect = new RectF(-width / 2.0f - outlineThick, -height / 2.0f - outlineThick,
                                width / 2.0f + outlineThick, height / 2.0f + outlineThick);
        outlineRect.offset(Metrics.width / 2.0f, Metrics.height / 2.0f);
    }

    @Override
    public void update(float deltaTime) { }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;

        paint.setColor(0xFF2A9DF4);
        canvas.drawRect(outlineRect, paint);

        paint.setAlpha(100);
        paint.setColor(Color.WHITE);
        canvas.drawRect(dstRect, paint);
        paint.setAlpha(255);

        paint.setColor(Color.BLACK);
        paint.setTextSize(Metrics.height * 0.08f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("게임오버", Metrics.width / 2.0f, Metrics.height / 2.0f - height / 2.0f + Metrics.height * 0.15f, paint);

        float playTime = GameScene.getInstance().getPlayTime();
        int min = (int) (playTime / 60);
        int sec = (int) (playTime % 60);
        paint.setColor(0xFF5DBB63);
        paint.setTextSize(Metrics.height * 0.05f);
        canvas.drawText("버틴 시간 : " + min + "분 " + sec + "초", Metrics.width / 2.0f, Metrics.height / 2.0f, paint);

        float y = Metrics.height / 2.0f + height / 2.0f - Metrics.height * 0.15f;
        paint.setColor(Color.BLACK);
        canvas.drawText("아무곳이나 터치하면", Metrics.width / 2.0f, y, paint);
        y += paint.descent() - paint.ascent();

        canvas.drawText("메인화면으로 이동합니다.", Metrics.width / 2.0f, y, paint);
    }
}
