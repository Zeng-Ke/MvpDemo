package com.zk.mvp.mvpdemo.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public class ThreadExecutor {

    private static Executor asyncExecutor;
    private static MainExecutor mainExecutor;

    static {
        //线程池为无限大，但线程会复用，当执行第二个任务时第一个任务刚好完成，则会复用第一个线程
        asyncExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                return new Thread(r, "AsycExecutor");
            }
        });
        mainExecutor = new MainExecutor();
    }


    //异步
    public static void runInAsync(@NonNull Runnable runnable) {
        asyncExecutor.execute(runnable);
    }

    //延迟异步
    public static void runInAsync(@NonNull Runnable runnable, long delayTime) {
        asyncExecutor.execute(new DelayRunnable(delayTime, runnable));
    }


    public static void runInMain(@NonNull Runnable runnable) {
        mainExecutor.execute(runnable);
    }

    private static class DelayRunnable implements Runnable {

        private long delayTime;
        private Runnable runnable;

        public DelayRunnable(long delayTime, Runnable runnable) {
            this.delayTime = delayTime;
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (delayTime > 0) {
                try {
                    Thread.currentThread().sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            runnable.run();
        }
    }

}
