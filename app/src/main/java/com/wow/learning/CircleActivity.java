package com.wow.learning;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.wow.learning.widget.CircleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljinjin on 11/23/16.
 */

public class CircleActivity extends AppCompatActivity {
    @BindView(R.id.sroot_layout)
    RelativeLayout mRoot;

    CircleView mBaseCv;
    CircleView mCloneCv;

    private static HandlerThread workingThread = new HandlerThread("clone");
    static {
        workingThread.start();
    }

    private static final int CLONE_CIRCLE = 0;
    private static final int SHOW_CIRCLE = 1;

    private Handler mHandler = new Handler(workingThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLONE_CIRCLE:
                    startClone((CircleView) msg.obj);
                    break;
                case SHOW_CIRCLE:
                    if (msg.arg1 == 1) {
                        showCircle((CircleView) msg.obj, true);
                    } else {
                        showCircle((CircleView) msg.obj, false);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        mBaseCv = createCv();
        float fx = mBaseCv.getCx();
        float fy = mBaseCv.getCy();
        float fr = mBaseCv.getRadius();

        float sx = 300;
        float sy = 900;
        float sr = 0;

        float dx = Math.abs(fx - sx);
        float dy = Math.abs(fy - sy);
        sr = (float) Math.sqrt(dx * dx + dy * dy) - fr;
        CircleView firstChild = createCv(sx, sy, sr);

        mRoot.addView(mBaseCv);
        mRoot.addView(firstChild);
        sendMsg(CLONE_CIRCLE, firstChild);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workingThread.quit();
    }

    private void sendMsg(int msg, CircleView circle) {
        sendMsg(msg, true, circle, 0);
    }
    private void sendMsg(int msg , boolean continueClone, CircleView circle, long delay) {
        int goOn = 0;
        if (continueClone) {
            goOn = 1;
        }
        Message message = mHandler.obtainMessage(msg, goOn, 0, circle);
        mHandler.sendMessageDelayed(message, delay);
    }

    private void showCircle(final CircleView target, final boolean continueClone) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRoot.addView(target);
                if (continueClone) {
                    sendMsg(CLONE_CIRCLE, target);
                }
            }
        });
    }

    private void startClone(CircleView targetCircle) {
        CircleView newCircle = clone(mBaseCv, targetCircle);
        sendMsg(SHOW_CIRCLE, newCircle);
    }

    private CircleView clone(CircleView baseCircle, CircleView target) {
        float maxXay = baseCircle.getCx() + baseCircle.getRadius() + target.getRadius();
        float minXay = baseCircle.getCx() - baseCircle.getRadius() - target.getRadius();
        float maxYay = baseCircle.getCy() + baseCircle.getRadius() + target.getRadius();
        float minYay = baseCircle.getCy() - baseCircle.getRadius() - target.getRadius();

        float tx = target.getCx();
        float ty = target.getCy();
        float tr = target.getRadius();
        CircleView clone = createCv(tx, ty, tr);

        while (!isTangent(target, clone)) {
            int symbol = 1;

            if (ty < baseCircle.getCy()) {
                symbol = -1;
            }

            if (tx > maxXay) {
                symbol = -1;
            }

            if (tx < minXay) {
                symbol = 1;
            }

            tx += symbol * 0.1f;
            float dx = Math.abs(baseCircle.getCx() - tx);
            float dr = tr + baseCircle.getRadius();
            ty = (float) Math.sqrt(Math.abs(dr * dr - dx * dx)) * symbol + baseCircle.getCy();

            Log.i("liauau", "tx = " + tx + ", ty = " + ty +
                    ", xRange[ " + minXay + ", " + maxXay + " ], yRange[ " + minYay + ", " + maxYay + " ]" );
            clone.setCx(tx);
            clone.setCy(ty);
        }
        return clone;
    }

    private CircleView createCv() {
        return (CircleView) getLayoutInflater().inflate(R.layout.wiget_circle_view, null);
    }

    private CircleView createCv(float cx, float cy, float r) {
        CircleView cv = (CircleView) getLayoutInflater().inflate(R.layout.wiget_circle_view, null);
        cv.setCx(cx);
        cv.setCy(cy);
        cv.setRadius(r);
        return cv;
    }

    private boolean isTangent(CircleView f, CircleView s) {
        float fx = f.getCx();
        float fy = f.getCy();
        float fr = f.getRadius();

        float sx = s.getCx();
        float sy = s.getCy();
        float sr = s.getRadius();

        float dx = Math.abs(fx - sx);
        float dy = Math.abs(fy - sy);
        float dr = fr + sr;

        if ( Math.sqrt( Math.abs(dx * dx + dy * dy - dr * dr) ) <= 4 ) {
            return true;
        }

        return false;
    }
}
