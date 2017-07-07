package com.example.jianqiang.testlistview.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.example.jianqiang.testlistview.MyView1;
import com.example.jianqiang.testlistview.entitiy.News;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ItemViewFactoryAware;
import com.example.jianqiang.testlistview.awares.OnItemViewClickedListener;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig.ConfigBuilder;


public class DefaultItemViewFactory implements ItemViewFactoryAware<News> {
    private ArrayMap<String, ItemViewAware<News>> mCachedViewList;

    public DefaultItemViewFactory() {
        mCachedViewList = new ArrayMap<>();
    }

    @Override
    public View obtainItemView(Context context, News news, @NonNull String tag, @Nullable OnItemViewClickedListener<News> listener) {
        if (news == null || context == null) return null;

        ItemViewAware<News> itemViewAware = mCachedViewList.get(tag);
        if (itemViewAware instanceof MyView1) {
            return (MyView1) itemViewAware;
        }
        MyView1 convertView = new MyView1(context);
        convertView.setData(news, createLayoutConfig(context));
        convertView.setOnItemViewClickedListener(listener);
        mCachedViewList.put(tag, convertView);

        return convertView;
    }

    @Override
    public void reset() {
        mCachedViewList.clear();
    }

    private ItemViewLayoutConfig createLayoutConfig(Context context) {
        return (new ConfigBuilder(context))
                .build();
    }
}
