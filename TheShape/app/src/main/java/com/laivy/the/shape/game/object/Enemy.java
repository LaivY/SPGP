package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.Audio;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.ui.Relic;
import com.laivy.the.shape.game.object.ui.TextObject;

public class Enemy extends GameObject {
    private int hp;
    private int damage;
    private float speed;
    private float rotate;
    private float knockBackDuration;
    private float knockBackTimer;
    private float knockBackPower;
    private PointF knockBackDirection;
    private PointF direction;

    public Enemy() {
        hp = 10;
        damage = 10;
        speed = 80.0f;
        rotate = 0.0f;
        knockBackDuration = 0.0f;
        knockBackTimer = 0.0f;
        knockBackPower = 0.0f;
        knockBackDirection = new PointF();
        direction = new PointF();
        hitBox = new RectF(-30.0f, -30.0f, 30.0f, 30.0f);
    }

    public void onHit(GameObject object) {
        if (object instanceof Bullet) {
            Bullet bullet = (Bullet) object;
            hp -= bullet.getDamage();
            knockBackDuration = 0.5f;
            knockBackPower = 300.0f;

            PointF bulletDirection = bullet.getDirection();
            knockBackDirection.x = bulletDirection.x;
            knockBackDirection.y = bulletDirection.y;

            // 데미지 표기
            TextObject damageText = new TextObject();
            damageText.setColor(Color.WHITE);
            damageText.setTextSize(40.0f);
            damageText.setText(Integer.toString(bullet.getDamage()));
            damageText.setLifeTime(0.5f);
            damageText.setPosition(position.x, position.y - getBitmapHeight());
            GameScene.getInstance().add(GameScene.eLayer.TEXT, damageText);

            // 효과음 출력
            Audio.playSound(R.raw.hit);
        }
        else if (object instanceof Player) {
            knockBackDuration = 0.5f;
            knockBackPower = 500.0f;

            knockBackDirection.x = -direction.x;
            knockBackDirection.y = -direction.y;
        }
        else if (object instanceof Relic) {
            Relic relic = (Relic) object;
            switch (relic.getId())
            {
                case 2:
                    hp -= GameScene.getInstance().getPlayer().getDamage();
                    TextObject damageText = new TextObject();
                    damageText.setColor(Color.WHITE);
                    damageText.setTextSize(40.0f);
                    damageText.setText(Integer.toString(GameScene.getInstance().getPlayer().getDamage()));
                    damageText.setLifeTime(0.5f);
                    damageText.setPosition(position.x, position.y - getBitmapHeight());
                    GameScene.getInstance().add(GameScene.eLayer.TEXT, damageText);
                    break;
            }
        }

        // 체력이 0이하라면 삭제
        if (hp <= 0) {
            GameScene.getInstance().remove(GameScene.eLayer.ENEMY, this);
        }
    }

    @Override
    public void onDestroy() {
        Exp exp = new Exp();
        exp.setBitmap(R.mipmap.exp);
        exp.setPosition(position.x, position.y);
        GameScene.getInstance().add(GameScene.eLayer.EXP, exp);

        // 효과음
        Audio.playSound(R.raw.enemy);

        // 피가 담긴 병
        Player player = GameScene.getInstance().getPlayer();
        if (player.hasRelic(Relic.BLOOD_VIAL)) {
            player.addHp(1);
        }

        // 고기덩어리
        if (player.hasRelic(Relic.MEAT) && player.getHp() <= player.getMaxHp() / 2.0f) {
            player.addHp(3);
        }
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;

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
            PointF playerPosition = GameScene.getInstance().getPlayer().getPosition();
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public PointF getDirection() {
        return direction;
    }
}
