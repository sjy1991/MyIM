package com.example.he.myim.module.home;

import java.util.List;

/**
 * Created by he on 17-5-4.
 */

public interface ContactContract {
    interface ContactView{
        void onQueryContract(List<String> list);
    }

    interface ContactPresenter{
        List<String> queryContacts(String username);
    }
}
