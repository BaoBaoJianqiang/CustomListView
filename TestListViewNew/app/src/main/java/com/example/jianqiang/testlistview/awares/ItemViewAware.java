package com.example.jianqiang.testlistview.awares;

import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;

public interface ItemViewAware<T>
{
    void setData(T item, ItemViewLayoutConfig layoutConfig);

    void triggerNetworkJob();
}
