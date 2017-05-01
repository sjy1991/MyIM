package com.example.he.myim.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by he on 17-4-30.
 */

public class ThreadUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();


    public static void runSubThread(Runnable runnable){
        EXECUTOR_SERVICE.submit(runnable);
    }

    public static void runOnMainThread(Runnable runnable){
        HANDLER.post(runnable);
    }
}
