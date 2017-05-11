package com.example.he.myim.utils;

import android.text.TextUtils;

/**
 * Created by he on 17-4-30.
 */

public class StringUtils {
    public static boolean checkUserName(String userName) {
        if (TextUtils.isEmpty(userName)) {
            return false;
        }

        return userName.matches("^[a-zA-Z]\\w{2,19}$");
    }

    public static boolean checkPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches("^[0-9]{2,19}$");
    }


    public static String getInitial(String contact){
        if (TextUtils.isEmpty(contact)) {
            return null;
        }
        return contact.substring(0, 1).toUpperCase();
    }
}
