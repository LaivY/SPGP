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
            case 0:
                setBitmap(R.mipmap.kettlebell);
                name = "케틀벨";
                desc = "공격력 +3";
                break;
            case 1:
                setBitmap(R.mipmap.blood_vial);
                name = "피가 담긴 병";
                desc = "적 처치 시 체력 1 회복";
                break;
            case 2:
                setBitmap(R.mipmap.bronze_scales);
                name = "청동 비늘";
                desc = "피격 시 해당 적에게 공격력 만큼의 피해를 줌";
                break;
            default:
                name = "이름";
                desc = "설명";
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
