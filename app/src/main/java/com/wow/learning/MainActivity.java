package com.wow.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.zoom_demo_btn)
    public void clickZoomDemo() {
        Intent intent = new Intent(this, ZoomImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.jni_demo_btn)
    public void clickJniDemo() {
        Intent intent = new Intent(this, JniActivity.class);
        startActivity(intent);
    }
}
