package com.example.jianqiang.testlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private final List<News> newsList;
    private final Context context;

    public MyAdapter(Context context, List<News> newsList) {
        super();
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //复用ConvertView
        if (convertView == null) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_custom_view, null);
            convertView = linearLayout.findViewById(R.id.myView);
        }
        News news = newsList.get(position);
        ((MyView1) convertView).setData(news);
        return convertView;
    }


}
