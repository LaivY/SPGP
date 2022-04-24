package com.laivy.the.shape.game;

import android.graphics.Canvas;

import com.laivy.the.shape.framework.GameObject;

import java.util.ArrayList;

public class GameScene {
    private static GameScene instance;
    private ArrayList<GameObject> gameObjects;

    public static GameScene getInstance() {
        if (instance == null)
            instance = new GameScene();
        return instance;
    }

    public void update(float deltaTime) {
        for (GameObject o : gameObjects)
            o.update();
    }

    public void draw(Canvas canvas) {
        for (GameObject o : gameObjects)
            o.draw(canvas);
    }
}
