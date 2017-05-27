package com.example.he.myim.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by je on 27/05/17.
 */

public class AppManager {

    private AppManager(){}

    private static List<Activity> mActivityList = new ArrayList<>();

    public static void add(Activity activity){
        mActivityList.add(activity);
    }

    public static void remove(Activity activity){
        if (activity != null) {
            mActivityList.remove(activity);
        }
    }

    public static void finishAll(){
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }
}
