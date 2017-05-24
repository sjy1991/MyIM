package com.example.he.myim.module.home.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class
   ChatActivity extends BaseActivity implements ChatContract.ChatView, View
        .OnClickListener{

    private Toolbar mToolbar;
    private TextView mTv_title;
    private EditText mEt_msg;
    private Button mBtn_send;
    private RecyclerView mRv_chat;
    private ChatAdapter mChatAdapter;
    private ChatContract.ChatPresenter mChatPresenter;
    private String mUsername;

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
        mUsername = intent.getStringExtra("username");
        mTv_title.setText("和"+ mUsername +"聊天中...");
        mRv_chat = (RecyclerView) findViewById(R.id.rv_chat);

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
        mChatPresenter = new ChatPresenterImpl(this);
        mChatPresenter.initChatRecord(mUsername);
        EventBus.getDefault().register(this);
        mBtn_send.setOnClickListener(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEven(List<EMMessage> messages){
        /**
         * 1.收到消息判断是不是自己收的
         * 2.如果是自己收的
         */
        String from = messages.get(0).getFrom();
        if (from.equals(mUsername)) {
            mChatPresenter.upDateChat(from);
        }




    }

    @Override
    public void onInitChatRecord(List<EMMessage> msgRecords) {
        /**
         * 初始化RecyclerView
         */
        mRv_chat.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(msgRecords);
        mRv_chat.setAdapter(mChatAdapter);
        if (msgRecords.size()!=0){
            mRv_chat.scrollToPosition(msgRecords.size()-1);
        }
    }

    @Override
    public void onUpData(int size) {
        mChatAdapter.notifyDataSetChanged();
        if (size != 0) {
            mRv_chat.scrollToPosition(size - 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        String msg = mEt_msg.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            mChatPresenter.sendMessage(msg, mUsername);
            mEt_msg.setText("");
        }
    }
}
