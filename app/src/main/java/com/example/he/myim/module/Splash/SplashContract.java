package com.example.he.myim.module.Splash;

/**
 * Created by he on 17-4-29.
 */

public interface SplashContract {
    interface SplashView{
        void onCheckedLogin(boolean isLogined);

    }

    interface SplashPresente{
        void checkLogined();

    }
}
