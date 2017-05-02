package com.example.he.myim.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends BaseFragment {

    public static ConversationFragment newInstance(){
        return new ConversationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

}
