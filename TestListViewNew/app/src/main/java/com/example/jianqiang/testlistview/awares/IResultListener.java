package com.example.jianqiang.testlistview.awares;

import android.graphics.Bitmap;

/**
 * Created by struggle_liping on 2017/7/1.
 */

public interface IResultListener {

    void onSuccess(Bitmap bitmap);
    void onFail();
}
