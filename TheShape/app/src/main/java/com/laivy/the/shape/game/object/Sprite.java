package com.laivy.the.shape.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.laivy.the.shape.framework.BitmapPool;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

import java.util.ArrayList;

public class Sprite extends GameObject {
    private final ArrayList<Bitmap> images;
    private final float interval;
    private float timer;
    private boolean isOnlyOnce;

    public Sprite() {
        images = new ArrayList<>();
        interval = 1.0f / 30.0f;
        timer = 0.0f;
        isOnlyOnce = false;
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;

        int frame = (int) (timer / interval);
        if (frame >= images.size()) {
            if (isOnlyOnce) {
                GameScene.getInstance().remove(GameScene.eLayer.SPRITE, this);
                return;
            }
            timer = 0.0f;
            frame = 0;
        }
        bitmap = images.get(frame);
        timer += deltaTime;
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void addImage(int bitmapResourceId) {
        Bitmap spriteImage = BitmapPool.get(bitmapResourceId);
        bitmapWidth = spriteImage.getWidth();
        bitmapHeight = spriteImage.getHeight();
        images.add(spriteImage);
    }

    public void setOnlyOnce(boolean onlyOnce) {
        this.isOnlyOnce = onlyOnce;
    }

}
