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
    private final Paint barPaint;
    private final RectF base;
    private final RectF bar;
    private Player player;
    private float width;
    private int playerExp;
    private float exp;
    private float delta;

    public ExpBar() {
        barPaint = new Paint();
        width = Metrics.getFloat(R.dimen.EXPBAR_WIDTH);
        base = new RectF(-width / 2.0f, 0.0f, width / 2.0f, 30.0f);
        base.offset(Metrics.width / 2.0f, 0.0f);
        bar = new RectF(-width / 2.0f, 0.0f, -width / 2.0f, 30.0f);
        bar.offset(Metrics.width / 2.0f, 0.0f);
        player = null;
        playerExp = 0;  // 최신 플레이어의 경험치
        exp = 0.0f;     // 그리기를 위한 경험치
        delta = 0.0f;   // exp에 계속해서 더해줘야할 경험치량
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;

        int currPlayerExp = player.getExp();
        int currPlayerReqExp = player.getReqExp();

        // 경험치바가 갖고있던 플레이어 경험치와 값이 다르다면
        // delta 값에 그만큼 더해주고 플레이어 경험치 값을 최신화한다.
        if (playerExp != currPlayerExp) {
            delta += currPlayerExp - playerExp;
            playerExp = currPlayerExp;
        }

        // delta 값이 있다면 그만큼 경험치 바를 채운다.
        if (delta != 0.0f) {
            float temp;
            if (delta > 0.0f) {
                temp = Math.max(0.0f, delta - deltaTime * 10.0f);
                exp += delta - temp;
            }
            else {
                temp = Math.min(0.0f, delta + deltaTime * 10.0f);
                exp += temp - delta;
            }
            delta = temp;
        }

        // 경험치바에 적용
        float expRatio = Math.max(0.0f, Math.min(1.0f, exp / currPlayerReqExp));
        int percentage = Math.round(expRatio * 100);
        if (percentage == 100) {
            player.onLevelUp();
            playerExp = 0;
            exp = 0.0f;
            delta = 0.0f;
        }

        bar.right = (Metrics.width / 2.0f) - (width / 2.0f) + (width * expRatio);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        barPaint.setColor(Color.GRAY);
        canvas.drawRect(base, barPaint);

        barPaint.setColor(0xFF00b7ee);
        canvas.drawRect(bar, barPaint);

        canvas.drawText("hello, world", 0.0f, 0.0f, paint);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
