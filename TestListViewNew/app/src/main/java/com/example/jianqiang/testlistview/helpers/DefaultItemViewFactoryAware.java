package com.example.jianqiang.testlistview.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.example.jianqiang.testlistview.MyView1;
import com.example.jianqiang.testlistview.News;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ItemViewFactoryAware;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class DefaultItemViewFactoryAware implements ItemViewFactoryAware<News>
{
    private ArrayMap<String, ItemViewAware<News>> mCachedViewList;

    public DefaultItemViewFactoryAware()
    {
        mCachedViewList = new ArrayMap<>();
    }

    @Override
    public View obtainItemView(Context context, News news, @NonNull String tag)
    {
        if(news == null || context == null) return null;

        ItemViewAware<News> itemViewAware = mCachedViewList.get(tag);

        if(itemViewAware instanceof MyView1)
        {
            return (MyView1) itemViewAware;
        }

        MyView1 convertView = new MyView1(context);

        int height = 300;
        if(news.preferList != null)
            height += 40;
        if(news.commentList != null) {
            height += news.commentList.size() * 40;
        }

        convertView.setData(news, MATCH_PARENT, height);

        mCachedViewList.put(tag, convertView);

        return convertView;
    }

    @Override
    public void reset()
    {
        mCachedViewList.clear();
    }
}
