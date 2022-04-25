package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;

import java.util.ArrayList;

public class GameScene {
    private static GameScene instance;
    private ArrayList<GameObject> gameObjects;
    private Player player;

    private GameScene() {
        instance = null;
        gameObjects = null;
        player = null;
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
        return result;
    }

    public void update(float deltaTime) {
        for (GameObject o : gameObjects)
            o.update(deltaTime);
    }

    public void draw(Canvas canvas) {
        for (GameObject o : gameObjects)
            o.draw(canvas);
    }

    public void init() {
        gameObjects = new ArrayList<>();

        player = new Player();
        player.setBitmap(R.mipmap.player);
        player.setPosition(50.0f, 50.0f);
        gameObjects.add(player);

        Controller controller = new Controller();
        controller.setPlayer(player);
        controller.setBitmap(R.mipmap.player);
        gameObjects.add(controller);
    }

    public void add(GameObject gameObject) {
        GameView.view.post(() -> gameObjects.add(gameObject));
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(() -> gameObjects.remove(gameObject));
    }
}
