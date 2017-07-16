package com.example.jianqiang.testlistview.imageloader;

import android.content.Context;

/**
 * Created by struggle_liping on 2017/3/25.
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context ctx, T config);
    void clear(Context ctx, T config);
}
