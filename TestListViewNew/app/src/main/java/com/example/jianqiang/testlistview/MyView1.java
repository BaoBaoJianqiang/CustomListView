package com.example.jianqiang.testlistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.jianqiang.testlistview.awares.IResultListener;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.awares.OnItemViewClickedListener;
import com.example.jianqiang.testlistview.helpers.AccountHelper;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;
import com.example.jianqiang.testlistview.helpers.ViewCoordinateHelper;
import com.example.jianqiang.testlistview.helpers.ZanContentHelper;

import static com.example.jianqiang.testlistview.Utils.smartDrawText;

public class MyView1 extends View implements ItemViewAware<News> {
    Paint paint;
    TextPaint textPaint;
    int margin = 30;
    int nameSize = 30;
    int titleSize = 32;
    int textMargin = 10;
    int imageMargin = 10;
    int contentWidth = 500;

    //记录各个view的坐标
    ViewCoordinateHelper viewCoordinateHelper;

    //图片列表, 每张图片的宽度（高度），图片之间的间距
    int shareImageSize = 100;
    int shareImageGap = 10;
    //图片列表的总高度
    int imageTotalHeight = 0;
    //记录"赞"相关内容的参数
    ZanContentHelper zanContentHelper;
    //记录上次绘制时的cell总高度
    int lastTotalHeight = 0;
    //标识新一次的onDraw事件是否由赞事件发起，onDraw结束后需要重置为false
    boolean isDrawRequestFromZanAction = false;

    News news;
    Bitmap zanImg;
    Bitmap zanButtonOff;
    Bitmap zanButtonOn;
    Bitmap imgDefault;
    Bitmap avatorImg;

    //文章左边的图片
    Bitmap articleImg;

    Bitmap listImg;
    Context mContext;
    OnItemViewClickedListener<News> mOnItemViewClickedListener;

    //用于图片列表
//    ImageView[] imageViews;

    //分享文章的高度和宽度都是固定的
    int articleHeight = 150;
    int articleFontSize = 30;


    public MyView1(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        mContext = context;
        zanImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.zan);
        zanButtonOn = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_star_on);
        zanButtonOff = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_star_off);
        imgDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        viewCoordinateHelper = new ViewCoordinateHelper();
    }

    @Override
    public void setData(News news, ItemViewLayoutConfig layoutConfig) {
        if (layoutConfig == null)
            return;
        this.news = news;

        zanContentHelper = new ZanContentHelper(this.news.preferList);

        if (news.imageList != null) {
//            imageViews = new ImageView[news.imageList.size()];
        }
    }

    @Override
    public boolean triggerNetworkJob(final ListAdapterAware adapter, final int position) {

        //获取人物头像
        Uri uri = Uri.parse(news.avator);
        FrescoUtils.downloadBitmap(uri, mContext, new IResultListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                avatorImg =bitmap;
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

        //获取文章图片
        if(news.article != null) {
            Uri uri2 = Uri.parse(news.article.imageUrl);
            FrescoUtils.downloadBitmap(uri2, mContext, new IResultListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    articleImg = bitmap;
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
        }

        //获取图片列表
        if(news.imageList != null) {
            for (int i = 0 ; i < news.imageList.size(); i++) {
                final int[] count = new int[1];

                Uri imageUri = Uri.parse(news.imageList.get(i).smallImageUrl);
                FrescoUtils.downloadBitmap(imageUri, mContext, new IResultListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {

                        ImageView imageView = new ImageView(mContext);
                        imageView.setTag(bitmap);
//                        imageViews[count[0]++] = imageView;
                        listImg = bitmap;

                        post(new Runnable()
                        {
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
            }
        }

        return true;
    }

    @Override
    public void setOnItemViewClickedListener(OnItemViewClickedListener<News> listener) {
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

    private void updateCellAfterZanAction()
    {
        int leftMargin = imgDefault.getWidth() / 2;//文字左边距

        Canvas canvas = new Canvas();

        String composedPreferString = zanContentHelper.getComposedPreferString();

        int newPreferContentHeight = TextUtils.isEmpty(composedPreferString) ? 0 :
                Utils.smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(),
                        contentWidth, leftMargin + zanImg.getWidth() + textMargin, 0).getHeight();

        isDrawRequestFromZanAction = true;

        if(newPreferContentHeight == zanContentHelper.lastZanContentHeight)
        {
            zanContentHelper.isZanContentHeightChanged = false;

            invalidate();
        }
        else
        {
            zanContentHelper.isZanContentHeightChanged = true;

            zanContentHelper.zanContentHeightDiff = newPreferContentHeight - zanContentHelper.lastZanContentHeight;

            requestLayout();
        }
    }

    private void zanActionUpdateProcessed()
    {
        zanContentHelper.isZanContentHeightChanged = false;

        isDrawRequestFromZanAction = false;
    }

    //获取总高度
    public int getTotalHeight() {

        if(isDrawRequestFromZanAction && lastTotalHeight > 0)
        {
            return lastTotalHeight += zanContentHelper.zanContentHeightDiff;
        }

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
        int height = Math.max(Utils.smartDrawText(canvas, textPaint, news.showtime, contentWidth, leftMargin, totalHeight).getHeight(), zanButtonOn.getHeight());
        totalHeight += height;

        //分享文章
        if(news.article != null) {
            totalHeight += articleHeight;
        }

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
            totalHeight += Utils.smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(), contentWidth, leftMargin + zanImg.getWidth() + textMargin,
                    totalHeight).getHeight();
        }
        //评论的总高度
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                textPaint.setTextSize(nameSize);
                textPaint.setColor(Color.BLACK);
                totalHeight += Utils.smartDrawText(canvas, textPaint, news.commentList.get(i).author + ": " + news.commentList.get(i).content, contentWidth, leftMargin, totalHeight).getHeight();
            }
        }

        lastTotalHeight = totalHeight;

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


        //分享文章
        if(news.article != null) {
            paint.setColor(0xFFF3F3F5);// 设置灰色
            paint.setStyle(Paint.Style.FILL);//设置填满
            canvas.drawRect(leftMargin, startY, contentWidth, articleHeight, paint);// 长方形
            viewCoordinateHelper.setArticleRect(leftMargin, startY, contentWidth, articleHeight);

            paint.setTextSize(articleFontSize);
            paint.setColor(Color.BLACK);//
            canvas.drawText(news.article.title, leftMargin + imgDefault.getWidth(), startY, paint);

            if (articleImg != null) {
                canvas.drawBitmap(articleImg, leftMargin, startY, paint);
            } else {
                canvas.drawBitmap(resizeBitmap(imgDefault, 2.0f), leftMargin, startY, paint);
            }


            startY += articleHeight;
        }

        //图片列表
        if(news.imageList != null) {
            for (int i = 0 ; i < news.imageList.size(); i++) {

                int x = leftMargin + (i%3) * (shareImageSize + shareImageGap);
                int y = startY + (i/3) * (shareImageSize + shareImageGap);

                //这些代码，需要fat修改一下，从服务器下载
                if (listImg!=null ) {
                    canvas.drawBitmap(listImg, x, y, paint);
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
        //点赞button
        int zanButtonLeft = leftMargin + 400;

        if(zanContentHelper.isZan)
        {
            viewCoordinateHelper.setZanButtonRect(zanButtonLeft, startY, zanButtonOn.getWidth(), zanButtonOn.getHeight());

            canvas.drawBitmap(zanButtonOn, zanButtonLeft, startY, paint);
        }
        else
        {
            viewCoordinateHelper.setZanButtonRect(zanButtonLeft, startY, zanButtonOff.getWidth(), zanButtonOff.getHeight());

            canvas.drawBitmap(zanButtonOff, zanButtonLeft, startY, paint);
        }
        int height1 = Math.max(zanButtonOn.getHeight(), staticLayout2.getHeight());
        startY += height1;


        //点赞
        int hasZan = 1;
        if (!TextUtils.isEmpty(zanContentHelper.getComposedPreferString())) {
            //点赞图标
            canvas.drawBitmap(zanImg, leftMargin, startY, paint);
            textPaint.setTextSize(nameSize);
            textPaint.setColor(Color.BLUE);
            zanContentHelper.lastZanContentHeight = smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(), contentWidth, leftMargin + zanImg.getWidth() + textMargin,
                    startY).getHeight();

            startY += zanContentHelper.lastZanContentHeight;
        } else {
            hasZan = 0;
            zanContentHelper.lastZanContentHeight = 0;
        }

        zanActionUpdateProcessed();

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
            if(viewCoordinateHelper.isZanClicked((int)event.getX(), (int)event.getY()))
            {
                if(zanContentHelper.isZan)
                {
                    zanContentHelper.removeZan(AccountHelper.getInstance().getAccountName());
                }
                else
                {
                    zanContentHelper.addZan(AccountHelper.getInstance().getAccountName());
                }

                updateCellAfterZanAction();
            }
            else if(viewCoordinateHelper.isArticleClicked((int)event.getX(), (int)event.getY()))
            {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(news.article.articleUrl);
                intent.setData(url);
                getContext().startActivity(intent);
            }
            else
            {
                if(mOnItemViewClickedListener != null)
                {
                    mOnItemViewClickedListener.onItemClicked(news);
                }
            }
        }

        return true;
    }
}