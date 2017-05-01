package com.example.he.myim.module.login;

import com.example.he.myim.utils.ThreadUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by he on 17-4-30.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresente {
    LoginContract.LoginView mLoginView;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void loginIn(final String userName, final String pwd) {
        // 登录环信服务器 回调运行在子线程
        EMClient.getInstance().login(userName, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.onLoginIn(userName, pwd, true, null);
                    }
                });
            }

            @Override
            public void onError(int code, final String error) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.onLoginIn(userName, pwd, false, error);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });

    }


}
