package com.laivy.the.shape.framework;

import android.content.res.Resources;
import android.util.TypedValue;

import com.laivy.the.shape.game.GameView;

public class Metrics {
    public static int width;
    public static int height;

    public static float getFloat(int dimenResourceId) {
        Resources res = GameView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResourceId, outValue, true);
        return outValue.getFloat();
    }
}