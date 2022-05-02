package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.laivy.the.shape.framework.GameObject;

public class Enemy extends GameObject {
    private int hp;
    private float speed;
    private float rotate;
    private float knockBackTimer;
    private float knockBackPower;
    private PointF knockBackDirection;
    private Player player;

    public Enemy() {
        hp = 10;
        speed = 100.0f;
        rotate = 0.0f;
        knockBackTimer = 0.0f;
        knockBackPower = 0.0f;
        knockBackDirection = null;
        player = null;
        hitBox = new RectF(-30.0f, -30.0f, 30.0f, 30.0f);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid || player == null) return;

        // 체력이 0이하라면 삭제
        if (hp <= 0) {
            GameScene.getInstance().remove(GameScene.eLayer.ENEMY, this);
            return;
        }

        // 넉백
        if (knockBackTimer > 0.0f) {
            position.offset(
            knockBackDirection.x * knockBackPower * deltaTime,
            knockBackDirection.y * knockBackPower * deltaTime
            );
            hitBox.offset(
                knockBackDirection.x * knockBackPower * deltaTime,
                knockBackDirection.y * knockBackPower * deltaTime
            );
            knockBackTimer = Math.max(0.0f, knockBackTimer - deltaTime);
        }
        else {
            // 플레이어를 쫓아다니도록 설정
            PointF playerPosition = player.getPosition();
            float dx = playerPosition.x - position.x;
            float dy = playerPosition.y - position.y;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= length;
            dy /= length;
            position.offset(dx * speed * deltaTime, dy * speed * deltaTime);
            hitBox.offset(dx * speed * deltaTime, dy * speed * deltaTime);

            // 위에서 구한 방향벡터를 이용하여 회전한 각도 계산
            rotate = (float) Math.toDegrees(Math.atan2(dx, dy));
            rotate = Math.abs(rotate - 180.0f);
        }

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid || bitmap == null) return;
        canvas.drawRect(hitBox, paint);
        canvas.save();
        canvas.rotate(rotate, position.x, position.y);
        super.draw(canvas);
        canvas.restore();
    }

    public void onHit(Bullet bullet) {
        //hp -= bullet.getDmg();
        knockBackTimer = 0.5f;
        knockBackPower = 100.0f;
        knockBackDirection = bullet.getDirection();
    }

    public void addHp(int hp) {
        this.hp += hp;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
