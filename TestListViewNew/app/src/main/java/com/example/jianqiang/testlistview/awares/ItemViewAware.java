package com.example.jianqiang.testlistview.awares;

public interface ItemViewAware<T>
{
    void setData(T item, int width, int height);

    void triggerNetworkJob();
}
