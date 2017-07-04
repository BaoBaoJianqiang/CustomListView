package com.example.jianqiang.testlistview.awares;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

public interface ItemViewFactoryAware<T>
{
    View obtainItemView(Context context, T item, String tag, @Nullable OnItemViewClickedListener<T> listener);

    /**
    *   Usually do cache cleaning here, call this method when adapter's data set has been changed
    */
    void reset();
}
