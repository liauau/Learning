package com.wow.learning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by lz on 16/8/17.
 */
public class JniActivity extends AppCompatActivity {
    private TextView mJniTv;

    static {
        System.loadLibrary("hello");
    }

    public static native String getStrFromC();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        mJniTv = (TextView) findViewById(R.id.str_tv);
        if (mJniTv != null) {
            mJniTv.setText(R.string.app_name);
        }
    }
}
