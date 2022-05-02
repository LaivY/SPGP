package com.laivy.the.shape.game;

import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;

import java.util.ArrayList;

public class CollisionChecker extends GameObject {

    @Override
    public void update(float deltaTime) {
        ArrayList<GameObject> bullets = GameScene.getInstance().getLayer(GameScene.eLayer.BULLET);
        ArrayList<GameObject> enemies = GameScene.getInstance().getLayer(GameScene.eLayer.ENEMY);

        for (GameObject o1 : enemies) {
            Enemy enemy = (Enemy) o1;

            for (GameObject o2 : bullets) {
                Bullet bullet = (Bullet) o2;
                if (isCollided(enemy.getHitBox(), bullet.getHitBox())) {
                    //GameScene.getInstance().remove(GameScene.eLayer.ENEMY, enemy);
                    GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);
                    break;
                }
            }
        }
    }

    public static boolean isCollided(RectF hitBox1, RectF hitBox2) {
        if (hitBox1.left > hitBox2.right) return false;
        if (hitBox1.top > hitBox2.bottom) return false;
        if (hitBox1.right > hitBox2.left) return false;
        if (hitBox1.bottom > hitBox2.top) return false;
        return true;
    }
}
