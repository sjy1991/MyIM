package com.example.he.myim.module.home;

/**
 * Created by he on 17-5-2.
 */

public interface PluginContract {

    interface PluginView {
        void onLogout(String user, boolean isSuccess, String msg);
    }

    interface PluginPresenter {
        void lougou();
    }

}
