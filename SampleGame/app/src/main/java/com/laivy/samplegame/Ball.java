package com.laivy.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball implements GameObject {
    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private float dx, dy;

    public Ball(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        dstRect.set(0, 0, 200, 200);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update() {
        MainGame game = MainGame.getInstance();
        float dx = this.dx * game.frameTime;
        float dy = this.dy * game.frameTime;
        dstRect.offset(dx, dy);
        if (dx > 0) {
            if (dstRect.right > Metrics.width)
                this.dx = -this.dx;
        } else {
            if (dstRect.left < 0)
                this.dx = -this.dx;
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height)
                this.dy = -this.dy;
        } else {
            if (dstRect.top < 0)
                this.dy = -this.dy;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
