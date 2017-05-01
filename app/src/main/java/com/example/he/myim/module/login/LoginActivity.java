package com.example.he.myim.module.login;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.he.myim.MainActivity;
import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.example.he.myim.module.regist.RegistActivity;
import com.example.he.myim.utils.StringUtils;


public class LoginActivity extends BaseActivity implements LoginContract.LoginView, View
        .OnClickListener {
    ImageView ivAvatar;
    EditText etUserName;
    EditText etPwd;
    Button btnLogin;
    TextView tvNewUser;
    TextInputLayout tilPwd;
    TextInputLayout tilUser;

    LoginContract.LoginPresente mLoginPresente;
    private static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        setContentView(R.layout.activity_login);
        init();
        mLoginPresente = new LoginPresenterImpl(this);
    }


    public void login() {
        String user = etUserName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        // 检查权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
                // 申请权限
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                return;
            }
        }


        // 数据验证
        if (StringUtils.checkUserName(user)) {
            tilUser.setErrorEnabled(false);
        } else {
            tilUser.setErrorEnabled(true);
            tilUser.setError("用户名不合法");
            etUserName.requestFocus(View.FOCUS_RIGHT);
            return;
        }

        if (StringUtils.checkPwd(pwd)) {
            tilPwd.setErrorEnabled(false);
        } else {
            tilPwd.setErrorEnabled(true);
            tilPwd.setError("密码不合法");
            etPwd.requestFocus(View.FOCUS_RIGHT);
            return;
        }
        showProgressDialog("正在拼命登录中...");
        mLoginPresente.loginIn(user, pwd);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && requestCode == REQUEST_CODE){
            if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED){
                // 被授权了
                login();
            }else {
                showToast("没有授权，不给你用了");
            }
        }
    }

    public void onRegist() {
        startActivity(RegistActivity.class, false);
    }

    @Override
    public void onLoginIn(String user, String pwd, boolean isSuccess, String msg) {
        hideProgressDialog();
        if (isSuccess) {
            // 保存数据
            saveUser(user, pwd);
            showToast("登录成功 ");
            startActivity(MainActivity.class, true);
        } else {
            showToast("登录失败:" + msg);
        }
    }

    private void init() {
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvNewUser = (TextView) findViewById(R.id.tv_new_user);
        tilUser = (TextInputLayout) findViewById(R.id.til_user);
        tilPwd = (TextInputLayout) findViewById(R.id.til_pwd);
        btnLogin.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);

        // 绑定密码键盘完成事件
        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (R.id.et_pwd == v.getId()) {
                    if (EditorInfo.IME_ACTION_DONE == actionId) {

                        login();
                        return true;
                    }
                }
                return false;
            }
        });
        setUserInfo();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setUserInfo();
    }

    private void setUserInfo() {
        etUserName.setText(getUserName());
        etPwd.setText(getPwd());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;

            case R.id.tv_new_user:
                onRegist();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etPwd.setOnEditorActionListener(null);
        tvNewUser.setOnClickListener(null);
        btnLogin.setOnClickListener(null);

    }


}
