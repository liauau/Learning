package com.wow.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wow.learning.csvZoomImage.ZoomImageActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ljinjin on 11/23/16.
 */

public class SIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_standard_activity_btn)
    public void clickBtn() {
        Intent intent = new Intent(this, ZoomImageActivity.class);
        startActivity(intent);
    }
}
