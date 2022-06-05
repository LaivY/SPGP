package com.laivy.the.shape.game.object;

import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.Audio;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.ui.Relic;

public class Exp extends GameObject {
    private int exp;

    public Exp() {
        exp = 1;
        hitBox = new RectF(-15.0f, -15.0f, 15.0f, 15.0f);
    }

    @Override
    public void onDestroy() {
        Audio.playSound(R.raw.exp);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;
        if (!GameScene.getInstance().getPlayer().hasRelic(Relic.BAG_OF_PREP)) {
            super.update(deltaTime);
            return;
        }

        // 준비된 가방 효과
        PointF playerPosition = GameScene.getInstance().getPlayer().getPosition();
        float dx = playerPosition.x - position.x;
        float dy = playerPosition.y - position.y;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        float speed = 500.0f;
        position.offset(dx * speed * deltaTime, dy * speed * deltaTime);
        hitBox.offset(dx * speed * deltaTime, dy * speed * deltaTime);
        super.update(deltaTime);
    }

    public int getExp() {
        return exp;
    }
}
