package com.laivy.the.shape.game.object;

import com.laivy.the.shape.framework.GameObject;

import java.util.ArrayList;

public class FlameBullet extends Bullet {
    private final ArrayList<GameObject> hitList;

    public FlameBullet() {
        hitList = new ArrayList<>();
    }

    public void addHitList(GameObject o) {
        hitList.add(o);
    }

    public ArrayList<GameObject> getHitList() {
        return hitList;
    }
}
