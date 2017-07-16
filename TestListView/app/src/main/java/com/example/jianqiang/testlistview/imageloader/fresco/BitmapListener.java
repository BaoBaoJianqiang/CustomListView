package com.example.jianqiang.testlistview.imageloader.fresco;

import android.graphics.Bitmap;

/**
 * Created by struggle_liping on 2017/7/16.
 */
public interface BitmapListener {
    void onSuccess(Bitmap bitmap);

    void onFail();
}