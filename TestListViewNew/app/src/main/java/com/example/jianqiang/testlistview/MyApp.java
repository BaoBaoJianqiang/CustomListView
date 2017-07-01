package com.example.jianqiang.testlistview;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //init
        Fresco.initialize(this);
    }
}
