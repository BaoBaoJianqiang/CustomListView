package com.example.jianqiang.testlistview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.jianqiang.testlistview.entitiy.News;
import com.example.jianqiang.testlistview.pulltorefresh.PullToRefreshBase;
import com.example.jianqiang.testlistview.pulltorefresh.PullToRefreshListView;
import com.example.jianqiang.testlistview.utils.Utils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

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

//        downloadIamgde("fdf")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .compose(this.<String>bindToLifecycle())
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d("MAIN","lip run----->");
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.d("MAIN","lip accept----->"+s);
//                    }
//                });
    }



    public Flowable<String> downloadIamgde(final String path) {
        return  Flowable.create(new FlowableOnSubscribe<String>() {


            @Override
            public void subscribe(@NonNull final FlowableEmitter<String> subscriber) throws Exception {
                for (int i=0;i<139;i++) {
                    subscriber.onNext("JKJFLDJFKDJ" + path+i);
                }
                subscriber.onComplete();
            }


        }, BackpressureStrategy.BUFFER);
    }

}
