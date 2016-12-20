package com.wow.learning.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wow.learning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljinjin on 11/16/16.
 */

public class CircleView extends View implements ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener {

    private float cx;
    private float cy;
    private float radius;
    private int color;
    private Paint mPaint;
    private RectF mRectF;

    private ScaleGestureDetector mSGDetector;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
            cx = arr.getFloat(R.styleable.CircleView_centerX, 100);
            cy = arr.getFloat(R.styleable.CircleView_centerY, 100);
            radius = arr.getFloat(R.styleable.CircleView_radius, 50);
            color = arr.getColor(R.styleable.CircleView_circle_color, Color.BLUE);
            arr.recycle();
        }

        mSGDetector = new ScaleGestureDetector(context, this);
        mPaint= new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mRectF = new RectF();

        setOnTouchListener(this);
    }

    @TargetApi(21)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mSGDetector = new ScaleGestureDetector(context, this);
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float mCx) {
        this.cx = mCx;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float mRadius) {
        this.radius = mRadius;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float mCy) {
        this.cy = mCy;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        radius *= scaleFactor;
        Log.i("liauau", "scaleFactor = " + scaleFactor + ", r = " + radius);
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mSGDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, radius, mPaint);
    }
}
