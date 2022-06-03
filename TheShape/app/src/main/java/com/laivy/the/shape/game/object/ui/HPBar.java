package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.BitmapPool;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class HPBar extends GameObject {
    private final Paint barPaint;
    private final RectF base;
    private final RectF bar;
    private final float width;
    private final float height;
    private float value;
    private float maxValue;
    private float heightOffset;

    public HPBar() {
        barPaint = new Paint();
        base = new RectF();
        bar = new RectF();
        width = Metrics.getFloat(R.dimen.HPBAR_WIDTH);
        height = Metrics.getFloat(R.dimen.HPBAR_HEIGHT);
        value = 0.0f;
        maxValue = 0.0f;
        heightOffset = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        float div = Math.min(1.0f, Math.max(0.0f, value / maxValue));

        // 위치, 길이 업데이트
        base.set(-width / 2.0f, -height / 2.0f, width / 2.0f, height / 2.0f);
        base.offset(position.x, position.y + heightOffset);
        bar.set(-width / 2.0f, -height / 2.0f, -width / 2.0f + width * div, height / 2.0f);
        bar.offset(position.x, position.y + heightOffset);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        barPaint.setColor(0xFF333333);
        canvas.drawRect(base, barPaint);

        barPaint.setColor(Color.RED);
        canvas.drawRect(bar, barPaint);
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setHeightOffset(float heightOffset) {
        this.heightOffset = heightOffset;
    }
}
