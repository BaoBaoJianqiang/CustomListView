package com.example.jianqiang.testlistview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ItemViewFactoryAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.helpers.DefaultItemViewFactory;

import java.util.List;

public class MyAdapter extends BaseAdapter implements ListAdapterAware{
    private List<News> newsList;
    private final Activity context;
    private ItemViewFactoryAware<News> itemViewFactory;
    private boolean isScrolling;

    public MyAdapter(Activity context, List<News> newsList)
    {
        this(context, newsList, null);
    }

    public MyAdapter(Activity context, List<News> newsList, ItemViewFactoryAware<News> itemViewFactory) {
        super();

        this.newsList = newsList;
        this.context = context;
        this.itemViewFactory = itemViewFactory == null ? new DefaultItemViewFactory() : itemViewFactory;
    }

    public void updateDataset(@NonNull List<News> newsList, boolean isAppend)
    {
        itemViewFactory.reset();

        if(this.newsList == null)
        {
            this.newsList = newsList;

            notifyDataSetChanged();

            return;
        }

        if(isAppend)
        {
            this.newsList.addAll(newsList);
        }
        else
        {
            this.newsList = newsList;
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        View itemView = itemViewFactory.obtainItemView(parent.getContext(), newsList.get(position), String.valueOf(position));

        if((itemView instanceof ItemViewAware) && !isScrolling)
        {
            ((ItemViewAware) itemView).triggerNetworkJob();
        }

        return itemView;
    }

    @Override
    public void setScrolling(boolean isScrolling)
    {
        this.isScrolling = isScrolling;
    }
}
