package com.example.he.myim.module.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.he.myim.MainActivity;
import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.example.he.myim.module.login.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {
    private ImageView iv_splash;
    private SplashContract.SplashPresente mPresente;
    private int DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        /**
         * 1. 检查是否登录
         * 2. 如果已登录就进入主页，
         * 3.否则闪屏2秒(渐变2秒)后进入登录页
         */

        mPresente = new SplashPresenterImpl(this);
        mPresente.checkLogined();
    }

    @Override
    public void onCheckedLogin(boolean isLogined) {
        if (isLogined) {
            //已登录就进入主页
            startActivity(MainActivity.class, true);
        } else {
            //闪屏2秒(渐变2秒)后进入登录页
            ObjectAnimator alpha = ObjectAnimator.ofFloat(iv_splash, "alpha", 0, 1).setDuration
                    (DURATION);
            alpha.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(LoginActivity.class, true);
                }
            });
            alpha.start();
        }
    }
}
