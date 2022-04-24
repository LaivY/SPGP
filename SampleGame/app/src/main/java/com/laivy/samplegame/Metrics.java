package com.laivy.samplegame;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }
}
