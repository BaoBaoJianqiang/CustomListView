package com.example.jianqiang.testlistview.imageloader;

import android.content.Context;

/**
 * Created by struggle_liping on 2017/3/25.
 */
public  class ImageLoader {

    private static volatile ImageLoader instance;

    private static final Object[] lock = new Object[0];


    public static ImageLoader getInstance(){
        if (instance==null){
            synchronized (lock){
                if (instance==null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    private BaseImageLoaderStrategy mStrategy;


    public ImageLoader(BaseImageLoaderStrategy strategy) {
        setLoadImgStrategy(strategy);
    }

    public ImageLoader() {
        setLoadImgStrategy(new GlideImageLoader().getImageloader());
    }

    public <T extends ImageConfig> void loadImage(Context context, T config) {
        this.mStrategy.loadImage(context, config);
    }

    public <T extends ImageConfig> void clear(Context context, T config) {
        this.mStrategy.clear(context, config);
    }


    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }

}
