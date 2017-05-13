package com.example.he.myim.module.home;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.evenbus.User;
import com.example.he.myim.utils.ToastUtils;
import com.example.he.myim.widget.ContactLayout;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements ContactContract.ContactView,
        SwipeRefreshLayout.OnRefreshListener,
        ContactAdapter.OnItemClickListener {
    private ContactLayout mContactLayout;
    private ContactAdapter mContactAdapter;
    ContactContract.ContactPresenter mContactPresenter;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentz
        mContactPresenter = new ContactPresenterImpl(this);
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mContactLayout = (ContactLayout) view.findViewById(R.id.contact_layout);
        mContactPresenter.initContacts();
        mContactLayout.setRefreshLayoutListener(this);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onInitContract(List<String> list) {

        if (mContactAdapter == null) {
            mContactAdapter = new ContactAdapter(list, getContext());
        }
        mContactAdapter.setOnItemClickListener(this);
        mContactLayout.setAdapter(mContactAdapter);
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateContract(boolean isSuccess, String msg) {

        if (isSuccess) {
            mContactAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showToast(getContext(), msg);
        }
        mContactLayout.onRefresh(false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(User user) {
        /* Do something */
        onRefresh();
    }


    @Override
    public void onRefresh() {
        mContactLayout.onRefresh(true);
        String currentUser = EMClient.getInstance().getCurrentUser();
        mContactPresenter.upDataContacts(currentUser);
    }

    @Override
    public void onItemLongClick(final String contact, final int position) {
        Snackbar.make(mContactLayout, "你确定要和" + contact + "友尽？", 2000)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContactPresenter.deleteContact(contact, position);
                    }
                }).show();
    }

    @Override
    public void onItemClick(String contact, int position) {

    }


    @Override
    public void onDeleteContact(String contact, boolean isSuccess, String msg) {
        if (isSuccess) {
            ToastUtils.showToast(getContext(), "和" + contact + "友尽了~~");
        }else {
            ToastUtils.showToast(getContext(), "网络不通，要不再续前缘?");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
