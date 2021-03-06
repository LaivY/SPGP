package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.Audio;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.object.Background;
import com.laivy.the.shape.game.object.Player;
import com.laivy.the.shape.game.system.CollisionChecker;
import com.laivy.the.shape.game.system.EnemyGenerator;
import com.laivy.the.shape.game.ui.Controller;
import com.laivy.the.shape.game.ui.ExpBar;
import com.laivy.the.shape.game.ui.PlayTimer;
import com.laivy.the.shape.game.ui.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScene {
    public enum eLayer {
        BACKGROUND, BULLET, EXP, ENEMY, PLAYER, SPRITE, UI, TEXT, SYSTEM
    }
    private static GameScene instance;
    private Map<eLayer, ArrayList<GameObject>> layers;
    private Player player;
    private Camera camera;
    private float playTime;
    private float gameOverDelay;
    private boolean isRunning;
    private boolean isGameOver;

    private GameScene() {
        instance = null;
        player = null;
        camera = null;
        playTime = 0.0f;
        gameOverDelay = 1.0f;
        isRunning = true;
        isGameOver = false;
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
        if (!isRunning) return;
        if (player.getHp() > 0)
            playTime += deltaTime;
        else {
            gameOverDelay -= deltaTime;
            if (gameOverDelay <= 0) {
                Result result = new Result();
                GameScene.getInstance().add(GameScene.eLayer.UI, result);
                isGameOver = true;
                isRunning = false;
            }
        }

        camera.update(deltaTime);
        for (eLayer key : eLayer.values())
            for (GameObject o : layers.get(key))
                o.update(deltaTime);
    }

    public void draw(Canvas canvas) {
        // ?????? ?????? ?????????
        camera.on(canvas);
        for (eLayer key : eLayer.values()) {
            if (key == eLayer.UI) continue;
            for (GameObject o : layers.get(key)) {
                o.draw(canvas);
            }
        }
        camera.off(canvas);

        // UI??? ???????????? ????????? ?????? ?????????.
        for (GameObject ui : layers.get(eLayer.UI))
            ui.draw(canvas);
    }

    public void init() {
        // ????????? ?????????
        Audio.init();
        Audio.loadSound(R.raw.hit);
        Audio.loadSound(R.raw.enemy);
        Audio.loadSound(R.raw.exp);
        Audio.loadSound(R.raw.gameover);

        // ????????? ??????
        Audio.playMusic(R.raw.bgm);

        // ?????? ?????????
        playTime = 0.0f;
        gameOverDelay = 1.0f;
        isRunning = true;
        isGameOver = false;

        // ????????? ?????????
        layers = new HashMap<>();
        for (eLayer layer : eLayer.values())
            layers.put(layer, new ArrayList<>());

        // ????????????
        player = new Player();
        player.setBitmap(R.mipmap.player);
        layers.get(eLayer.PLAYER).add(player);

        // ?????????
        camera = new Camera();

        // ??????
        Background background = new Background();
        background.setBitmap(R.mipmap.background);
        background.setCamera(camera);
        layers.get(eLayer.BACKGROUND).add(background);

        // ????????????
        GameObject stick = new GameObject();
        stick.setBitmap(R.mipmap.controller);
        stick.setBitmapWidth(Controller.RADIUS);
        stick.setBitmapHeight(Controller.RADIUS);
        Controller controller = new Controller();
        controller.setBitmap(R.mipmap.controller_base);
        controller.setBitmapWidth(Controller.RADIUS * 2.0f);
        controller.setBitmapHeight(Controller.RADIUS * 2.0f);
        controller.setStick(stick);
        layers.get(eLayer.UI).add(controller);

        // ????????????
        ExpBar expBar = new ExpBar();
        layers.get(eLayer.UI).add(expBar);

        // ?????????
        PlayTimer playTimer = new PlayTimer();
        layers.get(eLayer.UI).add(playTimer);

        // ????????????
        CollisionChecker checker = new CollisionChecker();
        layers.get(eLayer.SYSTEM).add(checker);

        // ??? ?????????
        EnemyGenerator enemyGenerator = new EnemyGenerator();
        layers.get(eLayer.SYSTEM).add(enemyGenerator);
    }

    public void add(eLayer layer, GameObject gameObject) {
        GameView.view.post(() -> layers.get(layer).add(gameObject));
    }

    public void remove(eLayer layer, GameObject gameObject) {
        gameObject.onDestroy();
        gameObject.setIsValid(false);
        GameView.view.post(() -> layers.get(layer).add(gameObject));
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public ArrayList<GameObject> getLayer(eLayer layer) {
        return layers.get(layer);
    }

    public Player getPlayer() {
        return player;
    }

    public float getPlayTime() {
        return playTime;
    }

    public boolean getGameOver() {
        return isGameOver;
    }
}
