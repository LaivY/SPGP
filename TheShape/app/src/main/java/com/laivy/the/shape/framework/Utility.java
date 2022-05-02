package com.laivy.the.shape.framework;

import java.util.Random;

public class Utility {
    private static final Random random = new Random();

    public static int getRandom(int min, int max) {
        int value = random.nextInt(max - min + 1);
        return value + min;
    }

    public static float getRandom(float min, float max) {
        float t = random.nextFloat();
        return min * (1.0f - t) + max * t;
    }
}
