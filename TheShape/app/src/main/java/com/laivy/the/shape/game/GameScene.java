package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;

import java.util.ArrayList;

public class GameScene {
    private static GameScene instance;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> uiObjects;
    private Player player;
    private Camera camera;

    private GameScene() {
        instance = null;
        gameObjects = null;
        uiObjects = null;
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
        for (GameObject o : gameObjects)
            result |= o.onTouchEvent(event);
        for (GameObject ui : uiObjects)
            result |= ui.onTouchEvent(event);
        return result;
    }

    public void update(float deltaTime) {
        camera.update(deltaTime);
        for (GameObject o : gameObjects)
            o.update(deltaTime);
        for (GameObject ui : uiObjects)
            ui.update(deltaTime);
    }

    public void draw(Canvas canvas) {
        camera.on(canvas);
        for (GameObject o : gameObjects)
            o.draw(canvas);
        camera.off(canvas);

        for (GameObject ui : uiObjects)
            ui.draw(canvas);
    }

    public void init() {
        gameObjects = new ArrayList<>();
        uiObjects = new ArrayList<>();

        player = new Player();
        player.setBitmap(R.mipmap.player);
        player.setPosition(50.0f, 50.0f);
        gameObjects.add(player);

        camera = new Camera();
        camera.setPlayer(player);

        // 카메라 작동 테스를 위한 오브젝트 배치
        GameObject test = new GameObject();
        test.setBitmap(R.mipmap.player);
        gameObjects.add(test);

        GameObject stick = new GameObject();
        stick.setBitmap(R.mipmap.controller);
        Controller controller = new Controller();
        controller.setPlayer(player);
        controller.setBitmap(R.mipmap.controller_base);
        controller.setStick(stick);
        uiObjects.add(controller);
    }

    public void add(GameObject gameObject) {
        GameView.view.post(() -> gameObjects.add(gameObject));
    }

    public void addUI(GameObject uiObject) {
        GameView.view.post(() -> uiObjects.add(uiObject));
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(() -> gameObjects.remove(gameObject));
    }

    public void removeUI(GameObject uiObject) {
        GameView.view.post(() -> uiObjects.remove(uiObject));
    }
}
