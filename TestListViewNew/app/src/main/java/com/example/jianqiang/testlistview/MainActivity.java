package com.example.jianqiang.testlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jianqiang.testlistview.MyAdapter.OnListItemClickedListener;

public class MainActivity extends AppCompatActivity
{
    MyListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (MyListView) findViewById(R.id.mylist);

        adapter = new MyAdapter(this, Utils.generateData(),null,listView);

        adapter.setOnListItemClickedListener(new OnListItemClickedListener()
        {
            @Override
            public void onListItemClicked(int position, News news)
            {
                Toast.makeText(MainActivity.this, "#" + position + " item with title: " + news.content + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(adapter);
    }
}
