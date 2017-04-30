package com.example.he.myim.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * 抽取页面基类,所有页面继承
 * Created by he on 17-4-29.
 */

public class BaseActivity extends AppCompatActivity {

    public void startActivity(Class clazz, boolean isFinish){
        if (isFinish) {
            startActivity(new Intent(this, clazz));
            finish();
        }else {
            startActivity(new Intent(this, clazz));
        }
    }
}
