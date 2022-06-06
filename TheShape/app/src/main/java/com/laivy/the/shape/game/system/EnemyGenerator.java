package com.laivy.the.shape.game.system;

import android.graphics.PointF;
import android.util.Log;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Enemy;

public class EnemyGenerator extends GameObject {
    private int lastChangedTime;
    private float spawnTime;
    private float timer;
    private int enemyHp;
    private int enemyDamage;
    private float enemySpeed;

    public EnemyGenerator() {
        lastChangedTime = 0;
        spawnTime = 3.0f;
        timer = 3.0f;
        enemyHp = 10;
        enemyDamage = 10;
        enemySpeed = 80.0f;
    }

    @Override
    public void update(float deltaTime) {
        updateEnemyStats();
        if (timer >= spawnTime) {
            spawnEnemy();
            timer = 0.0f;
            return;
        }
        timer += deltaTime;
    }

    private void updateEnemyStats() {
        float playTime = GameScene.getInstance().getPlayTime();
        if (lastChangedTime < (int) playTime && (int) playTime % 10 == 0) {
            lastChangedTime = (int) playTime;

            spawnTime *= 0.9f;
            enemyHp *= 1.05f;
            enemyDamage *= 1.05f;
            enemySpeed *= 1.05f;
        }
    }

    private void spawnEnemy() {
        int amount = lastChangedTime / 30 + 1;
        amount = Utility.getRandom(1, amount);
        for (int i = 0; i < amount; ++i) {
            int type = Utility.getRandom(0, 1);
            int xSign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            int ySign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            float x = Utility.getRandom(300.0f, 600.0f) * xSign;
            float y = Utility.getRandom(300.0f, 600.0f) * ySign;

            PointF playerPosition = GameScene.getInstance().getPlayer().getPosition();
            Enemy enemy = new Enemy();
            enemy.setBitmap(type == 0 ? R.mipmap.rect : R.mipmap.circle);
            enemy.setPosition(playerPosition.x + x, playerPosition.y + y);
            enemy.setHp(enemyHp);
            enemy.setDamage(enemyDamage);
            enemy.setSpeed(enemySpeed);
            GameScene.getInstance().add(GameScene.eLayer.ENEMY, enemy);
        }
    }
}
