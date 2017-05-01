package com.example.he.myim.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by he on 17-4-30.
 */

public class ToastUtils {

    private static Toast sToast;

    public static void showToast(Context context, String msg){
        if(sToast == null){
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
