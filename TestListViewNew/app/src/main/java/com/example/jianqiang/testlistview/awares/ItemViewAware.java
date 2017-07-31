package com.example.jianqiang.testlistview.awares;

import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public interface ItemViewAware<T>
{
    void setData(T item, ItemViewLayoutConfig layoutConfig);

    boolean triggerNetworkJob(ListAdapterAware adapter, int position, RxAppCompatActivity context);

    void setOnItemViewClickedListener(OnItemViewClickedListener<T> listener);
}
