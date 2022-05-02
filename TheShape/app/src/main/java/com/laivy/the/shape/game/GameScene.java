package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScene {
    public enum eLayer {
        BULLET, ENEMY, PLAYER, UI, SYSTEM
    }
    private static GameScene instance;
    private Map<eLayer, ArrayList<GameObject>> layers;
    private Player player;
    private Camera camera;

    private GameScene() {
        instance = null;
        player = null;
        camera = null;
    }

    public static GameScene getInstance() {
        if (instance == null)
            instance = new GameScene();
        return instance;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;
        for (eLayer key : eLayer.values())
            for (GameObject o : layers.get(key))
                result |= o.onTouchEvent(event);
        return result;
    }

    public void update(float deltaTime) {
        camera.update(deltaTime);
        for (eLayer key : eLayer.values())
            for (GameObject o : layers.get(key))
                o.update(deltaTime);
    }

    public void draw(Canvas canvas) {
        camera.on(canvas);
        for (eLayer key : eLayer.values()) {
            if (key == eLayer.UI) continue;
            for (GameObject o : layers.get(key)) {
                o.draw(canvas);
            }
        }
        camera.off(canvas);

        for (GameObject ui : layers.get(eLayer.UI))
            ui.draw(canvas);
    }

    public void init() {
        layers = new HashMap<>();
        for (eLayer layer : eLayer.values())
            layers.put(layer, new ArrayList<>());

        player = new Player();
        player.setBitmap(R.mipmap.player);
        player.setPosition(50.0f, 50.0f);
        layers.get(eLayer.PLAYER).add(player);

        camera = new Camera();
        camera.setPlayer(player);

        GameObject stick = new GameObject();
        stick.setBitmap(R.mipmap.controller);
        Controller controller = new Controller();
        controller.setPlayer(player);
        controller.setBitmap(R.mipmap.controller_base);
        controller.setStick(stick);
        layers.get(eLayer.UI).add(controller);

        // 테스트용 적
        Enemy enemy = new Enemy();
        enemy.setBitmap(R.mipmap.circle);
        enemy.setPlayer(player);
        layers.get(eLayer.ENEMY).add(enemy);
    }

    public void add(eLayer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                layers.get(layer).add(gameObject);
            }
        });
    }

    public void remove(eLayer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                layers.get(layer).add(gameObject);
            }
        });
    }
}
