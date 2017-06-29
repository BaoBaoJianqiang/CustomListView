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
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();

            convertView = context.getLayoutInflater().inflate(
                    R.layout.item, null);
            holder.imgAvator = (SimpleDraweeView) convertView
                    .findViewById(R.id.imgAvator);
            holder.tvAuthor = (TextView) convertView
                    .findViewById(R.id.tvAuthor);
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);

            holder.imgZan = (ImageView) convertView
                    .findViewById(R.id.imgZan);
            holder.tvPreferUserList = (TextView) convertView
                    .findViewById(R.id.tvPreferUserList);

            holder.tvTime = (TextView) convertView
                    .findViewById(R.id.tvTime);

            holder.llComments = (LinearLayout) convertView
                    .findViewById(R.id.llComments);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        News news = newsList.get(position);
        holder.tvAuthor.setText(news.author);
        holder.tvContent.setText(news.content);
        holder.tvTime.setText(news.showtime);

        //点赞
        if(news.preferList != null) {

            StringBuilder sb = new StringBuilder();
            for(String user : news.preferList) {
                sb.append(user);
                sb.append(", ");
            }

            sb.deleteCharAt(sb.length()-1);

            holder.tvPreferUserList.setText(sb.toString());
        } else {
            holder.imgZan.setVisibility(View.GONE);
            holder.tvPreferUserList.setText("");
        }

        //评论
        holder.llComments.removeAllViews();
        if(news.commentList != null) {
            for(Comment comment : news.commentList) {
                TextView tvComment = new TextView(convertView.getContext());
                tvComment.setText(comment.author + ": " + comment.content);

                holder.llComments.addView(tvComment);
            }
        } else {
        }

        return convertView;
    }

    class Holder {
        SimpleDraweeView imgAvator;
        TextView tvAuthor;
        TextView tvContent;
        TextView tvTime;

        TextView tvPreferUserList;
        ImageView imgZan;

        LinearLayout llComments;
    }
}
