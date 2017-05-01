package com.example.he.myim.module.regist;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.he.myim.utils.ThreadUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by he on 17-4-30.
 */

public class RegistPresenterImpl implements RegistContract.RegistPresente {
    RegistContract.RegistView mRegistView;
    AVUser mUser;

    public RegistPresenterImpl(RegistContract.RegistView registView) {
        mRegistView = registView;
    }

    @Override
    public void registed(final String user, final String pwd) {
        // 在app服务器上注册
        mUser = null;
        mUser = new AVUser();


        mUser.setPassword(pwd);
        mUser.setUsername(user);
        mUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(final AVException e) {
                if (e == null) {
                    // 注册成功
                    //注册失败会抛出HyphenateException
                    ThreadUtils.runSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 环信服务器注册成功
                                EMClient.getInstance().createAccount(user, pwd);
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRegistView.onReginst(user, pwd, true, null);
                                    }
                                });

                            } catch (HyphenateException e1) {
                                // 环信服务器注册失败
                                mUser.deleteInBackground();
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRegistView.onReginst(user, pwd, false, e.getMessage());
                                    }
                                });

                            }
                        }
                    });

                } else {
                    // 注册失败
                    mRegistView.onReginst(user, pwd, false, e.getMessage());

                }
            }
        });
    }
}
