package com.example.testmapbox;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

public class AppInstant extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
    }
}
