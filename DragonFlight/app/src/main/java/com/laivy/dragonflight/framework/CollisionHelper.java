package com.laivy.dragonflight.framework;

import android.graphics.RectF;

public class CollisionHelper {
    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        RectF r1 = o1.getBoundingBox();
        RectF r2 = o2.getBoundingBox();

        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;
        return true;
    }
}
