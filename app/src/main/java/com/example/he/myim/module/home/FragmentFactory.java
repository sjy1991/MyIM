package com.example.he.myim.module.home;

import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.module.home.contact.ContactFragment;
import com.example.he.myim.module.home.conversation.ConversationFragment;
import com.example.he.myim.module.home.plugin.PluginFragment;

/**
 * Fragment工厂类
 * Created by he on 17-5-1.
 */

public class FragmentFactory {
    private static ContactFragment sContactFragment = null;
    private static ConversationFragment sConversationFragment = null;
    private static PluginFragment sPluginFragment = null;

    private FragmentFactory() {
    }


    /**
     * 根据需求生产订单
     *
     * @param position
     * @return
     */
    public static BaseFragment getFragment(int position) {
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                if (sConversationFragment == null) {
                    sConversationFragment = ConversationFragment.newInstance();
                }
                fragment = sConversationFragment;
                break;

            case 1:
                if (sContactFragment == null) {
                    sContactFragment = ContactFragment.newInstance();
                }
                fragment = sContactFragment;

                break;

            case 2:
                if (sPluginFragment == null) {
                    sPluginFragment = PluginFragment.newInstance();
                }
                fragment = sPluginFragment;

                break;

            default:
                break;

        }
        return fragment;
    }


}
