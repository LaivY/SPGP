package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.GameScene;

public class Supporter extends GameObject{
    public static final int FLAME_SUPPORTER     = 0;
    public static final int TORNADO_SUPPORTER   = 1;
    public static final int LIGHTNING_SUPPORTER = 2;
    private final int type;
    private final float speed;
    private final float radius;
    private final float attackSpeed;
    private float attackTimer;

    public Supporter(int type) {
        this.type = type;
        switch (type) {
            case FLAME_SUPPORTER:
                speed = 5.0f;
                attackSpeed = 1.0f;
                break;
            case TORNADO_SUPPORTER:
                speed = 3.0f;
                attackSpeed = 3.0f;
                break;
            case LIGHTNING_SUPPORTER:
                speed = 4.0f;
                attackSpeed = 5.0f;
                break;
            default:
                speed = 0.0f;
                attackSpeed = 0.0f;
                break;
        }
        radius = 10.0f;
        attackTimer = 0.0f;
    }

    @Override
    public void update(float deltaTime) {
        Player player = GameScene.getInstance().getPlayer();
        PointF playerPosition = player.getPosition();

        float targetX = playerPosition.x + player.getBitmapWidth() * 0.7f;
        float targetY = playerPosition.y - player.getBitmapHeight() * 0.7f;

        switch (type) {
            case TORNADO_SUPPORTER:
                targetX -= radius * 1.5f;
                targetY += radius * 2.5f;
                break;
            case LIGHTNING_SUPPORTER:
                targetX += radius * 1.5f;
                targetY += radius * 2.5f;
                break;
        }

        float dx = targetX - position.x;
        float dy = targetY - position.y;

        position.offset(dx * speed * deltaTime, dy * speed * deltaTime);
        super.update(deltaTime);

        // 발사
        if (attackTimer >= attackSpeed) {
            attackTimer = 0.0f;
            fire();
        }
        attackTimer += deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        switch (type) {
            case FLAME_SUPPORTER:
                paint.setColor(Color.RED);
                break;
            case TORNADO_SUPPORTER:
                paint.setColor(Color.BLUE);
                break;
            case LIGHTNING_SUPPORTER:
                paint.setColor(Color.YELLOW);
                break;
        }
        canvas.drawCircle(position.x, position.y, radius, paint);
    }

    public void fire() {
        Player player = GameScene.getInstance().getPlayer();
        PointF direction = player.getDirection();

        Bullet bullet = null;
        switch (type) {
            case FLAME_SUPPORTER:
                bullet = new FlameBullet();
                bullet.setBitmap(R.mipmap.flame_bullet);
                bullet.setDamage((int) Math.ceil(player.getDamage() / 3.0f));
                break;
            case TORNADO_SUPPORTER:
                bullet = new Bullet();
                bullet.setBitmap(R.mipmap.bullet);
                bullet.setDamage(player.getDamage());
                break;
            case LIGHTNING_SUPPORTER:
                bullet = new LightningBullet();
                bullet.setBitmap(R.mipmap.lightning_bullet);
                bullet.setDamage(0);
                break;
            default:
                return;
        }
        bullet.setPosition(position.x, position.y);
        bullet.setDirection(direction.x, direction.y);
        GameScene.getInstance().add(GameScene.eLayer.BULLET, bullet);
    }
}