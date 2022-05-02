package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

public class ExpBar extends GameObject {
    private Paint barPaint;
    private RectF base;
    private RectF bar;
    private float width;
    private int exp;
    private float remain;
    private Player player;

    public ExpBar() {
        barPaint = new Paint();
        width = Metrics.getFloat(R.dimen.EXPBAR_WIDTH);
        base = new RectF(-width / 2.0f, 0.0f, width / 2.0f, 30.0f);
        base.offset(Metrics.width / 2.0f, 0.0f);
        bar = new RectF(-width / 2.0f, 0.0f, -width / 2.0f, 30.0f);
        bar.offset(Metrics.width / 2.0f, 0.0f);
        exp = 0;
        remain = 0.0f;
        player = null;
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;
        int exp = player.getExp();

        if (this.exp != exp) {
            remain += width * (exp - this.exp) / (4 + player.getLevel());
            this.exp = exp;
        }
        if (remain > 0.0f) {
            remain -= 500.0f * deltaTime;
            if (remain < 0.0f) {
                bar.right = Math.min(Metrics.width / 2.0f + width / 2.0f, bar.right + 500.0f * deltaTime + remain + 0.01f);
                remain = 0.0f;
            }
            else bar.right = Math.min(Metrics.width / 2.0f + width / 2.0f, bar.right + 500.0f * deltaTime);
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        barPaint.setColor(Color.GRAY);
        canvas.drawRect(base, barPaint);

        barPaint.setColor(0xFF00b7ee);
        canvas.drawRect(bar, barPaint);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
