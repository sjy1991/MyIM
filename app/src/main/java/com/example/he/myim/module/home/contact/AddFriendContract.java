package com.example.he.myim.module.home.contact;

import com.example.he.myim.evenbus.User;

import java.util.List;

/**
 * Created by je on 16/05/17.
 */

public interface AddFriendContract {

    interface AddFriendView{
        void onQueryResult(List<User> resutl, boolean success, String msg, List<String> contacts);

        void onAddFriend(boolean success, String msg, String username);
    };

    interface AddFriendPresenter{
        void QueryContact(String username, String keyWord);

        void addContact(String username);
    }
}
