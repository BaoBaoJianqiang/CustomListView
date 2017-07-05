package com.example.jianqiang.testlistview.helpers;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ZanContentHelper
{
    private List<String> preferList;
    //记录上次绘制时"赞"区域的高度
    public int lastZanContentHeight = 0;
    //"赞"区域的高度相比上次绘制有没有发生变化
    public boolean isZanContentHeightChanged = false;
    //"赞"区域高度的差值
    public int zanContentHeightDiff = 0;
    //记录上次绘制时"赞"区域的左边沿X坐标
    public int lastLeft = 0;
    //记录上次绘制时"赞"区域的上边沿Y坐标
    public int lastTop = 0;
    //是否已对该cell点赞
    public boolean isZan;

    private String composedPreferString;

    public static ArrayList<String> initPreferListFromArray(@NonNull String[] array)
    {
        ArrayList<String> preferList = new ArrayList<>();

        for(String s : array)
        {
            preferList.add(s);
        }

        return preferList;
    }

    public ZanContentHelper(List<String> preferList)
    {
        this.preferList = preferList;
    }

    public String getComposedPreferString()
    {
        if(!TextUtils.isEmpty(composedPreferString)) return composedPreferString;

        return innerComposePreferString();
    }

    private String innerComposePreferString()
    {
        if (preferList != null && !preferList.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            for (String user : preferList) {
                sb.append(user);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 2);

            return sb.toString();
        }

        return null;
    }

    public void addZan(String name)
    {
        if(TextUtils.isEmpty(name)) return;

        isZan = true;

        if(preferList == null) preferList = new ArrayList<>();

        if(preferList.contains(name)) return;

        preferList.add(name);

        composedPreferString = innerComposePreferString();
    }

    public void removeZan(String name)
    {
        if(TextUtils.isEmpty(name) || preferList == null || preferList.isEmpty()) return;

        isZan = false;

        if(preferList.remove(name))
        {
            composedPreferString = innerComposePreferString();
        }
    }
}
