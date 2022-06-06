package com.laivy.the.shape.game.object;

import com.laivy.the.shape.R;
import com.laivy.the.shape.game.GameScene;

public class LightningBullet extends Bullet {
    public LightningBullet() { }

    @Override
    public void onDestroy() {
        if (lifeTime < 0.0f) return;

        Sprite sprite = new SplashSprite();
        sprite.addImage(R.mipmap.splash0);
        sprite.addImage(R.mipmap.splash1);
        sprite.addImage(R.mipmap.splash2);
        sprite.addImage(R.mipmap.splash3);
        sprite.addImage(R.mipmap.splash4);
        sprite.addImage(R.mipmap.splash5);
        sprite.setOnlyOnce(true);
        sprite.setBitmapWidth(sprite.getBitmapWidth() * 2.0f);
        sprite.setBitmapHeight(sprite.getBitmapHeight() * 2.0f);
        sprite.setPosition(position.x, position.y);
        GameScene.getInstance().add(GameScene.eLayer.SPRITE, sprite);
    }
}
