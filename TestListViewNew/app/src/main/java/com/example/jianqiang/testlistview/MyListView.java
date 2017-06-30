package com.example.jianqiang.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;


public class MyListView extends ListView
{
    public MyListView(Context context)
    {
        super(context);

        init();
    }

    public MyListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init()
    {
        this.setOnScrollListener(new OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if(!(getAdapter() instanceof ListAdapterAware)) return;

                ((ListAdapterAware) getAdapter()).setScrolling(scrollState != SCROLL_STATE_IDLE);

                if(scrollState == SCROLL_STATE_IDLE)
                {
                    for(int i = 0; i < view.getChildCount(); i++)
                    {
                        View itemView = view.getChildAt(i);

                        if(itemView instanceof ItemViewAware)
                        {
                            ((ItemViewAware) itemView).triggerNetworkJob();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();

        if(getAdapter() instanceof ListAdapterAware)
        {
            ((ListAdapterAware) getAdapter()).destroy();
        }
    }
}
