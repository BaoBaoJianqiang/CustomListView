package com.example.jianqiang.testlistview.imageloader;

import com.example.jianqiang.testlistview.imageloader.fresco.FrescoImageLoaderStrategy;

/**
 * Created by struggle_liping on 2017/7/16.
 */

public class FrescoImageLoader implements IFactory {
    @Override
    public BaseImageLoaderStrategy getImageloader() {
        return new FrescoImageLoaderStrategy();
    }
}
