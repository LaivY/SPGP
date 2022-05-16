package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.object.Player;

public class ExpBar extends GameObject {
    private Paint barPaint;
    private RectF base;
    private RectF bar;
    private float width;
    private int playerExp = 0;
    private float exp;
    private float delta;
    private Player player;

    public ExpBar() {
        barPaint = new Paint();
        width = Metrics.getFloat(R.dimen.EXPBAR_WIDTH);
        base = new RectF(-width / 2.0f, 0.0f, width / 2.0f, 30.0f);
        base.offset(Metrics.width / 2.0f, 0.0f);
        bar = new RectF(-width / 2.0f, 0.0f, -width / 2.0f, 30.0f);
        bar.offset(Metrics.width / 2.0f, 0.0f);
        exp = 0.0f;
        delta = 0.0f;
        player = null;
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;

        if (playerExp != player.getExp()) {
            delta += player.getExp() - playerExp;
            playerExp = player.getExp();
        }
        if (delta != 0.0f) {
            float temp;
            if (delta > 0.0f) {
                temp = Math.max(0.0f, delta - deltaTime);
                exp += delta - temp;
            }
            else {
                temp = Math.min(0.0f, delta + deltaTime);
                exp += temp - delta;
            }
            delta = temp;
        }
        float reqExp = player.getReqExp();
        float div = exp / reqExp;

        bar.right = Metrics.width / 2.0f - width / 2.0f + width * div;
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
