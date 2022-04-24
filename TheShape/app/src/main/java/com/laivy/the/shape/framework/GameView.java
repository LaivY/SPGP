package com.laivy.the.shape.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

import com.laivy.the.shape.game.GameScene;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private boolean isInitialized;
    private boolean isRunning;
    private long prevTimeNanos;
    private float deltaTime;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = null;
        isInitialized = false;
        isRunning = false;
        prevTimeNanos = 0;
        deltaTime = 0.0f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        Metrics.width = w;
        Metrics.height = h;

        if (!isInitialized) {
            init();
            isInitialized = true;
            isRunning = true;
        }
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if (!isRunning)
            return;
        deltaTime = (currentTimeNanos - prevTimeNanos) / 1e-9f;
        if (deltaTime != 0.0f) {
            prevTimeNanos = currentTimeNanos;
            GameScene.getInstance().update(deltaTime);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        GameScene.getInstance().draw(canvas);
    }

    private void init() {

    }
}