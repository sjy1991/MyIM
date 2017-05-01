package com.example.he.myim.module.login;

/**
 * Created by he on 17-4-29.
 */

public interface LoginContract {
    interface LoginView{
        void onLoginIn(String user, String pwd, boolean isSuccess, String msg);
    }

    interface LoginPresente{
        void loginIn(String userName, String pwd);
    }
}
