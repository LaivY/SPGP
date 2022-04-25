package com.laivy.the.shape.framework;

import android.graphics.PointF;

public class Utility {

    private Utility() {

    }

    public static void normalize(Float x, Float y) {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
    }
}
