package com.example.jianqiang.testlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    MyListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (MyListView) findViewById(R.id.mylist);

        adapter = new MyAdapter(this, Utils.generateData());

        listView.setAdapter(adapter);
    }
}
