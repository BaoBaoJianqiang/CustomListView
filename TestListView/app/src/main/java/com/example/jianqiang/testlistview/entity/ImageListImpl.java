package com.example.jianqiang.testlistview.entity;

import com.example.jianqiang.testlistview.ImageList;

/**
 * Created by struggle_liping on 2017/7/16.
 */

public class ImageListImpl implements ImageList {

    public String smallImageUrl;
    public String largeImageUrl;

    public ImageListImpl(String smallImageUrl,String largeImageUrl) {
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public String getUrl() {
        return smallImageUrl;
    }
}
