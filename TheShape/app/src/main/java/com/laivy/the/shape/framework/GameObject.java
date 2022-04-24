package com.laivy.the.shape.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class GameObject {
    protected Bitmap bitmap;
    protected RectF dstRect;
    protected float x, y;

    GameObject() {
        dstRect = new RectF();
    }

    public void update() {

    }

    public void draw(Canvas canvas) {

    }

    public void setBitmap(int bitmapResourceId) {

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}