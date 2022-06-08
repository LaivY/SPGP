package com.laivy.the.shape.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

public class GameObject {
    static protected Paint paint;
    static protected Matrix matrix;
    protected boolean isValid;
    protected Bitmap bitmap;
    protected float bitmapWidth;
    protected float bitmapHeight;
    protected RectF dstRect;
    protected RectF hitBox;
    protected PointF position;

    public GameObject() {
        if (paint == null)
            paint = new Paint();
        if (matrix == null) {
            matrix = new Matrix();
        }
        isValid = true;
        bitmap = null;
        bitmapWidth = 0.0f;
        bitmapHeight = 0.0f;
        dstRect = new RectF();
        hitBox = null;
        position = new PointF();
    }

    public void onDestroy() { }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void update(float deltaTime) {
        if (!isValid) return;
        dstRect.set(-bitmapWidth / 2.0f, -bitmapHeight / 2.0f, bitmapWidth / 2.0f, bitmapHeight / 2.0f);
        dstRect.offset(position.x, position.y);
    }

    public void draw(Canvas canvas) {
        if (!isValid || bitmap == null) return;
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setBitmap(int bitmapResourceId) {
        bitmap = BitmapPool.get(bitmapResourceId);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    public void setBitmapWidth(float width) {
        bitmapWidth = width;
    }

    public void setBitmapHeight(float height) {
        bitmapHeight = height;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        if (hitBox != null) {
            float width = hitBox.width();
            float height = hitBox.height();
            hitBox.offsetTo(x - width / 2.0f, y - height / 2.0f);
        }
    }

    public boolean getIsValid() {
        return isValid;
    }

    public PointF getPosition() {
        return position;
    }

    public RectF getHitBox() {
        return hitBox;
    }

    public float getBitmapWidth() {
        return bitmapWidth;
    }

    public float getBitmapHeight() {
        return bitmapHeight;
    }
}