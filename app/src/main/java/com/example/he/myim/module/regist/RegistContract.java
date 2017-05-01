package com.example.he.myim.module.regist;

/**
 * Created by he on 17-4-29.
 */

public interface RegistContract {
    interface RegistView{
        void onReginst(String userName, String pwd, boolean isSuccess, String msg);
    }

    interface RegistPresente{
        void registed(String user, String pwd);
    }
}
