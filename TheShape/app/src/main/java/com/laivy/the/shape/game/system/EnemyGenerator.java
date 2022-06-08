package com.laivy.the.shape.game.system;

import android.graphics.PointF;
import android.util.Log;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.framework.Utility;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Enemy;

public class EnemyGenerator extends GameObject {
    private int lastChangedTime;
    private float spawnTime;
    private float timer;
    private float enemyHp;
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

            spawnTime   *= 0.95f;
            enemyHp     *= 1.2f;
            enemyDamage *= 1.1f;
            enemySpeed  *= 1.05f;
        }
    }

    private void spawnEnemy() {
        // 몬스터 생성 개수. 30초당 1개씩 더 많이 만들어질 수 있음
        int maxSpawnCount = lastChangedTime / 30 + 1;
        maxSpawnCount = Utility.getRandom(1, maxSpawnCount);

        // 몬스터의 크기. 플레이 타임이 3분일때 최대 2배가 될 수 있음
        float maxSpawnScale = 1.0f;
        if (Utility.getRandom(1, 100) < Math.min(90, lastChangedTime / 2))
            maxSpawnScale = Math.min(2.0f, lastChangedTime / 180.0f + 1.0f);

        for (int i = 0; i < maxSpawnCount; ++i) {
            int type = Utility.getRandom(0, 1);
            int xSign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            int ySign = Utility.getRandom(0, 1) == 0 ? -1 : 1;
            float x = Utility.getRandom(Metrics.width * 0.4f, Metrics.width * 0.6f) * xSign;
            float y = Utility.getRandom(Metrics.height * 0.4f, Metrics.height * 0.6f) * ySign;

            PointF playerPosition = GameScene.getInstance().getPlayer().getPosition();
            Enemy enemy = new Enemy();
            enemy.setBitmap(type == 0 ? R.mipmap.rect : R.mipmap.circle);
            enemy.setPosition(playerPosition.x + x, playerPosition.y + y);
            enemy.setHp((int) enemyHp);
            enemy.setDamage(enemyDamage);
            enemy.setSpeed(enemySpeed);
            enemy.setScale(Utility.getRandom(1.0f, maxSpawnScale));
            GameScene.getInstance().add(GameScene.eLayer.ENEMY, enemy);
        }
    }
}
