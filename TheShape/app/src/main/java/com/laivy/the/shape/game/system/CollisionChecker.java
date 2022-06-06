package com.laivy.the.shape.game.system;

import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Bullet;
import com.laivy.the.shape.game.object.Enemy;
import com.laivy.the.shape.game.object.Exp;
import com.laivy.the.shape.game.object.FlameBullet;
import com.laivy.the.shape.game.object.LightningBullet;
import com.laivy.the.shape.game.object.Player;
import com.laivy.the.shape.game.object.SplashSprite;

import java.util.ArrayList;

public class CollisionChecker extends GameObject {

    @Override
    public void update(float deltaTime) {
        checkEnemyCollision();
        checkPlayerCollision();
    }

    public void checkEnemyCollision() {
        // 적과 플레이어, 적과 총알 간의 충돌 처리를 한다.
        Player player = GameScene.getInstance().getPlayer();
        ArrayList<GameObject> enemies = GameScene.getInstance().getLayer(GameScene.eLayer.ENEMY);
        ArrayList<GameObject> bullets = GameScene.getInstance().getLayer(GameScene.eLayer.BULLET);
        ArrayList<GameObject> sprites = GameScene.getInstance().getLayer(GameScene.eLayer.SPRITE);

        for (GameObject o1 : enemies) {
            if (!o1.getIsValid()) continue;
            Enemy enemy = (Enemy) o1;

            // 적과 플레이어 간의 충돌 처리
            if (player.getInvincibleTime() == 0.0f &&
                isCollided(enemy.getHitBox(), player.getHitBox())) {
                enemy.onHit(player);
                player.onHit(enemy);
            }

            // 적과 총알 간의 충돌 처리
            for (GameObject o2 : bullets) {
                if (!o2.getIsValid()) continue;

                Bullet bullet = (Bullet) o2;
                if (isCollided(enemy.getHitBox(), bullet.getHitBox())) {
                    // 불
                    if (bullet instanceof FlameBullet) {
                        FlameBullet piercingBullet = (FlameBullet) bullet;
                        if (piercingBullet.getHitList().contains(enemy))
                            continue;
                        piercingBullet.addHitList(enemy);
                        enemy.onHit(bullet);
                        continue;
                    }

                    // 번개
                    if (bullet instanceof LightningBullet) {
                        GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);
                        continue;
                    }

                    // 일반 총알
                    enemy.onHit(bullet);
                    GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);
                }
            }

            // 적과 스플래시 스프라이트 간의 충돌 처리
            for (GameObject o2 : sprites) {
                if (!o2.getIsValid()) continue;
                if (!(o2 instanceof SplashSprite)) continue;

                SplashSprite sprite = (SplashSprite) o2;
                if (sprite.getUsed()) continue;

                if (isCollided(enemy.getHitBox(), sprite.getHitBox()))
                    enemy.onHit(sprite);
            }
        }

        for (GameObject o : sprites) {
            if (!o.getIsValid()) continue;
            if (!(o instanceof SplashSprite)) continue;

            SplashSprite sprite = (SplashSprite) o;
            sprite.setUsed(true);
        }
    }

    public void checkPlayerCollision() {
        Player player = GameScene.getInstance().getPlayer();
        ArrayList<GameObject> exps = GameScene.getInstance().getLayer(GameScene.eLayer.EXP);

        for (GameObject o : exps) {
            if (!o.getIsValid()) continue;
            Exp exp = (Exp) o;
            if (isCollided(player.getHitBox(), exp.getHitBox())) {
                player.addExp(exp.getExp());
                GameScene.getInstance().remove(GameScene.eLayer.EXP, exp);
            }
        }
    }

    public static boolean isCollided(RectF hitBox1, RectF hitBox2) {
        return RectF.intersects(hitBox1, hitBox2);
    }
}
