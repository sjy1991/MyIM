package com.example.he.myim.module.Splash;

import com.hyphenate.chat.EMClient;

/**
 * Created by he on 17-4-29.
 */

public class SplashPresenterImpl implements SplashContract.SplashPresente {

    private SplashContract.SplashView mSplashView;

    public SplashPresenterImpl(SplashContract.SplashView splashView) {
        mSplashView = splashView;
    }


    @Override
    public void checkLogined() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient
                .getInstance().isConnected()) {
            /* 已经登录 */
            mSplashView.onCheckedLogin(true);
        }else {
            /* 还没登录 */
            mSplashView.onCheckedLogin(false);
        }
    }
}
