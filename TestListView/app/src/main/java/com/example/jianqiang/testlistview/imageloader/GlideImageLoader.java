package com.example.jianqiang.testlistview.imageloader;


import com.example.jianqiang.testlistview.imageloader.glide.GlideImageLoaderStrategy;

/**
 * Created by struggle_liping on 2017/3/25.
 */
public class GlideImageLoader implements IFactory {

    @Override
    public BaseImageLoaderStrategy getImageloader() {
        return new GlideImageLoaderStrategy();
    }
}
