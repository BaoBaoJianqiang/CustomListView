package com.example.jianqiang.testlistview;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {
    private static Context sApp;

    public static Context getApp() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp =this;
        Fresco.initialize(this);
    }
}
