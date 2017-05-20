package com.example.he.myim.module.home.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 17-5-19.
 */

public class ChatPresenterImpl implements ChatContract.ChatPresenter {

    ChatContract.ChatView mChatView;
    private List<EMMessage> mMsgRecords;

    public ChatPresenterImpl(ChatContract.ChatView chatView) {
        mChatView = chatView;
    }


    @Override
    public void initChatRecord(String contact) {
        // 访问环信拿20条记录
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(contact);
        EMMessage lastMessage = conversation.getLastMessage();
        List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), 19);
        mMsgRecords = new ArrayList<>();
        mMsgRecords.clear();
        if (!emMessages.isEmpty()) {
            mMsgRecords.add(lastMessage);
            mMsgRecords.addAll(emMessages);

        }else {
            mMsgRecords.clear();
        }
        mChatView.onInitChatRecord(mMsgRecords);

    }
}
