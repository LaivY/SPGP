package com.laivy.the.shape.game.object.system;

import android.graphics.PointF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Enemy;
import com.laivy.the.shape.game.object.Player;

public class EnemyGenerator extends GameObject {
    private float spawnTime;
    private float timer;
    private float playTime;

    public EnemyGenerator() {
        spawnTime = 3.0f;
        timer = 3.0f;
        playTime = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        Player player = GameScene.getInstance().getPlayer();

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
        playTime = deltaTime;
    }
}
