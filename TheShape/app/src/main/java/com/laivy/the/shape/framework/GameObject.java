package com.laivy.the.shape.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class GameObject {
    protected Bitmap bitmap;
    protected float bitmapWidth;
    protected float bitmapHeight;
    protected RectF dstRect;
    protected PointF position;

    public GameObject() {
        bitmap = null;
        bitmapWidth = 0.0f;
        bitmapHeight = 0.0f;
        dstRect = new RectF();
        position = new PointF();
    }

    public void update(float deltaTime) {
        dstRect.set(-bitmapWidth / 2.0f, -bitmapHeight / 2.0f, bitmapWidth / 2.0f, bitmapHeight / 2.0f);
        dstRect.offset(position.x, position.y);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setBitmap(int bitmapResourceId) {
        bitmap = BitmapPool.get(bitmapResourceId);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }
}