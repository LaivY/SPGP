package com.laivy.morecontrols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private Paint bgrPaint = new Paint();
    private Paint[] shapePaints = new Paint[9];

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 배경 페인트 설정
        bgrPaint.setColor(0xFFFFC0CB);

        // 도형을 그릴 페인트 생성 및 설정
        for (int i = 0; i < shapePaints.length; ++i)
            shapePaints[i] = new Paint();
        shapePaints[0].setColor(Color.WHITE);   // 왼쪽 위
        shapePaints[1].setColor(Color.RED);     // 가운데 위
        shapePaints[2].setColor(0xFFFF7F00);    // 오른쪽 위
        shapePaints[3].setColor(Color.YELLOW);  // 왼쪽 중앙
        shapePaints[4].setColor(Color.GREEN);   // 정중앙
        shapePaints[5].setColor(Color.BLUE);    // 오른쪽 중앙
        shapePaints[6].setColor(0xFF000080);    // 왼쪽 아래
        shapePaints[7].setColor(0xFF8B00FF);    // 가운데 아래
        shapePaints[8].setColor(Color.BLACK);   // 오른쪽 아래
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //Paint paint = new Paint();
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int contentWidth = width - paddingLeft - paddingRight;
        int contentHeight = height - paddingTop - paddingBottom;

        // 원의 지름 또는 사각형의 한 변의 길이 = 한 영역의 모서리 중 작은 길이 / 2
        int areaWidth = contentWidth / 3;
        int areaHeight = contentHeight / 3;
        int shapeLength = Math.min(areaWidth, areaHeight) / 2;

        // 배경 그리기
        drawBackground(canvas, paddingLeft, paddingTop, contentWidth, contentHeight);

        // 도형 그리기
        for (int i = 0; i < 9; ++i) {
            int x = i % 3;
            int y = i / 3;
            float cx = paddingLeft + areaWidth / 2.0f + x * areaWidth;
            float cy = paddingTop + areaHeight / 2.0f + y * areaHeight;

            if (i % 2 == 0) {
                canvas.drawCircle(cx, cy, shapeLength / 2.0f, shapePaints[i]);
            } else {
                canvas.drawRect(
                        cx - shapeLength / 2.0f,
                        cy + shapeLength / 2.0f,
                        cx + shapeLength / 2.0f,
                        cy - shapeLength / 2.0f,
                        shapePaints[i]);
            }
        }
    }

    private void drawBackground(Canvas canvas, int paddingLeft, int paddingTop, int contentWidth, int contentHeight) {
        canvas.drawRoundRect(paddingLeft, paddingTop, paddingLeft + contentWidth, paddingTop + contentHeight, 30, 30, bgrPaint);
    }
}
