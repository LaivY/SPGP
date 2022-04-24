package com.laivy.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainGame {
    private static MainGame singleton;
    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private Fighter fighter;
    public float frameTime;

    public static MainGame getInstance() {
        if (singleton == null)
            singleton = new MainGame();
        return singleton;
    }

    private MainGame() {

    }

    public void init() {
        Random random = new Random();
        for (int i = 0; i < BALL_COUNT; ++i) {
            int dx = random.nextInt(100) + 100;
            int dy = random.nextInt(100) + 100;
            Ball ball = new Ball(dx, dy);
            objects.add(ball);
        }
        fighter = new Fighter(Metrics.width / 2.0f, Metrics.height / 2.0f);
        objects.add(fighter);
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f;
        for (GameObject object : objects) {
            object.update();
        }
    }

    public void draw(Canvas canvas) {
        for (GameObject object : objects)
            object.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }
}
