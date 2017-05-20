package com.example.he.myim.module.home.chat;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by he on 17-5-19.
 */

public interface ChatContract {
    interface ChatView{
        void onInitChatRecord(List<EMMessage> msgRecords);
    }
    interface ChatPresenter{
        void initChatRecord(String contact);
    }
}
