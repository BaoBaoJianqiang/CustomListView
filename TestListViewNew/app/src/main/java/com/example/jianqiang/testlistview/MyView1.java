package com.example.jianqiang.testlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView1 extends View {
    Paint paint;
    News news = new News();
    Bitmap zanImg;
    Bitmap imgDefault;
    private int mBaseHeight;
    private int mLineHeight;
    private int mNameSize;
    private int mLeftPadding;
    private int mContentSize;

    public MyView1(Context context) {
        this(context, null);
    }


    public MyView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView1);
        mBaseHeight = DensityUtils.dp2px(context, ta.getDimension(R.styleable.MyView1_image_width, 35));
        mLineHeight = (int) ta.getDimension(R.styleable.MyView1_line_height, 14);
        mNameSize = (int) ta.getDimension(R.styleable.MyView1_name_size, 13);
        mContentSize = (int) ta.getDimension(R.styleable.MyView1_content_size, 14);
        mLeftPadding = (int) ta.getDimension(R.styleable.MyView1_left_padding, 10);
        ta.recycle();
        initPaint(context);
    }

    private void initPaint(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        zanImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.zan);
        imgDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    public void setData(News news) {
        this.news = news;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measuredWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec));
    }

    //测量宽度
    private int measuredWidth(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY://width为match或者具体的长度
                result = width;
                break;
            case MeasureSpec.AT_MOST://wrap情况不作处理
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }

    //测量高度
    private int measuredHeight(int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY://height为match或者具体的长度
                int realHeight = getPaddingTop() + getPaddingBottom() + mBaseHeight;
                if (news.preferList != null)
                    realHeight += mLineHeight;
                if (news.commentList != null) {
                    realHeight += news.commentList.size() * mLineHeight;
                }
                result = realHeight;
                break;
            case MeasureSpec.AT_MOST://height为wrap_content
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        Log.d("result--->", String.valueOf(result));
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制头像
        canvas.drawBitmap(resizeBitmap(imgDefault, 0.5f), mLeftPadding, mLeftPadding, paint);

        int topPos = 30;

        //人名
        paint.setTextSize(mNameSize);
        canvas.drawText(news.author, imgDefault.getWidth(), topPos, paint);
        //内容
        paint.setTextSize(mContentSize);
        canvas.drawText(news.content, imgDefault.getWidth(), topPos + mNameSize + 20, paint);

        //发布时间
        paint.setTextSize(mContentSize);
        canvas.drawText(news.showtime, imgDefault.getWidth(), topPos + mNameSize + 20  + mContentSize, paint);

        //点赞
        int hasZan = 1;
        if (news.preferList != null) {
            //点赞图标
            canvas.drawBitmap(resizeBitmap(zanImg, 0.25f), imgDefault.getWidth(), topPos + mNameSize + mContentSize + 10 + mContentSize + 10, paint);
            StringBuilder sb = new StringBuilder();
            for (String user : news.preferList) {
                sb.append(user);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1);
            paint.setTextSize(mContentSize);
            canvas.drawText(sb.toString(), imgDefault.getWidth()+ 60, topPos + mNameSize + mContentSize + 10 + mContentSize + 40, paint);

        } else {
            hasZan = 0;
        }


        //评论
        int base = topPos + mNameSize + mContentSize + 10 + mContentSize + 10 + 60 + 40 * hasZan;
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                paint.setTextSize(mContentSize);
                canvas.drawText(
                        news.commentList.get(i).author + ": " + news.commentList.get(i).content,
                        imgDefault.getWidth(),
                        base + 50 * i,
                        paint);
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
}