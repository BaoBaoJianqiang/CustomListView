package com.example.jianqiang.testlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class News {
    public String avator;
    public String author;
    public String content;
    public String showtime;

    public String[] preferList;

    public List<Comment> commentList;

    public List<ImageEntity> imageList;
}
