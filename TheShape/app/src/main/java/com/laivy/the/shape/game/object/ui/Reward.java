package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Player;

public class Reward extends GameObject {
    /*
    이 객체는 3개의 사각형을 갖고 있고 중복되지 않는 서로 다른 보상을 갖고있다.
    3개의 사각형 중 하나라도 선택됬다면 게임을 진행시키고 이 객체를 삭제한다.
    */
    private final float REWARD_UI_INTERVAL = 0.05f;
    private Relic[] relics;
    private RectF[] rects;
    private float width;
    private float height;

    public Reward(float width, float height) {
        // 중복없이 3개의 보상 선택
        int[] relicIds = new int[3];
        relicIds[0] = -1;
        relicIds[1] = -1;
        relicIds[2] = -1;

        for (int i = 0; i < 3; ++i) {
            while (true) {
                boolean pass = true;
                int rewardIndex = Utility.getRandom(0, 2);
                for (int j = 0; j < i; ++j) {
                    if (relicIds[j] == rewardIndex) {
                        pass = false;
                        break;
                    }
                }
                if (pass) {
                    relicIds[i] = rewardIndex;
                    break;
                }
            }
        }

        // 유물 객체 생성
        relics = new Relic[3];
        for (int i = 0; i < 3; ++i) {
            relics[i] = new Relic(relicIds[i]);
            relics[i].setBitmapWidth(Metrics.width * 0.1f);
            relics[i].setBitmapHeight(Metrics.width * 0.1f);

            if (i == 0)
                relics[i].setPosition(Metrics.width / 2.0f - width - Metrics.width * REWARD_UI_INTERVAL, Metrics.height / 2.0f);
            else if (i == 1)
                relics[i].setPosition(Metrics.width / 2.0f, Metrics.height / 2.0f);
            else
                relics[i].setPosition(Metrics.width / 2.0f + width + Metrics.width * REWARD_UI_INTERVAL, Metrics.height / 2.0f);
        }

        // 파라미터로 받는 width, height 는 보상 칸 하나의 가로, 세로 길이
        this.width = width;
        this.height = height;

        rects = new RectF[3];
        for (int i = 0; i < 3; ++i)
            rects[i] = new RectF();
        rects[1].set(-width / 2.0f, -height / 2.0f, width / 2.0f, height / 2.0f);
        rects[1].offset(Metrics.width / 2.0f, Metrics.height / 2.0f);

        rects[0].set(rects[1]);
        rects[2].set(rects[1]);
        rects[0].offset(-width - Metrics.width * REWARD_UI_INTERVAL, 0.0f);
        rects[2].offset(width + Metrics.width * REWARD_UI_INTERVAL, 0.0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isValid) return false;
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        for (int i = 0; i < 3; ++i) {
            if (!rects[i].contains(event.getX(), event.getY())) continue;

            // 플레이어에게 유물 추가
            Player player = GameScene.getInstance().getPlayer();
            player.addRelic(relics[i]);

            GameScene.getInstance().remove(GameScene.eLayer.UI, this);
            GameScene.getInstance().setRunning(true);
            break;
        }
        return true;
    }

    @Override
    public void update(float deltaTime) { }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;
        for (int i = 0; i < 3; ++i) {
            paint.setColor(Color.WHITE);
            canvas.drawRect(rects[i], paint);
            relics[i].draw(canvas);

            paint.setColor(Color.BLACK);
            paint.setTextSize(30.0f);
            paint.setTextAlign(Paint.Align.CENTER);

            float y = rects[i].centerY() + relics[i].getBitmapHeight() / 2.0f;

            String text = relics[i].getName();
            canvas.drawText(text, rects[i].centerX(), y, paint);
            y += paint.descent() - paint.ascent();

            paint.setTextSize(20.0f);
            text = relics[i].getDesc();
            canvas.drawText(text, rects[i].centerX(), y, paint);
        }
    }
}
