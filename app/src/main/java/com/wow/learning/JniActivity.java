package com.wow.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wow.learning.jni.JNIProvider;

/**
 * Created by lz on 16/8/17.
 */
public class JniActivity extends AppCompatActivity {
    private TextView mJniTv;
    private TextView mJniProviderTv;
    private Button mBtn;

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
        mBtn = (Button) findViewById(R.id.start_siactivity_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JniActivity.this, SIActivity.class);
                JniActivity.this.startActivity(intent);
            }
        });

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
