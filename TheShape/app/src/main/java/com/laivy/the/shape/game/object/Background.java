package com.laivy.the.shape.game.object;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.laivy.the.shape.framework.GameObject;
import com.laivy.the.shape.framework.Metrics;
import com.laivy.the.shape.game.Camera;

public class Background extends GameObject {
    private Camera camera;
    private final RectF[] bgrRect;

    public Background() {
        camera = null;
        dstRect = null;
        bgrRect = new RectF[4];
        bgrRect[0] = new RectF();
        bgrRect[1] = new RectF();
        bgrRect[2] = new RectF();
        bgrRect[3] = new RectF();
    }

    @Override
    public void update(float deltaTime) {
        if (!isValid || camera == null) return;

        // 배경의 크기는 모두 같음
        bgrRect[0].set(-Metrics.width / 2.0f, -Metrics.height / 2.0f, Metrics.width / 2.0f, Metrics.height / 2.0f);
        bgrRect[1].set(-Metrics.width / 2.0f, -Metrics.height / 2.0f, Metrics.width / 2.0f, Metrics.height / 2.0f);
        bgrRect[2].set(-Metrics.width / 2.0f, -Metrics.height / 2.0f, Metrics.width / 2.0f, Metrics.height / 2.0f);
        bgrRect[3].set(-Metrics.width / 2.0f, -Metrics.height / 2.0f, Metrics.width / 2.0f, Metrics.height / 2.0f);

        // 카메라 위치
        PointF eye = camera.getPosition();
        int x = (int) Math.ceil(Math.abs(eye.x) / Metrics.width);
        int y = (int) Math.ceil(Math.abs(eye.y) / Metrics.height);
        if (eye.x < 0) x = -x;
        if (eye.y < 0) y = -y;

        // 좌측 상단
        bgrRect[0].offset(Metrics.width * (x + (eye.x > 0 ? -1 : 0)),
                          Metrics.height * (y + (eye.y > 0 ? -1 : 0)));

        // 우측 상단
        bgrRect[1].offset(Metrics.width * (x + (eye.x > 0 ? 0 : 1)),
                          Metrics.height * (y + (eye.y > 0 ? -1 : 0)));

        // 좌측 하단
        bgrRect[2].offset(Metrics.width * (x + (eye.x > 0 ? -1 : 0)),
                          Metrics.height * (y + (eye.y > 0 ? 0 : 1)));

        // 우측 하단
        bgrRect[3].offset(Metrics.width * (x + (eye.x > 0 ? 0 : 1)),
                          Metrics.height * (y + (eye.y > 0 ? 0 : 1)));
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isValid || bitmap == null) return;
        canvas.drawBitmap(bitmap, null, bgrRect[0], null);
        canvas.drawBitmap(bitmap, null, bgrRect[1], null);
        canvas.drawBitmap(bitmap, null, bgrRect[2], null);
        canvas.drawBitmap(bitmap, null, bgrRect[3], null);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
