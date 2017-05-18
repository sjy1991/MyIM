package com.example.he.myim;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.avos.avoscloud.AVOSCloud;
import com.example.he.myim.evenbus.User;
import com.example.he.myim.utils.DbUtil;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

/**
 * Created by he on 17-4-28.
 */

public class MyApp extends Application {

    private static final String APP_ID = "qAx8oDYFdCYxIV4nabIAlsPf-gzGzoHsz";
    private static final String APP_KEY = "rLQEKA3vK4TbP2sXrM9edwpn";

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);

        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        DbUtil.init(this);

        initFriendsListener();
    }

    private void initFriendsListener() {

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                EventBus.getDefault().post(new User(username));
            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
                EventBus.getDefault().post(new User(username));
            }

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
            }

            @Override
            public void onFriendRequestAccepted(String username) {
                //好友请求被同意
                EventBus.getDefault().post(new User(username));
            }

            @Override
            public void onFriendRequestDeclined(String username) {
                //好友请求被拒绝
            }
        });
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)
                    (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
