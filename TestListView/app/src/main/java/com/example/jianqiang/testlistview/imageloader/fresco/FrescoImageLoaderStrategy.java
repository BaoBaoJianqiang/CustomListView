package com.example.jianqiang.testlistview.imageloader.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.jianqiang.testlistview.imageloader.BaseImageLoaderStrategy;
import com.example.jianqiang.testlistview.imageloader.ImageConfig;
import com.example.jianqiang.testlistview.imageloader.OkHttpClientUtils;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by struggle_liping on 2017/7/16.
 */

public class FrescoImageLoaderStrategy implements BaseImageLoaderStrategy<FrescoImageConfig> {

    @Override
    public void loadImage(final Context ctx, FrescoImageConfig cf) {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(ctx)
                .setMaxCacheSize(100*1024*1024)
                .setBaseDirectoryName("imageCache")
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return ctx.getCacheDir();
                    }
                })
                .build();
//        MyImageCacheStatsTracker imageCacheStatsTracker = new MyImageCacheStatsTracker();

        OkHttpClient okHttpClient= OkHttpClientUtils.getClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(ctx,okHttpClient)
//                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setImageCacheStatsTracker(new ImageCacheStatsTracker(){

                    @Override
                    public void onBitmapCachePut() {

                    }

                    @Override
                    public void onBitmapCacheHit(CacheKey cacheKey) {

                    }

                    @Override
                    public void onBitmapCacheMiss() {

                    }

                    @Override
                    public void onMemoryCachePut() {

                    }

                    @Override
                    public void onMemoryCacheHit(CacheKey cacheKey) {

                    }

                    @Override
                    public void onMemoryCacheMiss() {

                    }

                    @Override
                    public void onStagingAreaHit(CacheKey cacheKey) {

                    }

                    @Override
                    public void onStagingAreaMiss() {

                    }

                    @Override
                    public void onDiskCacheHit() {

                    }

                    @Override
                    public void onDiskCacheMiss() {

                    }

                    @Override
                    public void onDiskCacheGetFail() {

                    }

                    @Override
                    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {

                    }

                    @Override
                    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {

                    }
                })
                .setDownsampleEnabled(true)//Downsampling，它处理图片的速度比常规的裁剪更快，
                // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                //让fresco即时清理内存:http://blog.csdn.net/honjane/article/details/65629799
                .setBitmapMemoryCacheParamsSupplier(new FrescoBitmapMemoryCacheSupplier((ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE)))
                .build();
        Fresco.initialize(ctx, config);
        requestBitmap(cf,ctx);


    }



    /**
     * 注:能够拿到网络gif的第一帧图,但拿不到res,本地file的第一帧图
     * @param config
     * @param ctx
     */
    private void requestBitmap(final ImageConfig config, Context ctx) {

        final ImageRequest request = buildRequest(config);
        final String finalUrl = request.getSourceUri().toString();//;MyUtil.appendUrl(config.getUrl());
        Log.e("uri",finalUrl);
        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(request, ctx);




        dataSource.subscribe(new BaseBitmapDataSubscriber(finalUrl,config.getWidth(),config.getHeight()) {
            @Override
            protected void onNewResultImpl(Bitmap bitmap,DataSource<CloseableReference<CloseableImage>> dataSource) {
                //注意，gif图片解码方法与普通图片不一样，是无法拿到bitmap的。如果要把gif的第一帧的bitmap返回，怎么做？
                //GifImage.create(bytes).decode(1l,9).getFrameInfo(1).
//                if(config.getShapeMode() == ShapeMode.OVAL){
//                    bitmap = MyUtil.cropCirle(bitmap,false);
//
//                }else if(config.getShapeMode() == ShapeMode.RECT_ROUND){
                    bitmap = rectRound(bitmap,0,0);
//                }



                config.getBitmapListener().onSuccess(bitmap);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                config.getBitmapListener().onFail();
            }
        }, CallerThreadExecutor.getInstance());

    }

    public  Bitmap rectRound(Bitmap source,int radius, int margin){
        return new RoundedCornersTransformation(radius,margin).transform(source,source.getWidth(),source.getHeight());
    }

    private ImageRequest buildRequest(ImageConfig config) {
        Uri uri = buildUriByType(config);

        ImageRequestBuilder builder =   ImageRequestBuilder.newBuilderWithSource(uri);
        Postprocessor postprocessor=null;


        ResizeOptions resizeOptions = getResizeOption(config);


        builder.setPostprocessor(postprocessor)
                .setResizeOptions(resizeOptions)//缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
                // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                .setAutoRotateEnabled(true);




        return builder.build();
    }


    /**
     * 类型	SCHEME	示例
     远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     本地文件	file://	FileInputStream
     Content provider	content://	ContentResolver
     asset目录下的资源	asset://	AssetManager
     res目录下的资源	res://	Resources.openRawResource
     Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
     * @param config
     * @return
     */
    public static Uri buildUriByType(ImageConfig config) {


        if(!TextUtils.isEmpty(config.getUrl())){
            String url = appendUrl(config.getUrl());
            return Uri.parse(url);
        }

        if(config.getPlaceholderResId() > 0){
            return Uri.parse("res://imageloader/" + config.getPlaceholderResId());
        }

        if(!TextUtils.isEmpty(config.getFilePath())){

            File file = new File(config.getFilePath());
            if(file.exists()){
                return Uri.fromFile(file);
            }
        }

        if(!TextUtils.isEmpty(config.getContentProvider())){
            String content = config.getContentProvider();
            if(!content.startsWith("content")){
                content = "content://"+content;
            }
            return Uri.parse(content);
        }




        return null;
    }


    public static String appendUrl(String url) {
        String newUrl = url;
        if(TextUtils.isEmpty(newUrl)){
            return newUrl;
        }
        boolean hasHost = url.contains("http:" ) || url.contains("https:" )  ;
//        if (!hasHost){
//            if(!TextUtils.isEmpty(GlobalConfig.baseUrl)){
//                newUrl = GlobalConfig.baseUrl+url;
//            }
//        }

        return newUrl;
    }



    private ResizeOptions getResizeOption(ImageConfig config) {
        ResizeOptions resizeOptions = null;
        if (config.getWidth() >0 && config.getHeight() > 0){
            resizeOptions = new ResizeOptions(config.getWidth(),config.getHeight());//todo 通过图片宽高和view宽高计算出最合理的resization
        }
        return resizeOptions;
    }





    @Override
    public void clear(Context ctx, FrescoImageConfig config) {
        Fresco.getImagePipeline().clearMemoryCaches();
        Fresco.getImagePipeline().clearCaches();
        Fresco.getImagePipeline().clearDiskCaches();
        String url = appendUrl(config.getUrl());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        imagePipeline.evictFromMemoryCache(uri);
    }
}
