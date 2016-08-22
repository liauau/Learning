package com.wow.learning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.wow.learning.jni.JNIProvider;

/**
 * Created by lz on 16/8/17.
 */
public class JniActivity extends AppCompatActivity {
    private TextView mJniTv;
    private TextView mJniProviderTv;

    static {
        System.loadLibrary("hello");
//        System.loadLibrary("JNIProvider"); 根据build.gradle文件决定加载哪个so
    }

    public static native String getStrFromC();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        mJniTv = (TextView) findViewById(R.id.str_tv);
        mJniProviderTv = (TextView) findViewById(R.id.tv_jni_provider);

        if (mJniTv != null) {
            mJniTv.setText(getStrFromC());
            Log.i("liauau", getStrFromC());
        }

        handleJni();
    }

    private void handleJni() {
        String str = getStrFromC() + JNIProvider.getName() + "\n"  + JNIProvider.getCount() + " " + JNIProvider.getMoney();
        mJniProviderTv.setText(str);
    }
}
