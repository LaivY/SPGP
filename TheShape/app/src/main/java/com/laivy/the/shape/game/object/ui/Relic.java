package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class Relic extends GameObject {
    private int id;
    private String name;
    private String desc;

    public Relic(int id) {
        this.id = id;
        switch (id) {
            default:
                setBitmap(R.mipmap.kettlebell);
                name = "케틀벨";
                desc = "공격력 +5\n이동속도 +10%";
                break;
        }
        super.update(0.0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        super.update(0.0f);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
