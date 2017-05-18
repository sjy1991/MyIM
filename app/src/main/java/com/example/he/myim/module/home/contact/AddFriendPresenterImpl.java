package com.example.he.myim.module.home.contact;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.he.myim.evenbus.User;
import com.example.he.myim.utils.DbUtil;
import com.example.he.myim.utils.ThreadUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by je on 16/05/17.
 */

public class AddFriendPresenterImpl implements AddFriendContract.AddFriendPresenter {

    AddFriendContract.AddFriendView mAddFriendView;

    private static final String TAG = "AddFriendPresenterImpl";

    public AddFriendPresenterImpl(AddFriendContract.AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
    }

    @Override
    public void QueryContact(final String username, final String keyWord) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        final List<User> userList = new ArrayList<>();

        userQuery.whereStartsWith("username", keyWord).whereNotEqualTo("username", username)
                .findInBackground(new FindCallback<AVUser>() {
                    @Override
                    public void done(List<AVUser> list, AVException e) {
                        if (e == null && list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                AVUser avUser = list.get(i);
                                Date createdAt = avUser.getCreatedAt();
                                String format = sdf.format(createdAt);
                                userList.add(new User(avUser.getUsername(), format));
                            }
                            final List<String> strings = DbUtil.queryContacts(username);
                            mAddFriendView.onQueryResult(userList, true, null, strings);

                        } else {
                            mAddFriendView.onQueryResult(null, false, null, null);
                        }
                    }
                });
    }

    @Override
    public void addContact(final String username) {
        // 调用环信添加用户
        ThreadUtils.runSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(username, "");
                    handlerAddFriend(true, null, username);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    handlerAddFriend(false, e.getMessage(), null);
                }
            }
        });
    }

    private void handlerAddFriend(final boolean success, final String msg, final String username) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mAddFriendView.onAddFriend(success, msg, username);
            }
        });
    }
}
