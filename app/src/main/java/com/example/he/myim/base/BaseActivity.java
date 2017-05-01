package com.example.he.myim.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.he.myim.module.splash.Constant;
import com.example.he.myim.utils.ToastUtils;

/**
 * 抽取页面基类,所有页面继承
 * Created by he on 17-4-29.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mSharedPreferences = getSharedPreferences(Constant.SP_NAEM, MODE_PRIVATE);
    }

    public void startActivity(Class clazz, boolean isFinish){
        if (isFinish) {
            startActivity(new Intent(this, clazz));
            finish();
        }else {
            startActivity(new Intent(this, clazz));
        }
    }

    public void showProgressDialog(String msg){
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void hideProgressDialog(){
        mProgressDialog.hide();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

    public void showToast(String msg){
        ToastUtils.showToast(this, msg);
    }

    public void saveUser(String userName, String pwd){
        mSharedPreferences.edit().putString(Constant.USER_NAME, userName).commit();
        mSharedPreferences.edit().putString(Constant.PWD, pwd).commit();
    }

    public String getUserName(){
        return mSharedPreferences.getString(Constant.USER_NAME, "");
    }

    public String getPwd(){
        return mSharedPreferences.getString(Constant.PWD, "");
    }

}
