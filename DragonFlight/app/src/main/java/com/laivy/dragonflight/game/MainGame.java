package com.laivy.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.laivy.dragonflight.framework.BoxCollidable;
import com.laivy.dragonflight.framework.CollisionHelper;
import com.laivy.dragonflight.framework.Metrics;
import com.laivy.dragonflight.R;
import com.laivy.dragonflight.framework.GameView;
import com.laivy.dragonflight.framework.GameObject;

import java.util.ArrayList;

public class MainGame {
    private Paint collisionPaint;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private Fighter fighter;

    public static void clear() {
        singleton = null;
    }

    public void init() {
        objects.clear();

        objects.add(new EnemyGenerator());

        float y = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(Metrics.width / 2.0f, y);
        objects.add(fighter);

        collisionPaint = new Paint();
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setColor(Color.RED);
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        for (GameObject obj : objects) {
            obj.update();
        }
        checkCollision();
    }

    private void checkCollision() {
        for (GameObject o1 : objects) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            boolean removed = false;
            for (GameObject o2 : objects) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    remove(bullet);
                    remove(enemy);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                continue;
            }
            // check enemy vs fighter
        }
    }

    public void draw(Canvas canvas) {
        for (GameObject obj : objects) {
            obj.draw(canvas);

            if (obj instanceof BoxCollidable) {
                RectF box = ((BoxCollidable) obj).getBoundingBox();
                canvas.drawRect(box, collisionPaint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
//                if (action == MotionEvent.ACTION_DOWN) {
//                    fighter.fire();
//                }
                return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
            }
        });
    }
}
