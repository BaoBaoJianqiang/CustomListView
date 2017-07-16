package com.example.jianqiang.testlistview.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.jianqiang.testlistview.imageloader.BaseImageLoaderStrategy;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by struggle_liping on 2017/3/25.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    public GlideImageLoaderStrategy() {
    }

    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");
        //这里不抛异常空的就显示默认图片
//        if (TextUtils.isEmpty(config.getUrl())) throw new IllegalStateException("url is required");
        if (config.getImageView() == null) throw new IllegalStateException("imageview is required");


        RequestManager manager;

        manager = Glide.with(ctx);//如果context是activity则自动使用Activity的生命周期

        DrawableRequestBuilder<String> requestBuilder = manager.load(config.getUrl())
                .centerCrop();

        if (config.fitCenter()) {
            requestBuilder.fitCenter();
        }

        if (config.dontAnimation()) {
            requestBuilder.dontAnimate();
        } else {
            requestBuilder.crossFade();
        }

        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case 3:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
        }

        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            requestBuilder.transform(config.getTransformation());
        }

        //设置占位符
        if (config.getPlaceholder() != null) {
            requestBuilder.placeholder(config.getPlaceholder());
        } else if (config.getPlaceholderResId() != 0) {
            requestBuilder.placeholder(config.getPlaceholderResId());
        }

        if (config.getErrorPic() != 0)//设置错误的图片
            requestBuilder.error(config.getErrorPic());

        requestBuilder.skipMemoryCache(config.skipMemoryCache());

        requestBuilder.into(config.getImageView());
    }

    @Override
    public void clear(final Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.clear(imageView);
            }
        }

        if (config.getTargets() != null && config.getTargets().length > 0) {//取消在执行的任务并且释放资源
            for (Target target : config.getTargets())
                Glide.clear(target);
        }


        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {//清除内存缓存
            Glide.get(ctx).clearMemory();
        }


    }
}
