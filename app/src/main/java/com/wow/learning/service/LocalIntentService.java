package com.wow.learning.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ljinjin on 12/7/16.
 */

public class LocalIntentService extends IntentService {
    public LocalIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
