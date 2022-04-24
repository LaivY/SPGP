package com.laivy.dragonflight.game;

import android.graphics.Canvas;

import com.laivy.dragonflight.R;
import com.laivy.dragonflight.framework.GameObject;
import com.laivy.dragonflight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        Enemy.setSize(Metrics.width / 5.0f);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        float tenth = Metrics.width / 10.0f;
        for (int i = 1; i <= 9; i += 2) {
            Enemy enemy = new Enemy(tenth * i, fallSpeed);
            MainGame.getInstance().add(enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) { }
}
