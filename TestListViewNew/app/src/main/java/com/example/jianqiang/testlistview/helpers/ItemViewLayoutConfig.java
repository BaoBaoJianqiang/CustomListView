package com.example.jianqiang.testlistview.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.example.jianqiang.testlistview.R;

import java.io.Serializable;

public class ItemViewLayoutConfig implements Serializable {


    private ConfigBuilder builder;
    private Context mContext;

    public ConfigBuilder getBuilder() {
        return builder;
    }

    public ItemViewLayoutConfig(ConfigBuilder builder) {
        this.builder = builder;
        this.mContext = builder.context;
    }


    public static class ConfigBuilder {
        Context context;
        int nameColor;//文字颜色
        int timeColor;//状态发布时间颜色
        int contentColor;//内容颜色
        private int rightMargin;//item右边距
        private float nameSize;//名字的颜色大小
        private float contentSize;//发表内容文字大小
        private int lineMargin;//行间距
        private int imageSpace;//图片间距


        private int imageSize;//分享的图片尺寸
        private int contentWidth;//item减去图片以及右间距
        private int articleHeight;//分享文章的高度
        private int articleFontSize;//分享文章的文字的大小


        private void initViewSize() {
            nameColor = ContextCompat.getColor(context, R.color.blue);
            timeColor = ContextCompat.getColor(context, R.color.gray);
            contentColor = ContextCompat.getColor(context, R.color.black_text);
            rightMargin = (int) context.getResources().getDimension(R.dimen.dp_10);
            imageSize = (int) context.getResources().getDimension(R.dimen.dp_50);
            nameSize = context.getResources().getDimension(R.dimen.sp_10);
            contentSize = context.getResources().getDimension(R.dimen.sp_11);
            lineMargin = (int) context.getResources().getDimension(R.dimen.dp_3);
            imageSpace = (int) context.getResources().getDimension(R.dimen.dp_3);
            contentWidth = (int) context.getResources().getDimension(R.dimen.dp_167);
            articleHeight = (int) context.getResources().getDimension(R.dimen.dp_20);
            articleFontSize = (int) context.getResources().getDimension(R.dimen.sp_11);
        }

        public ConfigBuilder(@NonNull Context context) {
            this.context = context;
            initViewSize();
        }

        public ItemViewLayoutConfig build() {
            return new ItemViewLayoutConfig(this);
        }

        public int getImageSize() {
            return imageSize;
        }

        public int getrightMargin() {
            return rightMargin;
        }

        public float getNameSize() {
            return nameSize;
        }

        public float getContentSize() {
            return contentSize;
        }

        public int getLineMargin() {
            return lineMargin;
        }

        public int getImageSpace() {
            return imageSpace;
        }

        public int getContentWidth() {
            return contentWidth;
        }

        public int getArticleHeight() {
            return articleHeight;
        }

        public int getArticleFontSize() {
            return articleFontSize;
        }

        public int getnameColor() {
            return nameColor;
        }

        public int gettimeColor() {
            return timeColor;
        }

        public int getContentColor() {
            return contentColor;
        }

        public void setContentColor(int contentColor) {
            this.contentColor = contentColor;
        }

    }
}
