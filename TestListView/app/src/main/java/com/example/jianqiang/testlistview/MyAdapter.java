package com.example.jianqiang.testlistview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

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
            holder.llPrefer = (LinearLayout) convertView.findViewById(R.id.llPrefer);
            holder.llPrefer = (LinearLayout) convertView.findViewById(R.id.llPrefer);
            holder.llNineGrid = (NineImageView) convertView.findViewById(R.id.nineview);
            holder.ivArticle = (ImageView) convertView.findViewById(R.id.imgarticle);
            holder.tvArticle = (TextView) convertView.findViewById(R.id.tvarticle);
            holder.llArticle = (LinearLayout) convertView.findViewById(R.id.llarticle);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        News news = newsList.get(position);
        holder.tvAuthor.setText(news.author);
        holder.tvContent.setText(news.content);
        holder.tvTime.setText(news.showtime);
        holder.imgAvator.setBackgroundResource(R.mipmap.default_image);
        holder.imgAvator.setImageURI(Uri.parse(news.avator));
//        ImageLoader
//                .getInstance()
//                .loadImage(
//                        MyApp.getApp(),
//                        GlideImageConfig
//                                .builder()
//                                .url(news.avator)
//                                .placeholder(R.mipmap.ic_launcher)
//                                .imageView(holder.imgAvator)
//                                .build());
        if (news.imageList!=null) {
            holder.llNineGrid.setVisibility(View.VISIBLE);
            holder.llNineGrid.setList(news.imageList);
        }else {
            holder.llArticle.setVisibility(View.GONE);
        }
        //资讯
        final Article article = news.article;
        Log.d("TAG","lip "+position+"==="+article);
        if (article!=null) {
            holder.llArticle.setVisibility(View.VISIBLE);
            holder.ivArticle.setBackgroundResource(R.drawable.zan);
            holder.ivArticle.setImageURI(Uri.parse(article.imageUrl));
            holder.tvArticle.setText(article.title);
            holder.llArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri url = Uri.parse(article.articleUrl);
                    intent.setData(url);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.llArticle.setVisibility(View.GONE);
        }
        //点赞
        if (news.preferList != null) {
            holder.llPrefer.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            for (String user : news.preferList) {
                sb.append(user);
                sb.append(", ");
            }

            sb.deleteCharAt(sb.length() - 1);
            holder.tvPreferUserList.setText(sb.toString());
        } else {
            holder.llPrefer.setVisibility(View.GONE);
            holder.imgZan.setVisibility(View.GONE);
            holder.tvPreferUserList.setText("");
        }

        //评论
        holder.llComments.removeAllViews();
        if (news.commentList != null) {
            for (Comment comment : news.commentList) {
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
        LinearLayout llPrefer;
        NineImageView llNineGrid;
        ImageView ivArticle;
        TextView tvArticle;
        LinearLayout llArticle;
    }
}
