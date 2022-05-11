package com.laivy.the.shape.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.laivy.the.shape.R;
import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.game.object.Background;
import com.laivy.the.shape.game.object.CollisionChecker;
import com.laivy.the.shape.game.object.Controller;
import com.laivy.the.shape.game.object.Enemy;
import com.laivy.the.shape.game.object.EnemyGenerator;
import com.laivy.the.shape.game.object.ExpBar;
import com.laivy.the.shape.game.object.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScene {
    public enum eLayer {
        BACKGROUND, BULLET, EXP, ENEMY, PLAYER, UI, SYSTEM
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
        // 모든 객체 그리기
        camera.on(canvas);
        for (eLayer key : eLayer.values()) {
            if (key == eLayer.UI) continue;
            for (GameObject o : layers.get(key)) {
                o.draw(canvas);
            }
        }
        camera.off(canvas);

        // UI는 카메라의 영향을 받지 않는다.
        for (GameObject ui : layers.get(eLayer.UI))
            ui.draw(canvas);
    }

    public void init() {
        // 레이어 초기화
        layers = new HashMap<>();
        for (eLayer layer : eLayer.values())
            layers.put(layer, new ArrayList<>());

        // 플레이어
        player = new Player();
        player.setBitmap(R.mipmap.player);
        layers.get(eLayer.PLAYER).add(player);

        // 카메라
        camera = new Camera();
        camera.setPlayer(player);

        // 배경
        Background background = new Background();
        background.setBitmap(R.mipmap.background);
        background.setCamera(camera);
        layers.get(eLayer.BACKGROUND).add(background);

        // 컨트롤러
        GameObject stick = new GameObject();
        stick.setBitmap(R.mipmap.controller);
        Controller controller = new Controller();
        controller.setPlayer(player);
        controller.setBitmap(R.mipmap.controller_base);
        controller.setStick(stick);
        layers.get(eLayer.UI).add(controller);

        // 경험치바
        ExpBar expBar = new ExpBar();
        expBar.setPlayer(player);
        layers.get(eLayer.UI).add(expBar);

        // 충돌체커
        CollisionChecker checker = new CollisionChecker();
        layers.get(eLayer.SYSTEM).add(checker);

        // 적 생성기
        EnemyGenerator enemyGenerator = new EnemyGenerator();
        enemyGenerator.setPlayer(player);
        layers.get(eLayer.SYSTEM).add(enemyGenerator);
    }

    public void add(eLayer layer, GameObject gameObject) {
        GameView.view.post(() -> layers.get(layer).add(gameObject));
    }

    public void remove(eLayer layer, GameObject gameObject) {
        gameObject.setIsValid(false);
        GameView.view.post(() -> layers.get(layer).add(gameObject));
    }

    public ArrayList<GameObject> getLayer(eLayer layer) {
        return layers.get(layer);
    }

    public Player getPlayer() {
        return player;
    }
}
