package com.example.he.myim.module.regist;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.example.he.myim.module.login.LoginActivity;
import com.example.he.myim.utils.StringUtils;

public class RegistActivity extends BaseActivity implements RegistContract.RegistView {
    ImageView ivAvatar;
    EditText etUserName;
    EditText etPwd;
    Button btnRegist;
    TextInputLayout til_user;
    TextInputLayout til_pwd;


    RegistContract.RegistPresente mRegistPresente;
    private TextView.OnEditorActionListener mL;

    private static final String TAG = "RegistActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_regist);
        init();
    }



    public void regist() {
        String user = etUserName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        // 数据验证
        if (StringUtils.checkUserName(user)) {
            til_user.setErrorEnabled(false);
        } else {
            til_user.setErrorEnabled(true);
            til_user.setError("用户名不合法");
            etUserName.requestFocus(View.FOCUS_RIGHT);
            return;
        }

        if (StringUtils.checkPwd(pwd)) {
            til_pwd.setErrorEnabled(false);
        } else {
            til_pwd.setErrorEnabled(true);
            til_pwd.setError("密码不合法");
            etPwd.requestFocus(View.FOCUS_RIGHT);
            return;
        }
        showProgressDialog("正在注册...");
        mRegistPresente.registed(user, pwd);

    }

    private void init() {
        mRegistPresente = new RegistPresenterImpl(this);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnRegist = (Button) findViewById(R.id.btn_regist);
        til_user = (TextInputLayout) findViewById(R.id.til_user);
        til_pwd = (TextInputLayout) findViewById(R.id.til_pwd);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

        // 绑定密码键盘完成事件
        mL = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (R.id.et_pwd == v.getId()) {
                    if (EditorInfo.IME_ACTION_DONE == actionId) {
                        regist();
                        return true;
                    }
                }
                return false;
            }
        };
        etPwd.setOnEditorActionListener(mL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mL != null) {
            etPwd.setOnClickListener(null);
        }
    }

    @Override
    public void onReginst(String userName, String pwd, boolean isSuccess, String msg) {
        hideProgressDialog();
        if (isSuccess) {
            // 保存数据
            saveUser(userName, pwd);
            showToast("注册成功");
            startActivity(LoginActivity.class, true);

        }else {
            // 提示失败
            showToast("注册失败:" + msg);
            // has already
            if (msg.contains("has alread")) {
                showToast("用户名已存在!请换一个");
            }

        }
    }
}
