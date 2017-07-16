package com.example.jianqiang.testlistview;

import android.os.Environment;

import java.io.File;


/**
 * Created by struggle_liping on 2017/7/16.
 */
public class FileConfiguration {

    private  String mAppRootPath;
    private  String mLogPath;
    private  String mImgPath;
    private  String mNetworkCachePath;

    /**
     * app的全局文件路径
     * @return
     */
    public String getAppRootPath(){
        return mAppRootPath;
    }

    /**
     * log 文件路径
     * @return
     */
    public String getLogPath(){
        return mLogPath;
    }
    /**
     * 图片缓存路径
     * @return
     */
    public String getImgPath(){
        return mImgPath;
    }


    /**
     * 创建未存在的文件夹
     * @param file
     * @return
     */
    public  File makeDirs(File file){
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }



    /**
     * 网络请求缓存
     * @return
     */
    public String getNetworkCache(){
        return mNetworkCachePath;
    }

    private FileConfiguration(Builder builder){

        mAppRootPath = builder.mAppRootPath;
        mImgPath = builder.mImgPath;
        mLogPath = builder.mLogPath;
        mNetworkCachePath = builder.mNetworkCachePath;

        File rootPath = new File(mAppRootPath + File.separator );

        if (!rootPath.exists()){
            rootPath.mkdirs();
        }

        File imgpath = new File(mImgPath);
        if (!imgpath.exists()){
            imgpath.mkdirs();
        }

        File logpath = new File(mLogPath);
        if (!logpath.exists()){
            logpath.mkdirs();
        }

        File networkCachePath = new File(mNetworkCachePath);
        if (!networkCachePath.exists()){
            networkCachePath.mkdirs();
        }

    }

    public static class Builder{
        private  String mAppRootPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "baojianqiang" + File.separator + MyApp.getApp().getPackageName();
        private  String mLogPath = mAppRootPath + File.separator + "log" + File.separator;
        private  String mImgPath = mAppRootPath + File.separator + "img" +File.separator;
        private  String mNetworkCachePath = mAppRootPath + File.separator + "netcache" +File.separator;

        public Builder appRootPath(String rootPath){
            mAppRootPath = rootPath;
            return this;
        }

        public Builder logPath(String logpath){
            mLogPath = logpath;
            return this;
        }

        public Builder imgPath(String imgpath){
            mImgPath = imgpath;
            return this;
        }

        public Builder NetworkCachePath(String networkcachepath){
            mNetworkCachePath = networkcachepath;
            return this;
        }

        public FileConfiguration builder(){
            return  new FileConfiguration(this);
        }
    }
}
