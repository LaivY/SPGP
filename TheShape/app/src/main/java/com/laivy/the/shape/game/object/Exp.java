package com.laivy.the.shape.game.object;

import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;

public class Exp extends GameObject {
    private int exp;

    public Exp() {
        exp = 1;
        hitBox = new RectF(-15.0f, -15.0f, 15.0f, 15.0f);
    }

    public int getExp() {
        return exp;
    }
}
