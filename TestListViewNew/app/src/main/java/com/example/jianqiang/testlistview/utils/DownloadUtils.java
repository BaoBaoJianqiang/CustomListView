package com.example.jianqiang.testlistview.utils;


import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;


public class DownloadUtils {

    private OkHttpClient mOkHttpClient;

    public DownloadUtils() {
        mOkHttpClient = new OkHttpClient();
    }

    public Observable<Bitmap> downloadIamge(final String path) {

        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {

                if (!subscriber.isUnsubscribed()) {

                    final Request request = new Request.Builder().url(path).build();
                    mOkHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                Bitmap bitmap = DecodeUtils.doParse(response);
                                if (bitmap!=null) {
                                    subscriber.onNext(bitmap);
                                }
//                                    File file = FileUtils.getFile(data, Environment.getExternalStorageDirectory().getPath(),  "temp.jpg");
//                                    Luban.with(MyApp.getApp()).load(file).setCompressListener(new OnCompressListener() {
//                                        @Override
//                                        public void onStart() {
//                                            subscriber.onStart();
//                                        }
//
//                                        @Override
//                                        public void onSuccess(File file) {

//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            subscriber.onError(e);
//                                        }
//                                    }).get();

//                                }
                            }
                            subscriber.onCompleted();
                        }
                    });
                }
            }
        });
    }


}