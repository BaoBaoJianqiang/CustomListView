package com.example.jianqiang.testlistview.imageloader.glide;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.example.jianqiang.testlistview.imageloader.ImageConfig;

/**
 * Created by struggle_liping on 2017/3/25.
 * 这里放Glide专属的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除或则切换缓存策略,则可以定义一个int类型的变量,内部根据int做不同过的操作
 * 其他操作同理
 */
public class GlideImageConfig extends ImageConfig {
    private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private BitmapTransformation[] transformation;//glide用它来改变图形的形状
    private Target[] targets;
    private ImageView[] imageViews;
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存
    private boolean skipMemoryCache;//本次加载是否忽略内存中的缓存
    private boolean dontAnimation;//是否取消动画
    private boolean isFitCenter;

    private GlideImageConfig(Buidler builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholderResId = builder.placeholderResId;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.targets = builder.targets;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.dontAnimation = builder.dontAnimation;
        this.isFitCenter = builder.isFitCenter;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public BitmapTransformation[] getTransformation() {
        return transformation;
    }

    public Target[] getTargets() {
        return targets;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public boolean skipMemoryCache() {
        return skipMemoryCache;
    }

    public boolean dontAnimation() {
        return dontAnimation;
    }

    public boolean fitCenter() {
        return isFitCenter;
    }

    public static Buidler builder() {
        return new Buidler();
    }


    public static final class Buidler {
        private String url;
        private ImageView imageView;
        private Drawable placeholder;
        private int placeholderResId;
        private int errorPic;
        private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        private BitmapTransformation[] transformation;//glide用它来改变图形的形状
        private Target[] targets;
        private ImageView[] imageViews;
        private boolean isClearMemory;//清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存
        private boolean skipMemoryCache;//本次加载是否忽略内存中的缓存
        private boolean dontAnimation;//是否取消动画
        private boolean isFitCenter;

        private Buidler() {
        }

        public Buidler url(String url) {
            this.url = url;
            return this;
        }

        public Buidler placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Buidler placeholder(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Buidler errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Buidler imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Buidler cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Buidler transformation(BitmapTransformation... transformation) {
            this.transformation = transformation;
            return this;
        }

        public Buidler targets(Target... targets) {
            this.targets = targets;
            return this;
        }

        public Buidler imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Buidler isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Buidler isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Buidler skipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Buidler dontAnimation(boolean dontAnimation) {
            this.dontAnimation = dontAnimation;
            return this;
        }

        public Buidler fitCenter() {
            this.isFitCenter = true;
            return this;
        }

        public GlideImageConfig build() {
            return new GlideImageConfig(this);
        }
    }
}
