package com.example.jianqiang.testlistview;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.jianqiang.testlistview.awares.IResultListener;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.awares.OnItemViewClickedListener;
import com.example.jianqiang.testlistview.executor.ExecutorUtils;
import com.example.jianqiang.testlistview.helpers.AccountHelper;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;
import com.example.jianqiang.testlistview.helpers.ViewCoordinateHelper;
import com.example.jianqiang.testlistview.helpers.ZanContentHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.example.jianqiang.testlistview.Utils.smartDrawText;

@SuppressWarnings("unchecked")
public class MyView1 extends View implements ItemViewAware<News> {
    private Paint paint;
    private TextPaint textPaint;
    private News news;
    private ViewCoordinateHelper viewCoordinateHelper;//记录各个view的坐标
    private ZanContentHelper zanContentHelper;    //记录"赞"相关内容的参数
    int lastTotalHeight = 0;//记录上次绘制时的cell总高度
    boolean isDrawRequestFromZanAction = false;//标识新一次的onDraw事件是否由赞事件发起，onDraw结束后需要重置为false
    Bitmap zanImg, zanButtonOff, zanButtonOn, imgDefault, avatorImg, articleImg, listImg;
    Context mContext;
    OnItemViewClickedListener<News> mOnItemViewClickedListener;
    private ItemViewLayoutConfig.ConfigBuilder mBuilder;
    private int contentWidth;//绘制区域的宽度
    private int allImageHeight;//图片的累计高度
    //用于图片列表
//    ImageView[] imageViews;


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
        if (mBuilder == null)
            mBuilder = layoutConfig.getBuilder();
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
                avatorImg = bitmap;
                post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }

            @Override
            public void onFail() {

            }
        });

        //获取文章图片
        if (news.article != null) {
            Uri uri2 = Uri.parse(news.article.imageUrl);
            FrescoUtils.downloadBitmap(uri2, mContext, new IResultListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    articleImg = bitmap;
                    post(new Runnable() {
                        @Override
                        public void run() {
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
        if (news.imageList != null) {
            for (int i = 0; i < news.imageList.size(); i++) {
                final int[] count = new int[1];
                Uri imageUri = Uri.parse(news.imageList.get(i).smallImageUrl);
                FrescoUtils.downloadBitmap(imageUri, mContext, new IResultListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {

                        ImageView imageView = new ImageView(mContext);
                        imageView.setTag(bitmap);
//                        imageViews[count[0]++] = imageView;
                        listImg = bitmap;
                        post(new Runnable() {
                            @Override
                            public void run() {
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

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), 0);
        Future<Integer> submit = ExecutorUtils.getDefaultExecutor().submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getTotalHeight();
            }
        });
        try {
            Integer integer = submit.get();
            Log.d("value---->", String.valueOf(integer));
            setMeasuredDimension(measureWidth(widthMeasureSpec), integer);
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                contentWidth = specSize - imgDefault.getWidth() - 2 * mBuilder.getrightMargin();
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }

    private void updateCellAfterZanAction() {
        int leftMargin = imgDefault.getWidth() / 2;//文字左边距

        Canvas canvas = new Canvas();

        String composedPreferString = zanContentHelper.getComposedPreferString();

        int newPreferContentHeight = TextUtils.isEmpty(composedPreferString) ? 0 :
                Utils.smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(),
                        contentWidth, leftMargin + zanImg.getWidth() + mBuilder.getContentSize(), 0).getHeight();

        isDrawRequestFromZanAction = true;

        if (newPreferContentHeight == zanContentHelper.lastZanContentHeight) {
            zanContentHelper.isZanContentHeightChanged = false;

            invalidate();
        } else {
            zanContentHelper.isZanContentHeightChanged = true;

            zanContentHelper.zanContentHeightDiff = newPreferContentHeight - zanContentHelper.lastZanContentHeight;

            requestLayout();
        }
    }

    private void zanActionUpdateProcessed() {
        zanContentHelper.isZanContentHeightChanged = false;

        isDrawRequestFromZanAction = false;
    }

    //获取总高度
    public int getTotalHeight() {
        int lineCount = 0;//记录间距
        if (isDrawRequestFromZanAction && lastTotalHeight > 0) {
            return lastTotalHeight += zanContentHelper.zanContentHeightDiff;
        }

        int leftMargin = imgDefault.getWidth() / 2;//文字左边距
        Canvas canvas = new Canvas();
        int totalHeight = 0;

        //人名的高度
        textPaint.setTextSize(mBuilder.getNameSize());

        totalHeight += Utils.smartDrawText(canvas, textPaint, news.author, contentWidth, leftMargin, totalHeight).getHeight();
        //内容的高度
        textPaint.setTextSize(mBuilder.getContentSize());
        totalHeight += Utils.smartDrawText(canvas, textPaint, news.content, contentWidth, leftMargin, totalHeight).getHeight();
        lineCount++;
        //发布时间的高度
        textPaint.setTextSize(mBuilder.getContentSize());
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int timeHeight = (int) (fontMetrics.bottom - fontMetrics.top);
        int height = Math.max(timeHeight, zanButtonOn.getHeight());
        totalHeight += height;
        lineCount++;
        Log.e("height------->", String.valueOf(totalHeight));
        //分享文章
        if (news.article != null) {
            totalHeight += articleImg.getHeight();
            lineCount++;
        }
        //分享图片
        int imageTotalHeight = 0;
        if (news.imageList != null) {
            int length = news.imageList.size();
            if (length > 0 && length < 4)
                imageTotalHeight = listImg.getWidth();
            else if (length < 7)
                imageTotalHeight = listImg.getWidth() * 2 + mBuilder.getImageSpace();
            else
                imageTotalHeight = listImg.getWidth() * 3 + mBuilder.getImageSpace() * 2;
            allImageHeight = imageTotalHeight;
            totalHeight += imageTotalHeight;
            lineCount++;
        }

        //点赞的高度
        if (news.preferList != null) {
            textPaint.setTextSize(mBuilder.getNameSize());
            textPaint.setColor(Color.BLUE);
            totalHeight += Utils.smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(), contentWidth, leftMargin + zanImg.getWidth() + mBuilder.getLineMargin(),
                    totalHeight).getHeight();
            lineCount++;

        }
        //评论的总高度
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                textPaint.setTextSize(mBuilder.getNameSize());
                textPaint.setColor(Color.BLACK);
                totalHeight += Utils.smartDrawText(canvas, textPaint, news.commentList.get(i).author + ": " + news.commentList.get(i).content, contentWidth, leftMargin, totalHeight).getHeight();
            }
            lineCount++;
        }
        lastTotalHeight = totalHeight + lineCount * mBuilder.getLineMargin();
        return totalHeight + mBuilder.getLineMargin() * lineCount;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int leftMargin = imgDefault.getWidth() + mBuilder.getImageSpace();//文字左边距
        int startY = 0;
        if (avatorImg != null) {
            canvas.drawBitmap(avatorImg, mBuilder.getImageSpace(), mBuilder.getImageSpace(), paint);
        } else {
            canvas.drawBitmap(imgDefault, mBuilder.getImageSpace(), mBuilder.getImageSpace(), paint);
        }

        //人名
        textPaint.setTextSize(mBuilder.getNameSize());
        textPaint.setColor(Color.BLUE);
        StaticLayout staticLayout1 = Utils.smartDrawText(canvas, textPaint, news.author, contentWidth, leftMargin, startY);
        int height = staticLayout1.getHeight();
        startY += height + mBuilder.getLineMargin();

        //内容
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(mBuilder.getContentSize());
        StaticLayout contentLayout = Utils.smartDrawText(canvas, textPaint, news.content, contentWidth, leftMargin, startY);
        int contentHeight = contentLayout.getHeight();
        startY += contentHeight + mBuilder.getLineMargin();

        //分享文章
        if (news.article != null) {
            paint.setColor(Color.RED);// 设置灰色
            paint.setStyle(Paint.Style.FILL);//设置填满
            //TODO 画上矩形之后，位置会向偏移，未找到原因
//            canvas.drawRect(leftMargin, startY, contentWidth, articleHeight, paint);// 长方形
            viewCoordinateHelper.setArticleRect(leftMargin, startY, contentWidth, mBuilder.getArticleHeight());
            if (articleImg != null) {
                canvas.drawBitmap(articleImg, leftMargin, startY, paint);
            } else {
                canvas.drawBitmap(imgDefault, leftMargin, startY, paint);
            }

            paint.setTextSize(mBuilder.getArticleFontSize());
            paint.setColor(Color.BLACK);//
            smartDrawText(canvas, textPaint, news.article.title, contentWidth, leftMargin+avatorImg.getWidth()+mBuilder.getLineMargin() , startY);
            startY += articleImg.getHeight() + mBuilder.getLineMargin();
        }

        //图片列表
        if (news.imageList != null) {
            for (int i = 0; i < news.imageList.size(); i++) {
                int x = leftMargin + (i % 3) * (listImg.getWidth() + mBuilder.getImageSpace());
                int y = startY + (i / 3) * (listImg.getWidth() + mBuilder.getImageSpace());

                //这些代码，需要fat修改一下，从服务器下载
                if (listImg != null) {
                    canvas.drawBitmap(listImg, x, y, paint);
                } else {
                    canvas.drawBitmap(imgDefault, x, y, paint);
                }
            }
            //累加图片列表的高度，从而为了绘制下一行
            startY += allImageHeight + mBuilder.getLineMargin();
        }

        //发布时间
        textPaint.setTextSize(mBuilder.getNameSize());
        int textWidth = (int) textPaint.measureText(news.showtime);
        textPaint.setColor(Color.GRAY);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int timeHeight = (int) (fontMetrics.bottom - fontMetrics.top);
        int baseY = (int) (startY + timeHeight - fontMetrics.bottom);
        canvas.drawText(news.showtime, leftMargin, baseY, textPaint);
        //点赞button
        int zanButtonLeft = textWidth + leftMargin + mBuilder.getLineMargin();
        if (zanContentHelper.isZan) {
            viewCoordinateHelper.setZanButtonRect(zanButtonLeft, startY, zanButtonOn.getWidth(), zanButtonOn.getHeight());
            canvas.drawBitmap(zanButtonOn, zanButtonLeft, startY, paint);
        } else {
            viewCoordinateHelper.setZanButtonRect(zanButtonLeft, startY, zanButtonOff.getWidth(), zanButtonOff.getHeight());

            canvas.drawBitmap(zanButtonOff, zanButtonLeft, startY, paint);
        }
        startY += zanButtonOn.getHeight();

        //点赞
        int hasZan = 1;
        if (!TextUtils.isEmpty(zanContentHelper.getComposedPreferString())) {
            //点赞图标
            canvas.drawBitmap(zanImg, leftMargin, startY, paint);
            textPaint.setTextSize(mBuilder.getNameSize());
            textPaint.setColor(Color.BLUE);
            zanContentHelper.lastZanContentHeight = Utils.smartDrawText(canvas, textPaint, zanContentHelper.getComposedPreferString(), contentWidth - zanImg.getWidth(), leftMargin + zanImg.getWidth(), startY).getHeight();
            startY += zanContentHelper.lastZanContentHeight + mBuilder.getLineMargin();
        } else {
            hasZan = 0;
            zanContentHelper.lastZanContentHeight = 0;
        }
        startY += mBuilder.getLineMargin();
        zanActionUpdateProcessed();
        //评论
        if (news.commentList != null) {
            for (int i = 0; i < news.commentList.size(); i++) {
                textPaint.setTextSize(mBuilder.getNameSize());
                textPaint.setColor(Color.BLACK);
                StaticLayout staticLayout = Utils.smartDrawText(canvas, textPaint, news.commentList.get(i).author + ": " + news.commentList.get(i).content, contentWidth, leftMargin, startY);
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
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            if (viewCoordinateHelper.isZanClicked((int) event.getX(), (int) event.getY())) {
                if (zanContentHelper.isZan) {
                    zanContentHelper.removeZan(AccountHelper.getInstance().getAccountName());
                } else {
                    zanContentHelper.addZan(AccountHelper.getInstance().getAccountName());
                }

                updateCellAfterZanAction();
            } else if (viewCoordinateHelper.isArticleClicked((int) event.getX(), (int) event.getY())) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(news.article.articleUrl);
                intent.setData(url);
                getContext().startActivity(intent);
            } else {
                if (mOnItemViewClickedListener != null) {
                    mOnItemViewClickedListener.onItemClicked(news);
                }
            }
        }

        return true;
    }
}