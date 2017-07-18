package com.zk.mvp.mvpdemo.thread;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public class MainExecutor implements Executor {

    private Handler mHandler;
    private Thread mainHandler;

    public MainExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
        this.mainHandler = Looper.getMainLooper().getThread();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        if (Thread.currentThread() == mainHandler)
            command.run();
        else mHandler.post(command);
    }
}
