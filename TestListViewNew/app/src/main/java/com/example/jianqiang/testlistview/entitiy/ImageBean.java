package com.example.jianqiang.testlistview.entitiy;

import android.graphics.Bitmap;

public  class ImageBean {
    private Object tag;
    private Bitmap bitmap;

    public ImageBean(Object tag, Bitmap bitmap) {
        this.tag = tag;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "tag=" + tag +
                ", bitmap=" + bitmap +
                '}';
    }
}