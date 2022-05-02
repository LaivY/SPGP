package com.laivy.the.shape.game.object;

import android.graphics.PointF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;

public class EnemyGenerator extends GameObject {
    private Player player;
    private float spawnTime;
    private float timer;

    public EnemyGenerator() {
        player = null;
        spawnTime = 5.0f;
        timer = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        if (player == null) return;

        if (timer > spawnTime) {
            int type = Utility.getRandom(0, 1);
            int xSign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            int ySign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            float x = Utility.getRandom(100.0f, 500.0f) * xSign;
            float y = Utility.getRandom(100.0f, 500.0f) * ySign;

            PointF playerPosition = player.getPosition();

            Enemy enemy = new Enemy();
            enemy.setPlayer(player);
            enemy.setBitmap(type == 0 ? R.mipmap.rect : R.mipmap.circle);
            enemy.setPosition(playerPosition.x + x, playerPosition.y + y);
            GameScene.getInstance().add(GameScene.eLayer.ENEMY, enemy);

            timer = 0.0f;
            return;
        }

        timer += deltaTime;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
