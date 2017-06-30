package com.example.jianqiang.testlistview.helpers;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.example.jianqiang.testlistview.R;

import java.io.Serializable;

public class ItemViewLayoutConfig implements Serializable
{
    private ConfigBuilder builder;
    private Context context;

    public ItemViewLayoutConfig(ConfigBuilder builder)
    {
        this.builder = builder;

        this.context = builder.context;
    }

    public int getHeight()
    {
        return builder.height;
    }

    public int getWidth()
    {
        return builder.width;
    }

    public float getPrimaryTextSize()
    {
        return builder.primaryTextSize > 0 ? builder.primaryTextSize : context.getResources().getDimension(R.dimen.common_text_size_medium);
    }

    public float getSecondaryTextSize()
    {
        return builder.secondaryTextSize > 0 ? builder.secondaryTextSize : context.getResources().getDimension(R.dimen.common_text_size_small);
    }

    @ColorInt
    public int getPrimaryTextColor()
    {
        return builder.primaryTextColor == 0 ? context.getResources().getColor(R.color.common_text_color_primary) : builder.primaryTextColor;
    }

    @ColorInt
    public int getSecondaryTextColor()
    {
        return builder.secondaryTextColor == 0 ? context.getResources().getColor(R.color.common_text_color_secondary) : builder.secondaryTextColor;
    }

    public static class ConfigBuilder
    {
        Context context;
        int height;
        int width;
        float primaryTextSize;
        float secondaryTextSize;
        @ColorInt int primaryTextColor;
        @ColorInt int secondaryTextColor;

        public ConfigBuilder(@NonNull Context context, int height, int width)
        {
            this.context = context;

            this.height = height;

            this.width = width;
        }

        public ConfigBuilder primaryTextSize(float primaryTextSize)
        {
            this.primaryTextSize = primaryTextSize;

            return this;
        }

        public ConfigBuilder secondaryTextSize(float secondaryTextSize)
        {
            this.secondaryTextSize = secondaryTextSize;

            return this;
        }

        public ConfigBuilder primaryTextColor(@ColorInt int primaryTextColor)
        {
            this.primaryTextColor = primaryTextColor;

            return this;
        }

        public ConfigBuilder secondaryTextColor(@ColorInt int secondaryTextColor)
        {
            this.secondaryTextColor = secondaryTextColor;

            return this;
        }

        public ItemViewLayoutConfig build()
        {
            return new ItemViewLayoutConfig(this);
        }
    }
}
