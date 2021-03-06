package com.example.he.myim.module.home.contact;

import java.util.List;

/**
 * Created by he on 17-5-4.
 */

public interface ContactContract {
    interface ContactView{
        void onInitContract(List<String> contact);
        void onUpdateContract(boolean isSuccess, String msg);

        void onDeleteContact(String contact, boolean isSuccess, String msg);
    }

    interface ContactPresenter{
        void initContacts();
        void upDataContacts(String username);
        void deleteContact(String contact, int position);
    }

}
