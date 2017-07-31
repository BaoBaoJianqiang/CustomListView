package com.example.jianqiang.testlistview.utils;


import android.graphics.Bitmap;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DownloadUtils {

    private OkHttpClient mOkHttpClient;

    public DownloadUtils() {
        mOkHttpClient = new OkHttpClient();
    }

    public Flowable<Bitmap> downLoadImageFlowable(final String path) {
        return  Flowable.create(new FlowableOnSubscribe<Bitmap>() {

            @Override
            public void subscribe(@NonNull final FlowableEmitter<Bitmap> subscriber) throws Exception {

                final Request request = new Request.Builder().url(path).build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Bitmap bitmap = DecodeUtils.doParse(response);
                            if (bitmap != null) {
                                System.out.println("lip onResponse "+bitmap);
                                subscriber.onNext(bitmap);

                            }

                        }
                        System.out.println("lip onComplete ");
                        subscriber.onComplete();
                    }
                });

            }


        },BackpressureStrategy.BUFFER);
    }


    public Observable<Bitmap> downLoadImageObservable(final String path) {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Bitmap> subscriber) throws Exception {

                if (!subscriber.isDisposed()) {

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
                            }
                           subscriber.onComplete();
                        }
                    });
                }
            }
        });

    }

}