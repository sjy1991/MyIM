package com.example.he.myim.module.home.conversation;

import com.example.he.myim.module.home.conversation.ConversationContract.ConversationPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by he on 17-5-23.
 */

public class ConversationPresenterImpl implements ConversationPresenter {
    ConversationContract.ConversationView mConversationView;
    List<EMConversation> mConversations = new ArrayList<>();

    public ConversationPresenterImpl(ConversationContract.ConversationView conversationView) {
        mConversationView = conversationView;
    }

    @Override
    public void initConverstaion() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager()
                .getAllConversations();
        Collection<EMConversation> values = allConversations.values();
        mConversations.clear();
        mConversations.addAll(values);
        mConversationView.onInitData(mConversations);
    }
}
