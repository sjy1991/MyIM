package com.example.he.myim.module.home.plugin;

import com.example.he.myim.module.interfaze.CallBackLisenter;
import com.hyphenate.chat.EMClient;

/**
 * Created by he on 17-5-2.
 */

public class PluginPresenterImpl implements PluginContract.PluginPresenter {
    PluginContract.PluginView mPluginView;

    public PluginPresenterImpl(PluginContract.PluginView pluginView) {
        mPluginView = pluginView;
    }

    @Override
    public void lougou() {
        final String currentUser = EMClient.getInstance().getCurrentUser();
        EMClient.getInstance().logout(true, new CallBackLisenter() {
            @Override
            public void onMainSuccess() {
                mPluginView.onLogout(currentUser,true, null);
            }

            @Override
            public void onMainError(int code, String error) {
                mPluginView.onLogout(currentUser, false, error);
            }
        });
    }
}
