package com.laivy.the.shape.game.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.GameScene;

public class PlayTimer extends GameObject {
    private static final float EXPBAR_HEIGHT =
            Metrics.width * Metrics.getFloat(R.dimen.EXPBAR_WIDTH) * Metrics.getFloat(R.dimen.EXPBAR_HEIGHT);

    public PlayTimer() { }

    @Override
    public void update(float deltaTime) { }

    @Override
    public void draw(Canvas canvas) {
        float playTime = GameScene.getInstance().getPlayTime();
        int min = (int) (playTime / 60);
        int sec = (int) (playTime % 60);
        String text = "";
        if (min < 10)
            text += "0";
        text += min + " : ";
        if (sec < 10)
            text += "0";
        text += sec;

        paint.setColor(Color.WHITE);
        paint.setTextSize(Metrics.height * 0.04f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, Metrics.width / 2.0f, EXPBAR_HEIGHT * 2.5f, paint);
    }
}
