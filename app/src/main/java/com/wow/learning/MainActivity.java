package com.wow.learning;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks2;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("liauau", "battery level is " + getBatteryLevel());
    }

    @OnClick(R.id.zoom_demo_btn)
    public void clickZoomDemo() {
        wifi();
        Intent intent = new Intent(this, ZoomImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.jni_demo_btn)
    public void clickJniDemo() {
        Intent intent = new Intent(this, JniActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cv_demo_btn)
    public void clickCvDemo() {
        Intent intent = new Intent(this, CircleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.canvas_demo_btn)
    public void clickCanvasDemo() {
        Intent intent = new Intent(this, CanvasActivity.class);
        startActivity(intent);
    }
    public float getBatteryLevel() {
        float bLevel;

        Intent batteryIntent = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        Log.i("liauau", "level is " + level + ", scale is " + scale);
        if (level == -1 || scale == -1) {
            bLevel = 50.0f;
        } else {
            bLevel = (float) level / (float) scale * 100.0f ;
        }

        return bLevel;
    }

    @TargetApi(21)
    public void wifi() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        boolean is5G = wifiManager.is5GHzBandSupported();
        Log.i("liauau", "wifi 5G is " + is5G);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ContentProviderOperation.Builder cb = ContentProviderOperation.newInsert(Uri.EMPTY);
        ContentProviderOperation co = cb.withExpectedCount(0).build();
        if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            Log.i("liauau", "onTrimMemory level is " + level);
        }
    }
}
