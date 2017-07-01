package com.example.jianqiang.testlistview.awares;

public interface ListAdapterAware
{
    void setScrolling(boolean isScrolling);

    void destroy();

    void updateItemView(int position);
}
