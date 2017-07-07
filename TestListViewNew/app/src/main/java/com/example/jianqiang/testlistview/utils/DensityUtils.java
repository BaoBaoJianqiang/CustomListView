package com.example.jianqiang.testlistview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.util.TypedValue;

public class DensityUtils {

    public static int dp2px(Context ctx, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());

    }

    public static int px2dp(Context ctx, int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    public static int getDimens(Context context, @DimenRes int resource) {
        Resources mResources = context.getResources();
        return mResources.getDimensionPixelSize(resource);
    }
}
