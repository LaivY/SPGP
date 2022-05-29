package com.laivy.the.shape.game.object.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Player;

import java.util.Map;

public class Reward extends GameObject {
    /*
    이 객체는 3개의 사각형을 갖고 있고 중복되지 않는 서로 다른 보상을 갖고있다.
    3개의 사각형 중 하나라도 선택됬다면 게임을 진행시키고 이 객체를 삭제한다.
    */
    private final float REWARD_UI_INTERVAL = 0.05f;
    private enum eReward
    {
        HP, DMG, SPEED, ATKSPEED, KNOCKBACK
    }
    private eReward[] rewards;
    private RectF[] rects;
    private float width;
    private float height;

    public Reward(float width, float height) {
        rewards = new eReward[3];

        // 중복없이 3개의 보상 선택
        for (int i = 0; i < 3; ++i) {
            while (true) {
                boolean pass = true;
                int rewardIndex = Utility.getRandom(0, eReward.values().length - 1);
                for (int j = 0; j < i; ++j) {
                    if (rewards[j].ordinal() == rewardIndex) {
                        pass = false;
                        break;
                    }
                }
                if (pass) {
                    rewards[i] = eReward.values()[rewardIndex];
                    break;
                }
            }
        }

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

            // 플레이어 스탯 증가
            Player player = GameScene.getInstance().getPlayer();
            player.addDamage(5);

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

            String text = "";
            switch (rewards[i])
            {
                case HP:
                    text = "체력 +10";
                    break;
                case DMG:
                    text = "데미지 +5";
                    break;
                case SPEED:
                    text = "이동속도 +10%";
                    break;
                case ATKSPEED:
                    text = "공격속도 +10%";
                    break;
                case KNOCKBACK:
                    text = "넉백 +10%";
                    break;
            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, rects[i].centerX(), rects[i].centerY(), paint);
        }
    }
}
