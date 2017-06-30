package com.example.jianqiang.testlistview.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.example.jianqiang.testlistview.MyView1;
import com.example.jianqiang.testlistview.News;
import com.example.jianqiang.testlistview.R;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ItemViewFactoryAware;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig.ConfigBuilder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class DefaultItemViewFactory implements ItemViewFactoryAware<News>
{
    private ArrayMap<String, ItemViewAware<News>> mCachedViewList;

    public DefaultItemViewFactory()
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

        convertView.setData(news, createLayoutConfig(context, height, MATCH_PARENT));

        mCachedViewList.put(tag, convertView);

        return convertView;
    }

    @Override
    public void reset()
    {
        mCachedViewList.clear();
    }

    private ItemViewLayoutConfig createLayoutConfig(Context context, int height, int width)
    {
        return (new ConfigBuilder(context, height, width))
                .primaryTextColor(context.getResources().getColor(R.color.colorPrimary))
                .build();
    }
}
