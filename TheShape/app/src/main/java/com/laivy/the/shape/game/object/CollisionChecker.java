package com.laivy.the.shape.game.object;

import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

import java.util.ArrayList;

public class CollisionChecker extends GameObject {

    @Override
    public void update(float deltaTime) {
        checkEnemyAndBullet();
        checkPlayerAndExp();
    }

    public void checkEnemyAndBullet() {
        ArrayList<GameObject> bullets = GameScene.getInstance().getLayer(GameScene.eLayer.BULLET);
        ArrayList<GameObject> enemies = GameScene.getInstance().getLayer(GameScene.eLayer.ENEMY);

        for (GameObject o1 : enemies) {
            if (!o1.getIsValid()) continue;
            Enemy enemy = (Enemy) o1;

            for (GameObject o2 : bullets) {
                if (!o2.getIsValid()) continue;

                Bullet bullet = (Bullet) o2;
                if (isCollided(enemy.getHitBox(), bullet.getHitBox())) {
                    GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);
                    enemy.onHit(bullet);
                }
            }
        }
    }

    public void checkPlayerAndExp() {
        Player player = GameScene.getInstance().getPlayer();
        ArrayList<GameObject> exps = GameScene.getInstance().getLayer(GameScene.eLayer.EXP);

        for (GameObject o : exps) {
            if (!o.getIsValid()) continue;
            Exp exp = (Exp) o;
            if (isCollided(player.getHitBox(), exp.getHitBox())) {
                GameScene.getInstance().remove(GameScene.eLayer.EXP, exp);
                player.addExp(1);
            }
        }
    }

    public static boolean isCollided(RectF hitBox1, RectF hitBox2) {
        return RectF.intersects(hitBox1, hitBox2);
    }
}
