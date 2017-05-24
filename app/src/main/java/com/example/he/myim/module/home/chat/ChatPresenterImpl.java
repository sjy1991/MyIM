package com.example.he.myim.module.home.chat;

import com.example.he.myim.interfaze.CallBackLisenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by he on 17-5-19.
 */

public class ChatPresenterImpl implements ChatContract.ChatPresenter {

    ChatContract.ChatView mChatView;
    private List<EMMessage> mMsgRecords;

    public ChatPresenterImpl(ChatContract.ChatView chatView) {
        mChatView = chatView;
        mMsgRecords = new ArrayList<>();
    }


    @Override
    public void initChatRecord(String contact) {
        updata(contact);
        mChatView.onInitChatRecord(mMsgRecords);

    }

    private void updata(String contact) {
        // 访问环信拿20条记录
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(contact);


        if (conversation != null) {
            EMMessage lastMessage = conversation.getLastMessage();
            int count = 19;
            if (mMsgRecords.size() >= 19) {
                count = mMsgRecords.size();
            }
            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), count);
            mMsgRecords.clear();
            Collections.reverse(emMessages);
            mMsgRecords.add(lastMessage);
            mMsgRecords.addAll(emMessages);
            Collections.reverse(mMsgRecords);
            EventBus.getDefault().post(lastMessage);

        } else {
            mMsgRecords.clear();
        }
    }

    @Override
    public void upDateChat(String from) {
        updata(from);
        mChatView.onUpData(mMsgRecords.size());
    }

    @Override
    public void sendMessage(String msg, String username) {
        EMMessage emMessage = EMMessage.createTxtSendMessage(msg, username);
        emMessage.setStatus(EMMessage.Status.INPROGRESS);
        mMsgRecords.add(emMessage);
        mChatView.onUpData(mMsgRecords.size());
        emMessage.setMessageStatusCallback(new CallBackLisenter() {
            @Override
            public void onMainSuccess() {
                mChatView.onUpData(mMsgRecords.size());
            }

            @Override
            public void onMainError(int code, String error) {
                mChatView.onUpData(mMsgRecords.size());
            }
        });
        EMClient.getInstance().chatManager().sendMessage(emMessage);
    }
}
