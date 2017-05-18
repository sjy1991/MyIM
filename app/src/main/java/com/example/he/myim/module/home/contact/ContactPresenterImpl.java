package com.example.he.myim.module.home.contact;

import com.example.he.myim.utils.DbUtil;
import com.example.he.myim.utils.ThreadUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by he on 17-5-4.
 */

public class ContactPresenterImpl implements ContactContract.ContactPresenter {
    ContactContract.ContactView mContactView;
    private List<String> contacts = new ArrayList<>();

    public ContactPresenterImpl(ContactContract.ContactView contactView) {
        mContactView = contactView;
    }

    @Override
    public void initContacts() {
        String currentUser = EMClient.getInstance().getCurrentUser();
        List<String> strings = DbUtil.getDbUtil().queryContacts(currentUser);
        contacts.clear();
        contacts.addAll(strings);
        mContactView.onInitContract(contacts);
        upDataContacts(currentUser);
    }


    /**
     * 更新服务器数据
     *
     * @param username
     */
    @Override
    public void upDataContacts(final String username) {
        //访问网环信拿数据
        ThreadUtils.runSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager()
                            .getAllContactsFromServer();
                    Collections.sort(allContactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                    // 更新本地数据缓存
                    DbUtil.updateContact(username, allContactsFromServer);
                    contacts.clear();
                    contacts.addAll(allContactsFromServer);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onUpdateContract(true, null);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onUpdateContract(false, e.getMessage());
                        }
                    });
                }

            }
        });
    }

    @Override
    public void deleteContact(final String contact, int position) {
        try {
            EMClient.getInstance().contactManager().deleteContact(contact);
            handlerDeleteContact(contact, true, null);
        } catch (final HyphenateException e) {
            e.printStackTrace();
            handlerDeleteContact(contact, false, e.getMessage());
        }
    }

    public void handlerDeleteContact(final String contact, final boolean isSuccess, final String
            msg) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    mContactView.onDeleteContact(contact, true, null);
                } else {
                    mContactView.onDeleteContact(contact, false, msg);
                }
            }
        });


    }
}
