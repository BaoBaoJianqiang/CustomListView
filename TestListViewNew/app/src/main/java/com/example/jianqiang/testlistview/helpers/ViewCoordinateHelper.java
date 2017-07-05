package com.example.jianqiang.testlistview.helpers;

import android.graphics.Rect;

public class ViewCoordinateHelper
{
    private Rect zanButtonRect;

    public void setZanButtonRect(int left, int top, int width, int height)
    {
        zanButtonRect = new Rect(left - 20, top - 20, left + width + 20, top + height + 20);
    }

    public boolean isZanClicked(int x, int y)
    {
        if(zanButtonRect == null) return false;

        return x >= zanButtonRect.left && x <= zanButtonRect.right
                && y >= zanButtonRect.top && y <= zanButtonRect.bottom;
    }
}
