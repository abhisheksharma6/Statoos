package com.vs.Statoos;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by Android-Dev2 on 3/5/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, "ca-app-pub-5946413716271057~6101591659");
    }
}
