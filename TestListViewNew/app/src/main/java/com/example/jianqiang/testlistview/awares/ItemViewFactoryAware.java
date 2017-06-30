package com.example.jianqiang.testlistview.awares;

import android.content.Context;
import android.view.View;

public interface ItemViewFactoryAware<T>
{
    View obtainItemView(Context context, T item, String tag);

    /**
    *   Usually do cache cleaning here, call this method when adapter's data set has been changed
    */
    void reset();
}
