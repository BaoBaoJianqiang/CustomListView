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
import android.os.Handler;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jianqiang.testlistview.awares.IResultListener;
import com.example.jianqiang.testlistview.awares.ItemViewAware;
import com.example.jianqiang.testlistview.awares.ListAdapterAware;
import com.example.jianqiang.testlistview.awares.OnItemViewClickedListener;
import com.example.jianqiang.testlistview.entitiy.News;
import com.example.jianqiang.testlistview.helpers.AccountHelper;
import com.example.jianqiang.testlistview.helpers.ItemViewLayoutConfig;
import com.example.jianqiang.testlistview.helpers.ViewCoordinateHelper;
import com.example.jianqiang.testlistview.helpers.ZanContentHelper;
import com.example.jianqiang.testlistview.utils.ClipboardUtils;
import com.example.jianqiang.testlistview.utils.FrescoUtils;
import com.example.jianqiang.testlistview.utils.Utils;

import static com.example.jianqiang.testlistview.utils.Utils.smartDrawText;

@SuppressWarnings("unchecked")
public class MyView1 extends View implements ItemViewAware<News> {
    private static final int LONG_PRESS_INTERVAL = 750;

    private Paint paint;
    private TextPaint textPaint;
    private News news;
    private ViewCoordinateHelper viewCoordinateHelper;//记录各个view的坐标
    private ZanContentHelper zanContentHelper;    //记录"赞"相关内容的参数
    int lastTotalHeight = 0;//记录上次绘制时的cell总高度
    boolean isDrawRequestFromZanAction = false;//标识新一次的onDraw事件是否由赞事件发起，onDraw结束后需要重置为false
    Bitmap zanImg, zanButtonOff, zanButtonOn, imgDefault, avatorImg, articleImg, listDefault;
    Context mContext;
    OnItemViewClickedListener<News> mOnItemViewClickedListener;
    private ItemViewLayoutConfig.ConfigBuilder mBuilder;
    private int contentWidth;//绘制区域的宽度
    private int allImageHeight;//图片的累计高度
    private Handler touchEventHandler;
    //用于图片列表
    SparseArray<Bitmap> listImg = new SparseArray<>();
    //行间距
    int cellMargin = 20;
    private int count;

    public MyView1(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        touchEventHandler = new Handler();
        mContext = context;
        zanImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.zan);
        zanButtonOn = BitmapFactory.decodeResource(context.getResources(), R.mipmap.btn_star_on);
        zanButtonOff = BitmapFactory.decodeResource(context.getResources(), R.mipmap.btn_star_off);
        imgDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_image);
        listDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.list_default);
        articleImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.list_default);
        avatorImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_image);
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
            for (int i = 0; i < news.imageList.size(); i++) {
                listImg.put(i, listDefault);
            }

        }
    }

    @Override
    public boolean triggerNetworkJob(final ListAdapterAware adapter, final int position) {
        //获取人物头像
        Uri uri = Uri.parse(news.avator);
        FrescoUtils.downloadBitmap(uri, null, mContext, new IResultListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                if(bitmap == null) return;

                avatorImg = bitmap;
                postInvalidate();
            }

            @Override
            public void onFail() {

            }
        });

        //获取文章图片
        if (news.article != null) {
            Uri uri2 = Uri.parse(news.article.imageUrl);
            FrescoUtils.downloadBitmap(uri2, null, mContext, new IResultListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    if(bitmap == null) return;

                    articleImg = bitmap;
                    postInvalidate();
                }

                @Override
                public void onFail() {

                }
            });
        }

        //获取图片列表
        if (news.imageList != null) {
            for (int i = 0; i < news.imageList.size(); i++) {
                Uri imageUri = Uri.parse(news.imageList.get(i).smallImageUrl);
                final View targetView = new View(getContext());
                targetView.setTag(i);
                FrescoUtils.downloadBitmap(imageUri, targetView, mContext, new IResultListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        count++;
                        int tag = (int) targetView.getTag();
                        listImg.put(tag, bitmap);
                        if (listImg.size() == news.imageList.size())
                            postInvalidate();
                    }

                    @Override
                    public void onFail() {
                        count++;
                        if (listImg.size() == news.imageList.size())
                            postInvalidate();
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
        setMeasuredDimension(measureWidth(widthMeasureSpec), getTotalHeight());
        //TODO onMeasure中调用requestLayout会引起死循环
//        Future<Integer> submit = ExecutorUtils.getDefaultExecutor().submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return getTotalHeight();
//            }
//        });
//        try {
//            Integer integer = submit.get();
//            Log.d("value---->", String.valueOf(integer));
//            setMeasuredDimension(measureWidth(widthMeasureSpec), integer);
//            requestLayout();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        } else {
            zanContentHelper.isZanContentHeightChanged = true;

            zanContentHelper.zanContentHeightDiff = newPreferContentHeight - zanContentHelper.lastZanContentHeight;
        }

        requestLayout();
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
                imageTotalHeight = listDefault.getWidth();
            else if (length < 7)
                imageTotalHeight = listDefault.getWidth() * 2 + mBuilder.getImageSpace();
            else
                imageTotalHeight = listDefault.getWidth() * 3 + mBuilder.getImageSpace() * 2;
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
        lastTotalHeight = totalHeight + lineCount * mBuilder.getLineMargin() + cellMargin;
        return totalHeight + mBuilder.getLineMargin() * lineCount + cellMargin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int leftMargin = imgDefault.getWidth() + mBuilder.getImageSpace() + mBuilder.getLineMargin();//文字左边距
        int startY = 0;
        Log.d("width---->", String.valueOf(listDefault.getWidth()));
        //头像
        if (avatorImg != null) {
            canvas.drawBitmap(resizeBitmap(avatorImg, (float) (imgDefault.getWidth()) / avatorImg.getWidth()), mBuilder.getImageSpace(), mBuilder.getImageSpace(), paint);
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
        viewCoordinateHelper.setContentRect(leftMargin, startY, leftMargin + contentLayout.getWidth(), startY + contentLayout.getHeight());
        startY += contentHeight + mBuilder.getLineMargin();

        //分享文章
        if (news.article != null) {
            paint.setColor(Color.LTGRAY);// 设置灰色
            paint.setStyle(Paint.Style.FILL);//设置填满
            canvas.drawRect(leftMargin, startY, leftMargin + contentWidth, startY + mBuilder.getArticleHeight(), paint);// 长方形
            viewCoordinateHelper.setArticleRect(leftMargin, startY, leftMargin + contentWidth, startY + mBuilder.getArticleHeight());
            if (articleImg != null) {
                canvas.drawBitmap(articleImg, leftMargin, startY, paint);
            } else {
                canvas.drawBitmap(imgDefault, leftMargin, startY, paint);
            }

            paint.setTextSize(mBuilder.getArticleFontSize());
            paint.setColor(Color.BLACK);//
            smartDrawText(canvas, textPaint, news.article.title, contentWidth, leftMargin + avatorImg.getWidth() + mBuilder.getLineMargin(), startY);
            startY += articleImg.getHeight() + mBuilder.getLineMargin();
        }

        //图片列表
        if (news.imageList != null) {
            for (int i = 0; i < news.imageList.size(); i++) {
                int x = leftMargin + (i % 3) * (listDefault.getWidth() + mBuilder.getImageSpace());
                int y = startY + (i / 3) * (listDefault.getWidth() + mBuilder.getImageSpace());

                //这些代码，需要fat修改一下，从服务器下载
                if (listImg.get(i) != null) {
                    canvas.drawBitmap(resizeBitmap(listImg.get(i), (float) (listDefault.getWidth()) / listImg.get(i).getWidth()), x, y, paint);
                } else {
                    canvas.drawBitmap(listDefault, x, y, paint);
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
        Log.d("scale---->", String.valueOf(scale));
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
        int lastX = (int) event.getX();
        int lastY = (int) event.getY();

        if (action == MotionEvent.ACTION_DOWN) {

            viewCoordinateHelper.recordDownAction(lastX, lastY);

            if(viewCoordinateHelper.isInContentArea(lastX, lastY))
            {
                touchEventHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        TextView popText = new TextView(mContext);
                        popText.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                        popText.setGravity(Gravity.CENTER);
                        popText.setBackgroundColor(Color.WHITE);
                        popText.setText("复制");

                        int[] lastDownActionXY = viewCoordinateHelper.getLastDownActionXY();

                        final PopupWindow pw = new PopupWindow(popText, 150, 75);
                        pw.setTouchable(true);
                        pw.setOutsideTouchable(true);
                        pw.showAsDropDown(MyView1.this, lastDownActionXY[0] + 30, -(30 + lastTotalHeight), Gravity.TOP);

                        popText.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                ClipboardUtils.copy(mContext, news.content);
                                pw.dismiss();
                                Toast.makeText(mContext, "已复制", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, LONG_PRESS_INTERVAL);
            }
        }
        else if (action == MotionEvent.ACTION_MOVE) {

            int[] lastDownActionXY = viewCoordinateHelper.getLastDownActionXY();

            if(lastDownActionXY != null && viewCoordinateHelper.isInContentArea(lastDownActionXY[0], lastDownActionXY[1])
                    && !viewCoordinateHelper.isInContentArea(lastX, lastY))
            {
                touchEventHandler.removeCallbacksAndMessages(null);
            }
        }
        else if (action == MotionEvent.ACTION_UP) {

            int[] lastDownActionXY = viewCoordinateHelper.getLastDownActionXY();

            if (viewCoordinateHelper.isInZanArea(lastX, lastY)) {
                if (zanContentHelper.isZan) {
                    zanContentHelper.removeZan(AccountHelper.getInstance().getAccountName());
                } else {
                    zanContentHelper.addZan(AccountHelper.getInstance().getAccountName());
                }

                updateCellAfterZanAction();
            } else if (viewCoordinateHelper.isInArticleArea(lastY, lastY)) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(news.article.articleUrl);
                intent.setData(url);
                getContext().startActivity(intent);
            } else if (lastDownActionXY != null && !viewCoordinateHelper.isInContentArea(lastDownActionXY[0], lastDownActionXY[1])){
                if (mOnItemViewClickedListener != null) {
                    mOnItemViewClickedListener.onItemClicked(news);
                }
            }

            touchEventHandler.removeCallbacksAndMessages(null);
        }
        else {
            touchEventHandler.removeCallbacksAndMessages(null);
        }

        return true;
    }
}