package com.example.jianqiang.testlistview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ItemViewFactoryAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.helpers.DefaultItemViewFactory;

import java.util.List;

public class MyAdapter extends BaseAdapter implements ListAdapterAware {
    private List<News> newsList;
    private Activity context;
    private ItemViewFactoryAware<News> itemViewFactory;
    private boolean isScrolling;
    private ListView mMyListView;

    public MyAdapter(Activity context, List<News> newsList) {
        this(context, newsList, null, null);
    }

    public MyAdapter(Activity context, List<News> newsList, ItemViewFactoryAware<News> itemViewFactory, ListView listView) {
        super();

        this.newsList = newsList;
        this.context = context;
        this.itemViewFactory = itemViewFactory == null ? new DefaultItemViewFactory() : itemViewFactory;
        this.mMyListView = listView;
    }

    public void updateDataset(@NonNull List<News> newsList, boolean isAppend) {
        if (this.newsList == null) {
            itemViewFactory.reset();

            this.newsList = newsList;

            notifyDataSetChanged();

            return;
        }

        if (isAppend) {
            this.newsList.addAll(newsList);
        } else {
            itemViewFactory.reset();

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
        convertView = itemViewFactory.obtainItemView(parent.getContext(), newsList.get(position), String.valueOf(position));

        if ((convertView instanceof ItemViewAware) && !isScrolling) {
            ((ItemViewAware) convertView).triggerNetworkJob(this, position);
        }


        return convertView;
    }


    @Override
    public void setScrolling(boolean isScrolling) {
        this.isScrolling = isScrolling;
    }

    @Override
    public void destroy() {
        itemViewFactory.reset();
    }

    @Override
    public void updateItemView(int position) {
        if (mMyListView == null) {
            new IllegalStateException("listview can't be empty");
            return;
        }
        int firstVisiblePosition = mMyListView.getFirstVisiblePosition();

        int lastVisiblePosition = mMyListView.getLastVisiblePosition();

        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {

            View view = mMyListView.getChildAt(position - firstVisiblePosition);

            mMyListView.getAdapter().getView(position, view, mMyListView);
        }
        if (position == lastVisiblePosition) {
            return;

        }


    }

}
