package com.wow.learning.csvZoomImage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private boolean mInflateOnce = false;
    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;
    private Matrix mScaleMatrix;
    private ScaleGestureDetector mGestureDetector;

    private int mLastPointerCount;
    private float mLastX;
    private float mLastY;
    private static int TOUCH_SLOP;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;



    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mGestureDetector = new ScaleGestureDetector(getContext(), this);
        setOnTouchListener(this);
        TOUCH_SLOP = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @TargetApi(21)
    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        if (!mInflateOnce) {

            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;

            if (dw > width && dh < height) {
                scale = width * 1f / dw;
            } else if (dw < width && dh > height) {
                scale = height * 1f / dh;
            } else if (dw > width && dh > height) {
                scale = Math.min(width * 1f / dw, height * 1f / dh);
            } else if (dw < width && dh < height) {
                scale = Math.min(width * 1f / dw, height * 1f / dh);
            }

            scale = Math.min(width * 1f / dw, height * 1f / dh);
            mInitScale = scale;
            mMidScale = mInitScale * 2;
            mMaxScale = mInitScale * 4;

            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);
            mInflateOnce = true;
        }
    }

    private float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (canScaleUp(scale, scaleFactor) ||
                canScaleDown(scale, scaleFactor)) {

            if (getDrawable() == null) {
                return true;
            }

            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            } else if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            Log.i("liauau", "scale=" + scale + ", scaleFactor=" + scaleFactor + ", [" + mInitScale + ", " + mMaxScale + "]");
            Log.i("liauau", "mulValue=" + scale * scaleFactor);
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    private boolean canScaleDown(float scale, float scaleFactor) {
        Log.i("ww", "down factor " + scaleFactor + ", canDown? " + (scaleFactor < 1f));
        return (scale > mInitScale) && (scaleFactor < 1f);
    }

    private boolean canScaleUp(float scale, float scaleFactor) {
        Log.i("ww", "up factor " + scaleFactor + ", canUp? " + (scaleFactor > 1f));
        return (scale < mMaxScale) && (scaleFactor > 1f);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        float x = 0;
        float y = 0;

        int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointCount;
        y /= pointCount;

        if (mLastPointerCount != pointCount) {
            mLastX = x;
            mLastY = y;
            isCanDrag = false;
        }
        mLastPointerCount = pointCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.height() > getHeight() + 0.01 || rectF.width() > getWidth() + 0.01) {
                    if (rectF.right > getWidth() && rectF.left < 0) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                } else {
                    if (getDrawable() == null) {
                        return true;
                    }

                    isCheckLeftAndRight = isCheckTopAndBottom = true;
                    if (rectF.width() < getWidth()) {
                        isCheckLeftAndRight = false;
                        dx = 0;
                    }

                    if (rectF.height() < getHeight()) {
                        isCheckTopAndBottom = false;
                        dy = 0;
                    }

                    mScaleMatrix.postTranslate(dx, dy);
                    checkBorderWhenTranslate();
                    setImageMatrix(mScaleMatrix);
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }

    /**
     * 获取图片的坐标
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        return rectF;
    }

    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }

            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }

            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private boolean isMoveAction(double dx, double dy) {
        return Math.sqrt(dx * dx + dy * dy) >= TOUCH_SLOP;
    }

    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < getHeight() && isCheckTopAndBottom) {
            deltaY = getHeight() - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < getWidth() && isCheckLeftAndRight) {
            deltaX = getWidth() - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }
}
