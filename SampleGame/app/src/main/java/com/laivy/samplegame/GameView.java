package com.laivy.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private static final int BALL_COUNT = 10;
    //private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<GameObject> objects = new ArrayList<>();
    private Fighter fighter;

    private long previousTimeNanos = 0;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();

    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        view = this;

        Random random = new Random();
        for (int i = 0; i < BALL_COUNT; ++i) {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            Ball ball = new Ball(dx, dy);
            objects.add(ball);
        }

        fighter = new Fighter();
        objects.add(fighter);

        // 다음 프레임에 doFrame 함수가 호출되도록 설정
        Choreographer.getInstance().postFrameCallback(this);

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(50);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        int elapsed = (int) (currentTimeNanos - previousTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            previousTimeNanos = currentTimeNanos;
            update();
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        for (GameObject object : objects) {
            object.update();
        }
        //fighter.update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (GameObject object : objects) {
            object.draw(canvas);
        }
//        fighter.draw(canvas);
        canvas.drawText("FPS : " + framesPerSecond, 100, 100, fpsPaint);
        //Log.d(TAG, "onDraw!");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
