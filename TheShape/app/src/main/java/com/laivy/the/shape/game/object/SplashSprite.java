package com.laivy.the.shape.game.object;

import android.graphics.RectF;

public class SplashSprite extends Sprite {
    private boolean isUsed;

    public SplashSprite() {
        // getHitBox 함수가 호출되면 true 로 바뀜
        isUsed = false;
        hitBox = new RectF();
    }

    @Override
    public RectF getHitBox() {
        hitBox.set(-bitmapWidth / 2.0f, -bitmapHeight / 2.0f, bitmapWidth / 2.0f, bitmapHeight / 2.0f);
        hitBox.offset(position.x, position.y);
        return hitBox;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean getUsed() {
        return isUsed;
    }
}
