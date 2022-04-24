package com.laivy.the.shape.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class BitmapPool {
    private static final HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    public static Bitmap get(int bitmapResourceId) {
        Bitmap bitmap = bitmaps.get(bitmapResourceId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, bitmapResourceId);
            bitmaps.put(bitmapResourceId, bitmap);
        }
        return bitmap;
    }
}
