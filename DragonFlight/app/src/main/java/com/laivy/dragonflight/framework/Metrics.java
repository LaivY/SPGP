package com.laivy.dragonflight.framework;

import android.content.res.Resources;
import android.util.TypedValue;

import com.laivy.dragonflight.framework.GameView;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        return outValue.getFloat();
    }
}
