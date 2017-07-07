package com.example.jianqiang.testlistview.helpers;

import android.graphics.Rect;

public class ViewCoordinateHelper
{
    private static final int padding = 20;
    private Rect zanButtonRect;
    private Rect articleRect;
    private Rect contentRect;
    private int[] lastDownActionXY;

    private boolean isRectExist(Rect rect, int left, int top, int right, int bottom)
    {
        return rect != null && rect.left == left && rect.top == top
                && rect.right == right && rect.bottom == bottom;
    }

    public void recordDownAction(int x, int y)
    {
        if(lastDownActionXY == null) lastDownActionXY = new int[2];

        lastDownActionXY[0] = x;

        lastDownActionXY[1] = y;
    }

    public int[] getLastDownActionXY()
    {
        return lastDownActionXY;
    }

    public void setZanButtonRect(int left, int top, int width, int height)
    {
        if(!isRectExist(zanButtonRect, left - padding, top - padding, left + width + padding, top + height + padding))
        {
            zanButtonRect = new Rect(left - padding, top - padding, left + width + padding, top + height + padding);
        }
    }

    public boolean isInZanArea(int x, int y)
    {
        if(zanButtonRect == null) return false;

        return x >= zanButtonRect.left && x <= zanButtonRect.right
                && y >= zanButtonRect.top && y <= zanButtonRect.bottom;
    }

    public void setArticleRect(int left, int top, int right, int bottom)
    {
        if(!isRectExist(articleRect, left, top, right, bottom))
        {
            articleRect = new Rect(left, top, right, bottom);
        }
    }

    public boolean isInArticleArea(int x, int y)
    {
        if(articleRect == null) return false;

        return x >= articleRect.left && x <= articleRect.right
                && y >= articleRect.top && y <= articleRect.bottom;
    }

    public void setContentRect(int left, int top, int right, int bottom)
    {
        if(!isRectExist(contentRect, left, top, right, bottom))
        {
            contentRect = new Rect(left, top, right, bottom);
        }
    }

    public boolean isInContentArea(int x, int y)
    {
        if(contentRect == null) return false;

        return x >= contentRect.left && x <= contentRect.right
                && y >= contentRect.top && y <= contentRect.bottom;
    }
}
