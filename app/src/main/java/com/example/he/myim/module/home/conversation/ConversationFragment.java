package com.example.he.myim.module.home.conversation;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.module.home.MainActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends BaseFragment implements ConversationContract.ConversationView, View.OnClickListener {



    private RecyclerView mRv_conversation;
    private ConversationAdapter mConversationAdapter;
    private ConversationContract.ConversationPresenter mConversationPresenter;
    private FloatingActionButton mFab;

    public static ConversationFragment newInstance(){
        return new ConversationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRv_conversation = (RecyclerView) view.findViewById(R.id.rv_conversation);
        mRv_conversation.setLayoutManager(new LinearLayoutManager(getContext()));
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mConversationPresenter = new ConversationPresenterImpl(this);
        mConversationPresenter.initConverstaion();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage messages){
        mConversationPresenter.initConverstaion();
    }

    @Override
    public void onInitData(List<EMConversation> conversations) {
        if (mConversationAdapter == null) {
            mConversationAdapter = new ConversationAdapter(conversations);
            mRv_conversation.setAdapter(mConversationAdapter);
        }else {
            mConversationAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mConversationAdapter = null;
        mFab.setOnClickListener(null);
        mFab = null;
    }

    @Override
    public void onClick(View v) {
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
        ObjectAnimator.ofFloat(mFab,"rotation", 0, 360).setDuration(2000).start();
        mConversationPresenter.initConverstaion();
        MainActivity activity = (MainActivity) getActivity();
        activity.upDataUnread();
    }
}
