package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.BitmapPool;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.GameScene;
import com.laivy.the.shape.game.object.ui.HPBar;
import com.laivy.the.shape.game.object.ui.Relic;
import com.laivy.the.shape.game.object.ui.Result;
import com.laivy.the.shape.game.object.ui.Reward;
import com.laivy.the.shape.game.object.ui.TextObject;

import java.util.ArrayList;

public class Player extends GameObject {
    private final float MAX_ROTATE_DEGREE = 180.0f;
    private boolean isMove;
    private int maxHp;
    private int hp;
    private int def;
    private int exp;
    private int[] reqExp;
    private int level;
    private int damage;
    private float fireSpeed;
    private float fireTimer;
    private float targetRotateDegree;
    private float currRotateDegree;
    private float speed;
    private float invincibleTime;
    private float knockBackDuration;
    private float knockBackTimer;
    private float knockBackPower;
    private PointF knockBackDirection;
    private PointF direction;
    private HPBar hpBar;
    private ArrayList<Relic> relics;

    public Player() {
        // 컨트롤러로 조종 중인지
        isMove = false;

        // 플레이어 관련
        //maxHp = (int) Metrics.getFloat(R.dimen.PLAYER_HP);
        maxHp = (int) 10;
        hp = maxHp;
        def = 0;
        speed = Metrics.getFloat(R.dimen.PLAYER_SPEED);
        direction = new PointF(0.0f, -1.0f);
        level = 1;
        exp = 0;
        reqExp = new int[]{ 2, 6, 7, 8, 9, 10 };
        invincibleTime = 0.0f;

        // 총알 관련
        damage = (int) Metrics.getFloat(R.dimen.PLAYER_DMG);
        fireSpeed = Metrics.getFloat(R.dimen.PLAYER_FIRE_SPEED);
        fireTimer = 0.0f;
        
        // 회전
        targetRotateDegree = 0.0f;
        currRotateDegree = 0.0f;

        // 넉백
        knockBackDuration = 0.0f;
        knockBackTimer = 0.0f;
        knockBackPower = 0.0f;
        knockBackDirection = new PointF();
        
        // 체력바
        hpBar = new HPBar();
        hpBar.setHeightOffset(BitmapPool.get(R.mipmap.player).getHeight() * 0.8f);
        hpBar.setValue(hp);
        hpBar.setMaxValue(maxHp);
        
        // 히트박스
        hitBox = new RectF(-20.0f, -20.0f, 20.0f, 20.0f);

        // 유물들
        relics = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        Sprite sprite = new Sprite();
        sprite.addImage(R.mipmap.explosition0);
        sprite.addImage(R.mipmap.explosition1);
        sprite.addImage(R.mipmap.explosition2);
        sprite.addImage(R.mipmap.explosition3);
        sprite.addImage(R.mipmap.explosition4);
        sprite.addImage(R.mipmap.explosition5);
        sprite.setOnlyOnce(true);
        sprite.setBitmapWidth(sprite.getBitmapWidth() * 2.0f);
        sprite.setBitmapHeight(sprite.getBitmapHeight() * 2.0f);
        sprite.setPosition(position.x, position.y);
        GameScene.getInstance().add(GameScene.eLayer.SPRITE, sprite);

        Result result = new Result();
        GameScene.getInstance().add(GameScene.eLayer.UI, result);
        GameScene.getInstance().setGameOver(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isMove = false;
                break;
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                break;
        }
        return false;
    }

    public void onHit(GameObject object) {
        if (object instanceof Enemy) {
            // 넉백, 무적, 체력 감소
            Enemy enemy = (Enemy) object;
            knockBackDirection = enemy.getDirection();
            knockBackDuration = 0.5f;
            knockBackPower = 100.0f;
            invincibleTime = 0.5f;
            addHp(-enemy.getDamage() + def);

            // 유물 효과
            for (Relic r : relics)
                enemy.onHit(r);
        }
    }

    public void onLevelUp() {
        exp = 0;
        ++level;

        // 보상 UI 생성, 게임 일시 정지
        Reward reward = new Reward(Metrics.width * 0.2f, Metrics.height * 0.5f);
        GameScene.getInstance().add(GameScene.eLayer.UI, reward);
        GameScene.getInstance().setRunning(false);
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid) return;

        // 피격 시 넉백
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

            // 체력바 최신화
            hpBar.setPosition(position.x, position.y);
            hpBar.setValue(hp);
            hpBar.setMaxValue(maxHp);
            hpBar.update(deltaTime);

            super.update(deltaTime);
            knockBackTimer += deltaTime;
            if (knockBackTimer > knockBackDuration) {
                knockBackDuration = 0.0f;
                knockBackTimer = 0.0f;
            }
            return;
        }

        // 회전해야할 각도가 남아있다면 회전
        if (targetRotateDegree != 0.0f) {
            int sign = (int) (targetRotateDegree / Math.abs(targetRotateDegree));
            currRotateDegree += sign * MAX_ROTATE_DEGREE * deltaTime;
            targetRotateDegree += -sign * MAX_ROTATE_DEGREE * deltaTime;

            if (sign > 0) {
                targetRotateDegree = Math.max(0.0f, targetRotateDegree);
            }
            else {
                targetRotateDegree = Math.min(0.0f, targetRotateDegree);
            }

            // 현재 각도를 0 ~ 360으로 제한
            if (currRotateDegree < 0.0f)
                currRotateDegree = 360.0f - currRotateDegree;
            else if (currRotateDegree > 360.0f)
                currRotateDegree -= 360.0f;

            // 방향 최신화
            matrix.reset();
            matrix.postRotate(currRotateDegree);
            float[] pos = { 0.0f, -1.0f };
            matrix.mapPoints(pos);
            direction.x = pos[0];
            direction.y = pos[1];
        }

        // 이동, dstRect 최신화
        if (isMove) {
            position.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
            hitBox.offset(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
        }
        super.update(deltaTime);

        // 총 발사
        if (fireTimer >= fireSpeed) fire();
        fireTimer += deltaTime;

        // 체력바 최신화
        hpBar.setPosition(position.x, position.y);
        hpBar.setValue(hp);
        hpBar.setMaxValue(maxHp);
        hpBar.update(deltaTime);

        // 무적 시간 감소
        invincibleTime = Math.max(0.0f, invincibleTime - deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid) return;

        canvas.save();
        canvas.rotate(currRotateDegree, position.x, position.y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
        hpBar.draw(canvas);
    }

    public void fire() {
        Bullet bullet = new Bullet();
        bullet.setBitmap(R.mipmap.bullet);
        bullet.setDamage(damage);
        bullet.setPosition(position.x, position.y);
        bullet.setDirection(direction.x, direction.y);
        GameScene.getInstance().add(GameScene.eLayer.BULLET, bullet);

        fireTimer = 0.0f;
    }

    public void setTargetRotateDegree(float degree) {
        // 현재 각도에서 몇도 회전해야되는 지 계산
        float delta = Math.abs(currRotateDegree - degree);
        if (delta > 180.0f) {
            delta = 360.0f - delta;
            if (currRotateDegree < degree)
                delta = -delta;
        }
        else {
            if (currRotateDegree > degree)
                delta = -delta;
        }
        targetRotateDegree = delta;
    }

    public void setDirection(float x, float y) {
        if (x == 0.0f && y == 0.0f) {
            direction.set(0.0f, 0.0f);
            return;
        }

        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        direction.set(x, y);
    }

    public void addHp(int value) {
        hp = Math.max(0, Math.min(maxHp, hp + value));
        if (hp == 0) GameScene.getInstance().remove(GameScene.eLayer.PLAYER, this);

        // 데미지 표기
        TextObject textObject = new TextObject();
        if (value < 0)
            textObject.setColor(Color.RED);
        else
            textObject.setColor(Color.GREEN);
        textObject.setTextSize(40.0f);
        textObject.setText(Integer.toString(Math.abs(value)));
        textObject.setLifeTime(0.5f);
        textObject.setPosition(position.x, position.y - getBitmapHeight());
        GameScene.getInstance().add(GameScene.eLayer.TEXT, textObject);
    }

    public void addRelic(Relic relic) {
        switch (relic.getId()) {
            case 0:
                damage += 3;
                break;
        }

        relic.setBitmapWidth(Metrics.width * 0.08f);
        relic.setBitmapHeight(Metrics.width * 0.08f);
        relic.setPosition((relic.getBitmapWidth() / 2.0f) + (relics.size() * relic.getBitmapWidth() / 2.0f), relic.getBitmapHeight() / 2.0f);
        relics.add(relic);

        GameScene.getInstance().add(GameScene.eLayer.UI, relic);
    }


    public boolean hasRelic(int relicId) {
        for (Relic r : relics) {
            if (r.getId() == relicId)
                return true;
        }
        return false;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getExp() {
        return exp;
    }

    public int getReqExp() {
        return reqExp[level - 1];
    }

    public int getLevel() {
        return level;
    }

    public float getInvincibleTime() {
        return invincibleTime;
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    public void addDef(int def) {
        this.def += def;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public int getDamage() {
        return damage;
    }

    public ArrayList<Relic> getRelics() {
        return relics;
    }
}
