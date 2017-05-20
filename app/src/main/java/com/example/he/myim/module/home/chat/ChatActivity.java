package com.example.he.myim.module.home.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;

public class ChatActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mTv_title;
    private EditText mEt_msg;
    private Button mBtn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTv_title = (TextView) findViewById(R.id .tv_title);
        mEt_msg = (EditText) findViewById(R.id.et_msg);
        mBtn_send = (Button) findViewById(R.id.btn_send);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        mTv_title.setText("和"+username+"聊天中...");
        String s = mEt_msg.getText().toString();
        if (TextUtils.isEmpty(s)) {
            mBtn_send.setEnabled(false);
        }else {
            mBtn_send.setEnabled(true);
        }
        mEt_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mBtn_send.setEnabled(false);
                }else {
                    mBtn_send.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
