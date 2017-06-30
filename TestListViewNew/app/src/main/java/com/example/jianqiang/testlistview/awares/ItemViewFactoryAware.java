package com.example.jianqiang.testlistview.awares;

import android.content.Context;
import android.view.View;

public interface ItemViewFactoryAware<T>
{
    View obtainItemView(Context context, T item, String tag);

    void reset();
}
