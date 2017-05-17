package com.example.he.myim.module.home;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.he.myim.evenbus.User;
import com.example.he.myim.utils.DbUtil;

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
                        for (int i = 0; i < list.size(); i++) {
                            AVUser avUser = list.get(i);
                            Date createdAt = avUser.getCreatedAt();
                            String format = sdf.format(createdAt);
                            userList.add(new User(avUser.getUsername(), format));

                        }
                        final List<String> strings = DbUtil.queryContacts(username);

                        mAddFriendView.onQueryResult(userList, true, null, strings);

                    }
                });
    }
}
