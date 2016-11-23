package com.wow.learning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    CircleView mFirstCv;
    CircleView mSecondCv;
    CircleView mThirdCv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        mFirstCv = createCv();
        float fx = mFirstCv.getCx();
        float fy = mFirstCv.getCy();
        float fr = mFirstCv.getRadius();

        float sx = 300;
        float sy = 900;
        float sr = 0;

        float dx = Math.abs(fx - sx);
        float dy = Math.abs(fy - sy);
        float dr = (float) Math.sqrt(dx * dx + dy * dy) - fr;

        float tx;
        float ty;
        float tr;

        mRoot.addView(createCv());
        mRoot.addView(createCv(sx, sy, sr));
        mRoot.addView(createCv(600, 900, 100));
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

    private void threeTangent(CircleView base, CircleView left, CircleView right) {
        while (! (isTangent(base, left) && isTangent(base, right) && isTangent(left, right)) ) {
            right.setCy(left.getCy());
            right.setCx(2 * base.getCx() - left.getCx());
            right.setRadius(left.getRadius());

            left.setCx(left.getCx() + 0.1f);
        }
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

        float dv;
        if ( (dv = Math.abs(dx * dx + dy * dy - dr * dr)) <= 0.1) {
            return true;
        }

        return false;
    }
}
