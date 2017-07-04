package com.example.jianqiang.testlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

import com.example.jianqiang.testlistview.awares.IResultListener;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.awares.OnItemViewClickedListener;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;

import static com.example.jianqiang.testlistview.Utils.smartDrawText;

public class MyView1 extends View implements ItemViewAware<News> {
    Paint paint;
    TextPaint textPaint;
    int margin = 30;
    int nameSize = 30;
    int titleSize = 32;
    int textMargin = 10;
    int imageMargin = 10;
    int contentWidth = 560;

    //图片列表, 每张图片的宽度（高度），图片之间的间距
    int shareImageSize = 100;
    int shareImageGap = 10;
    //图片列表的总高度
    int imageTotalHeight = 0;

    News news;
    Bitmap zanImg;
    Bitmap imgDefault;
    Bitmap avatorImg;
    Context mContext;
    OnItemViewClickedListener<News> mOnItemViewClickedListener;

    public MyView1(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        mContext = context;
        zanImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.zan);
        imgDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    @Override
    public void setData(News news, ItemViewLayoutConfig layoutConfig) {

        if (layoutConfig == null)
            return;
        this.news = news;

        //TODO read other custom values
    }

    @Override
    public boolean triggerNetworkJob(final ListAdapterAware adapter, final int position) {
        //TODO do network job here
        Uri uri = Uri.parse(news.avator);
        FrescoUtils.downloadBitmap(uri, mContext, new IResultListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                avatorImg = bitmap;
                post(new Runnable() {
                    @Override
                    public void run()
                    {
                        invalidate();
                    }
                });
            }

            @Override
            public void onFail() {

            }
        });
        //TODO After success of the network call
//
        return true;
    }

    @Override
    public void setOnItemViewClickedListener(OnItemViewClickedListener<News> listener)
    {
        mOnItemViewClickedListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), getTotalHeight());

    }

    //获取测量宽度，同时设置文字的最大宽度
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);//获取宽度
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                result = specSize;
                contentWidth = specSize - imgDefault.getWidth() / 2 - margin;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }

    //获取总高度
    public int getTotalHeight() {
        int leftMargin = imgDefault.getWidth() / 2;//文字左边距
        Canvas canvas = new Canvas();
        int totalHeight = 0;
        //人名的高度
        textPaint.setTextSize(nameSize);
        totalHeight += smartDrawText(canvas, textPaint, news.author, contentWidth, leftMargin, totalHeight).getHeight();
        //内容的高度
        textPaint.setTextSize(titleSize);
        totalHeight += Utils.smartDrawText(canvas, textPaint, news.content, contentWidth, leftMargin, totalHeight).getHeight();
        //发布时间的高度
        textPaint.setTextSize(nameSize);
        totalHeight += Utils.smartDrawText(canvas, textPaint, news.showtime, contentWidth, leftMargin, totalHeight).getHeight();

        if(news.imageList != null) {
            int length = news.imageList.size();
            switch (length) {
                case 1:
                case 2:
                case 3:
                    imageTotalHeight = shareImageSize + shareImageGap * 2;
                    break;
                case 4:
                case 5:
                case 6:
                    imageTotalHeight = shareImageSize * 2 + shareImageGap * 3;
                    break;
                case 7:
                case 8:
                case 9:
                    imageTotalHeight = shareImageSize * 3 + shareImageGap * 4;
                    break;
                default:
                    break;
            }

            totalHeight += imageTotalHeight;
        }

        //点赞的高度
        if (news.preferList != null) {
            textPaint.setTextSize(nameSize);
            textPaint.setColor(Color.BLUE);
            totalHeight += Utils.smartDrawText(canvas, textPaint, "赞", contentWidth, leftMargin + zanImg.getWidth() + textMargin, totalHeight).getHeight();
        }
        //评论的总高度
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                textPaint.setTextSize(nameSize);
                textPaint.setColor(Color.BLACK);
                totalHeight += Utils.smartDrawText(canvas, textPaint, news.commentList.get(i).author + ": " + news.commentList.get(i).content, contentWidth, leftMargin, totalHeight).getHeight();
            }
        }
        return totalHeight;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int leftMargin = imgDefault.getWidth()+imageMargin;//文字左边距
        int startY = 0;
        if (avatorImg != null) {
            canvas.drawBitmap(avatorImg, imageMargin, imageMargin, paint);
        } else {
            canvas.drawBitmap(resizeBitmap(imgDefault, 2.0f), imageMargin, imageMargin, paint);
        }

        //人名
        textPaint.setTextSize(nameSize);
        textPaint.setColor(Color.BLUE);
        StaticLayout staticLayout1 = smartDrawText(canvas, textPaint, news.author, contentWidth, leftMargin, startY);
        int height = staticLayout1.getHeight();
        startY += height;

        //内容
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(titleSize);
        StaticLayout contentLayout = smartDrawText(canvas, textPaint, news.content, contentWidth, leftMargin, startY);
        int contentHeight = contentLayout.getHeight();
        startY += contentHeight;

        //图片列表
        if(news.imageList != null) {
            for (int i = 0 ; i < news.imageList.size(); i++) {

                int x = leftMargin + (i%3) * (shareImageSize + shareImageGap);
                int y = startY + (i/3) * (shareImageSize + shareImageGap);

                //这些代码，需要fat修改一下，从服务器下载
                if (avatorImg != null) {
                    canvas.drawBitmap(avatorImg, x, y, paint);
                } else {
                    canvas.drawBitmap(resizeBitmap(imgDefault, 2.0f), x, y, paint);
                }
            }
        }

        //累加图片列表的高度，从而为了绘制下一行
        startY += imageTotalHeight;

        //发布时间
        textPaint.setTextSize(nameSize);
        textPaint.setColor(Color.GRAY);
        StaticLayout staticLayout2 = smartDrawText(canvas, textPaint, news.showtime, contentWidth, leftMargin, startY);
        int height1 = staticLayout2.getHeight();
        startY += height1;

        //点赞
        int hasZan = 1;
        if (news.preferList != null) {
            //点赞图标
            canvas.drawBitmap(resizeBitmap(zanImg, 1.0f), leftMargin, startY, paint);
            StringBuilder sb = new StringBuilder();
            for (String user : news.preferList) {
                sb.append(user);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 2);
            textPaint.setTextSize(nameSize);
            textPaint.setColor(Color.BLUE);
            StaticLayout staticLayout = smartDrawText(canvas, textPaint, sb.toString(), contentWidth, leftMargin + zanImg.getWidth() + textMargin, startY);
            int height2 = staticLayout.getHeight();
            startY += height2;
        } else {
            hasZan = 0;
        }

        //评论
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                textPaint.setTextSize(nameSize);
                textPaint.setColor(Color.BLACK);
                StaticLayout staticLayout = smartDrawText(canvas, textPaint, news.commentList.get(i).author + ": " + news.commentList.get(i).content, contentWidth, leftMargin, startY);
                int height2 = staticLayout.getHeight();
                startY += height2;
            }
        }


    }

    Bitmap resizeBitmap(Bitmap bitmap, float scale) {
        // Matrix类进行图片处理（缩小或者旋转）
        Matrix matrix = new Matrix();
        // 缩小一倍
        matrix.postScale(scale, scale);
        // 生成新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_UP)
        {
            if(mOnItemViewClickedListener != null)
            {
                mOnItemViewClickedListener.onItemClicked(news);
            }
        }

        return true;
    }
}