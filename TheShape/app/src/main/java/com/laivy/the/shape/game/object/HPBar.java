package com.laivy.the.shape.game.object;

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
    private Paint barPaint;
    private RectF base;
    private RectF bar;
    private float width;
    private float heightOffset;
    private Player player;

    public HPBar() {
        barPaint = new Paint();
        base = new RectF();
        bar = new RectF();
        width = Metrics.getFloat(R.dimen.HPBAR_WIDTH);
        heightOffset = BitmapPool.get(R.mipmap.player).getHeight() * 0.8f;
        player = null;
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;

        float maxHp = player.getMaxHp();
        float hp = player.getHp();
        float div = Math.min(1.0f, Math.max(0.0f, hp / maxHp));

        // 위치, 길이 업데이트
        PointF playerPosition = player.getPosition();
        base.set(-width / 2.0f, 0.0f, width / 2.0f, 10.0f);
        base.offset(playerPosition.x, playerPosition.y + heightOffset);
        bar.set(-width / 2.0f, 0.0f, -width / 2.0f + width * div, 10.0f);
        bar.offset(playerPosition.x, playerPosition.y + heightOffset);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        barPaint.setColor(0xFF333333);
        canvas.drawRect(base, barPaint);

        barPaint.setColor(Color.RED);
        canvas.drawRect(bar, barPaint);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
