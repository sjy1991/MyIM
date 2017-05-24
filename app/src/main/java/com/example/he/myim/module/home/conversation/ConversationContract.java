package com.example.he.myim.module.home.conversation;

import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Created by he on 17-5-23.
 */

public interface ConversationContract {
    interface ConversationView{

        void onInitData(List<EMConversation> conversations);
    }

    interface ConversationPresenter{
        void initConverstaion();
    }


}
