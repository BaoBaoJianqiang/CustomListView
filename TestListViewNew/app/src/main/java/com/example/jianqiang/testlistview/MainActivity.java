package com.example.jianqiang.testlistview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.jianqiang.testlistview.entitiy.News;
import com.example.jianqiang.testlistview.pulltorefresh.PullToRefreshBase;
import com.example.jianqiang.testlistview.pulltorefresh.PullToRefreshListView;
import com.example.jianqiang.testlistview.utils.Utils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity
{
    MyListView listView;
    MyAdapter adapter;
    private PullToRefreshListView mPullRefreshListView;
    private int hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                hours+=10;
                adapter.updateDataset(Utils.generateLoadData(hours),true);
                mPullRefreshListView.onRefreshComplete();
            }
        };

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<MyListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<MyListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<MyListView> refreshView) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                },2000);

            }
        });
        mPullRefreshListView.getListView().setRxAppCompatActivity(this);
        adapter = new MyAdapter(this, Utils.generateLoadData(10),null,mPullRefreshListView.getListView());

        adapter.setOnListItemClickedListener(new MyAdapter.OnListItemClickedListener()
        {
            @Override
            public void onListItemClicked(int position, News news)
            {
                Toast.makeText(MainActivity.this, "#" + position + " item with title: " + news.content + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mPullRefreshListView.setAdapter(adapter);
    }
}
