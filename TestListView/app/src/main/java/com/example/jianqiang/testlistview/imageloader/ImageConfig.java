package com.example.jianqiang.testlistview.imageloader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.jianqiang.testlistview.imageloader.fresco.BitmapListener;

/**
 * Created by struggle_liping on 2017/3/25.
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholderResId;
    protected Drawable placeholder;
    protected int errorPic;
    protected int width;
    protected int height;
    protected String mFilePath;
    protected String mContentProvider;
    protected BitmapListener mBitmapListener;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public Drawable getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public String getContentProvider() {
        return mContentProvider;
    }

    public BitmapListener getBitmapListener() {
        return mBitmapListener;
    }

    public void setBitmapListener(BitmapListener bitmapListener) {
        mBitmapListener = bitmapListener;
    }
}
