package com.wow.learning.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ljinjin on 11/24/16.
 */

public class CanvasView extends View {
    private Paint mPaint;
    private int mHeight;
    private int mWidth;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @TargetApi(21)
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

//        drawMyRect(canvas);
        drawMyCircle(canvas);
    }

    private void drawMyRect(Canvas canvas) {
        RectF rectF = new RectF(-400, -400, 400, 400);
        for (int i = 0; i < 20; i++) {
            canvas.drawRect(rectF, mPaint);
            canvas.scale(0.9f, 0.9f);
        }
    }

    private void drawMyCircle(Canvas canvas) {
//        canvas.drawCircle(0, 0, 400, mPaint);
//        canvas.drawCircle(0, 0, 350, mPaint);
        mPaint.setStrokeWidth(7);
        mPaint.setColor(Color.GRAY);
            for (int i = 0; i < 180; i++) {
                canvas.drawLine(0, 380, 0, 410, mPaint);
                canvas.drawLine(0, 365, 0, 375, mPaint);
                canvas.rotate(2);
            }
    }
}
