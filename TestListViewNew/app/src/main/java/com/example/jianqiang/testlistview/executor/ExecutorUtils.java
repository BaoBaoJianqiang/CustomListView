package com.example.jianqiang.testlistview.executor;

/**
 * author pangchao
 * created on 2017/6/20
 * email fat_chao@163.com.
 */

public class ExecutorUtils {
    private volatile static SmartExecutor mSmartExecutor;

    public static SmartExecutor getDefaultExecutor() {
        if (mSmartExecutor == null) {
            synchronized (SmartExecutor.class) {
                if (mSmartExecutor == null) {
                    mSmartExecutor = new SmartExecutor();
                }
            }
        }
        return mSmartExecutor;
    }

    public static SmartExecutor getSpecificExecutor(int coreSize, int queueSize) {
        if (mSmartExecutor == null) {
            synchronized (SmartExecutor.class) {
                if (mSmartExecutor == null) {
                    mSmartExecutor = new SmartExecutor(coreSize, queueSize);
                }
            }
        }
        return mSmartExecutor;
    }

}
