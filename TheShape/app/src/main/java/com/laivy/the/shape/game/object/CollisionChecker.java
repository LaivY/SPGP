package com.laivy.the.shape.game.object;

import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
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
                    // 총알 위치에 폭발 이펙트 추가
                    PointF bulletPosition = bullet.getPosition();
                    Sprite sprite = new Sprite();
                    sprite.addImage(R.mipmap.explosition0);
                    sprite.addImage(R.mipmap.explosition1);
                    sprite.addImage(R.mipmap.explosition2);
                    sprite.addImage(R.mipmap.explosition3);
                    sprite.addImage(R.mipmap.explosition4);
                    sprite.addImage(R.mipmap.explosition5);
                    sprite.setOnlyOnce(true);
                    sprite.setPosition(bulletPosition.x, bulletPosition.y);
                    GameScene.getInstance().add(GameScene.eLayer.SPRITE, sprite);

                    // 총알 삭제
                    GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);

                    // 적에게 피격당했다는 것을 알려줌
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
