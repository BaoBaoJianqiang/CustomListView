package com.example.jianqiang.testlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private final List<News> newsList;
    private final Activity context;

    public MyAdapter(Activity context, List<News> newsList) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        MyView1 myView = new MyView1(parent.getContext());

        //提前计算行高
        News news = newsList.get(position);
        int height = 300;
        if(news.preferList != null)
            height += 40;
        if(news.commentList != null) {
            height += news.commentList.size() * 40;
        }

        myView.setData(news, 1200, height);
        convertView = myView;

        return convertView;
    }
}
