package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;

public class Relic extends GameObject {
    private int id;
    private String name;
    private String desc;

    public Relic(int id) {
        this.id = id;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
