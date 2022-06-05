package com.laivy.the.shape.game.object.system;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.Bullet;
import com.laivy.the.shape.game.object.Enemy;
import com.laivy.the.shape.game.object.Exp;
import com.laivy.the.shape.game.object.Player;
import com.laivy.the.shape.game.object.Sprite;
import com.laivy.the.shape.game.object.ui.Relic;
import com.laivy.the.shape.game.object.ui.TextObject;

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
                    enemy.onHit(bullet);
                    GameScene.getInstance().remove(GameScene.eLayer.BULLET, bullet);
                }
            }
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
