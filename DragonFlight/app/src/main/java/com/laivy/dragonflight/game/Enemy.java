package com.laivy.dragonflight.game;

import android.graphics.RectF;

import com.laivy.dragonflight.R;
import com.laivy.dragonflight.framework.BoxCollidable;
import com.laivy.dragonflight.framework.Metrics;
import com.laivy.dragonflight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    private static float size;
    private static float inset;
    protected float dy;
    protected RectF boundingBox = new RectF();

    public Enemy(float x, float speed) {
        super(x, -size / 2, size, size, R.mipmap.f_01_01);
        dy = speed;
    }

    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 16;
    }

    @Override
    public void update() {
        // 위치 최신화
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();

        // 바운딩박스 최신화
        boundingBox.set(dstRect);
        boundingBox.inset(inset, inset);

        if (y > Metrics.height + size / 2)
            MainGame.getInstance().remove(this);
    }

    @Override
    public RectF getBoundingBox() {
        return boundingBox;
    }
}
