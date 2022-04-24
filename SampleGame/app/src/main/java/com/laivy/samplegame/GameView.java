package com.laivy.samplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private long previousTimeNanos = 0;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();

    public static GameView view;
    private boolean initialized;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if (!initialized) {
            initView();
            initialized = true;
        }
    }

    private void initView() {
        view = this;
        MainGame.getInstance().init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(50);

        // 다음 프레임에 doFrame 함수가 호출되도록 설정
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        int elapsed = (int) (currentTimeNanos - previousTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            previousTimeNanos = currentTimeNanos;
            MainGame.getInstance().update(elapsed);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.getInstance().draw(canvas);
        //fighter.draw(canvas);
        canvas.drawText("FPS : " + framesPerSecond, 100, 100, fpsPaint);
        //Log.d(TAG, "onDraw!");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.getInstance().onTouchEvent(event);
    }
}
