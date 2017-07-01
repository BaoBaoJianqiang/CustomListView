package com.example.jianqiang.testlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;

public class MyView1 extends View implements ItemViewAware<News>
{
    int width;
    int height;

    Paint paint;

    int titleSize = 40;
    int dispSize = 40;

    News news;

    Bitmap zanImg;
    Bitmap imgDefault;

    public MyView1(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        zanImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.zan);
        imgDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    @Override
    public void setData(News news, ItemViewLayoutConfig layoutConfig) {

        if(layoutConfig == null) return;

        this.news = news;

        this.width = layoutConfig.getWidth();
        this.height = layoutConfig.getHeight();

        //TODO read other custom values
    }

    @Override
    public boolean triggerNetworkJob(ListAdapterAware adapter, int position)
    {
        //TODO do network job here
        //TODO After success of the network call
        adapter.updateItemView(position);
        System.out.println("lip triggerNetworkJob updateView"+position);
        return true;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(resizeBitmap(imgDefault, 0.5f), 10, 10, paint);

        int topPos = 30;

        //人名
        paint.setTextSize(titleSize);
        canvas.drawText(news.author, imgDefault.getWidth() + 10, topPos + 10, paint);

        //内容
        paint.setTextSize(dispSize);
        canvas.drawText(news.content, imgDefault.getWidth() + 10, topPos + titleSize + 20 + 10, paint);

        //发布时间
        paint.setTextSize(dispSize);
        canvas.drawText(news.showtime, imgDefault.getWidth() + 10, topPos + titleSize + 20 + 10 + dispSize + 10, paint);

        //点赞
        int hasZan = 1;
        if (news.preferList != null) {
            //点赞图标
            canvas.drawBitmap(resizeBitmap(zanImg, 0.25f), imgDefault.getWidth() + 10, topPos + titleSize + dispSize + 10 + dispSize + 10, paint);

            StringBuilder sb = new StringBuilder();
            for (String user : news.preferList) {
                sb.append(user);
                sb.append(", ");
            }

            sb.deleteCharAt(sb.length() - 1);

            paint.setTextSize(dispSize);
            canvas.drawText(sb.toString(), imgDefault.getWidth() + 10 + 60, topPos + titleSize + dispSize + 10 + dispSize + 40, paint);

        } else {
            hasZan = 0;
        }


        //评论
        int base = topPos + titleSize + dispSize + 10 + dispSize + 10 + 60 + 40 * hasZan;
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                paint.setTextSize(dispSize);
                canvas.drawText(
                        news.commentList.get(i).author + ": " + news.commentList.get(i).content,
                        imgDefault.getWidth() + 10,
                        base + 50 * i,
                        paint);
            }
        }

        //图片加载

    }

    Bitmap resizeBitmap(Bitmap bitmap, float scale) {
        // Matrix类进行图片处理（缩小或者旋转）
        Matrix matrix = new Matrix();
        // 缩小一倍
        matrix.postScale(scale, scale);
        // 生成新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

    }
}