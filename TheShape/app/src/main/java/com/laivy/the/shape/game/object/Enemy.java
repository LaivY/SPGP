package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

public class Enemy extends GameObject {
    private int hp;
    private float speed;
    private float rotate;
    private float knockBackDuration;
    private float knockBackTimer;
    private float knockBackPower;
    private PointF knockBackDirection;
    private PointF direction;
    private Player player;

    public Enemy() {
        hp = 10;
        speed = 80.0f;
        rotate = 0.0f;
        knockBackDuration = 0.0f;
        knockBackTimer = 0.0f;
        knockBackPower = 0.0f;
        knockBackDirection = new PointF();
        direction = new PointF();
        player = null;
        hitBox = new RectF(-30.0f, -30.0f, 30.0f, 30.0f);
    }

    public void onHit(GameObject object) {
        if (object instanceof Bullet) {
            Bullet bullet = (Bullet) object;
            hp -= bullet.getDmg();
            knockBackDuration = 0.5f;
            knockBackPower = 300.0f;

            PointF bulletDirection = bullet.getDirection();
            knockBackDirection.x = bulletDirection.x;
            knockBackDirection.y = bulletDirection.y;
        }
        else if (object instanceof Player) {
            //Player player = (Player) object;
            knockBackDuration = 0.5f;
            knockBackPower = 500.0f;

            knockBackDirection.x = -direction.x;
            knockBackDirection.y = -direction.y;
        }
    }

    public void onDestroy() {
        Exp exp = new Exp();
        exp.setBitmap(R.mipmap.exp);
        exp.setPosition(position.x, position.y);
        GameScene.getInstance().add(GameScene.eLayer.EXP, exp);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid || player == null) return;

        // 체력이 0이하라면 삭제
        if (hp <= 0) {
            onDestroy();
            GameScene.getInstance().remove(GameScene.eLayer.ENEMY, this);
            return;
        }

        // 넉백
        if (knockBackDuration != 0.0f) {
            float delta = (float) (knockBackPower * Math.cos(knockBackTimer * Math.PI / 2.0f / knockBackDuration));

            position.offset(
                knockBackDirection.x * delta * deltaTime,
                knockBackDirection.y * delta * deltaTime
            );
            hitBox.offset(
                knockBackDirection.x * delta * deltaTime,
                knockBackDirection.y * delta * deltaTime
            );

            knockBackTimer += deltaTime;
            if (knockBackTimer > knockBackDuration) {
                knockBackDuration = 0.0f;
                knockBackTimer = 0.0f;
            }
        }
        else {
            // 플레이어를 쫓아다니도록 설정
            PointF playerPosition = player.getPosition();
            float dx = playerPosition.x - position.x;
            float dy = playerPosition.y - position.y;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= length;
            dy /= length;
            direction.x = dx;
            direction.y = dy;
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
        //canvas.drawRect(hitBox, paint);
        canvas.save();
        canvas.rotate(rotate, position.x, position.y);
        super.draw(canvas);
        canvas.restore();
    }

    public void addHp(int hp) {
        this.hp += hp;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PointF getDirection() {
        return direction;
    }
}
