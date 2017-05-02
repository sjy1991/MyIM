package com.example.he.myim.module.interfaze;

import com.example.he.myim.utils.ThreadUtils;
import com.hyphenate.EMCallBack;

/**
 * Created by he on 17-5-2.
 */

public abstract class CallBackLisenter implements EMCallBack {
    public abstract void onMainSuccess();
    public abstract void onMainError(int code, String error);


    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess();
            }
        });
    }

    @Override
    public void onError(final int code, final String error) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainError(code, error);
            }
        });
    }

    @Override
    public void onProgress(int progress, String status) {

    }
}
