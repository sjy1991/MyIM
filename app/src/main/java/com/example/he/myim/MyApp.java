package com.example.he.myim;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.NotificationCompat;

import com.avos.avoscloud.AVOSCloud;
import com.example.he.myim.base.AppManager;
import com.example.he.myim.evenbus.User;
import com.example.he.myim.module.home.MainActivity;
import com.example.he.myim.module.home.chat.ChatActivity;
import com.example.he.myim.module.home.chat.MessageListenerAdapter;
import com.example.he.myim.module.login.LoginActivity;
import com.example.he.myim.utils.DbUtil;
import com.example.he.myim.utils.ToastUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by he on 17-4-28.
 */

public class MyApp extends Application {

    private static final String APP_ID = "qAx8oDYFdCYxIV4nabIAlsPf-gzGzoHsz";
    private static final String APP_KEY = "rLQEKA3vK4TbP2sXrM9edwpn";
    private SoundPool mSoundPool;
    private int mDuan;
    private int mYulu;
    private boolean isRunBackground;
    private NotificationManager mNm;

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        int pid = android.os.Process.myPid();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);

        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        DbUtil.init(this);

        initSoundPool();
        initFriendsListener();
        initMsgListener();
        initConnectionListener();
    }

    /**
     * 监听连接状态
     */
    private void initConnectionListener() {
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int errorCode) {
                if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    ToastUtils.showToast(MyApp.this, "帐号在其他设备登录了");
                    // 清空所有activity

                    Intent intent = new Intent(MyApp.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    AppManager.finishAll();
                }
            }
        });
    }


    /**
     * 加载声音资源
     */
    private void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mDuan = mSoundPool.load(this, R.raw.duan, 1);
        mYulu = mSoundPool.load(this, R.raw.yulu, 1);

    }


    private void initMsgListener() {
        EMClient.getInstance().chatManager().addMessageListener(new MessageListenerAdapter() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                super.onMessageReceived(messages);

                EMMessage emMessage = messages.get(0);
                EventBus.getDefault().post(emMessage);
                String from = emMessage.getFrom();
                EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
                String message = body.getMessage();
                checkRunBackground();
                if (isRunBackground) {
                    if (mNm == null) {
                        mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApp.this);
                    builder.setContentTitle("你有新的消息");
                    builder.setContentInfo(from);
                    builder.setContentText(message);
                    builder.setSmallIcon(R.drawable.avatar_left);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable
                            .default_avatar));
                    builder.setAutoCancel(true);

                    Intent mainIntent = new Intent(MyApp.this, MainActivity.class);
                    Intent chatIntent = new Intent(MyApp.this, ChatActivity .class);
                    chatIntent.putExtra("username", from);
                    Intent[] intents = new Intent[2];
                    intents[0] = mainIntent;
                    intents[1] = chatIntent;
                    PendingIntent activities = PendingIntent.getActivities(MyApp.this, 1,
                            intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(activities);

                    mNm.notify(1, builder.build());
                }
            }
        });

    }

    private void checkRunBackground() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(100);

        ComponentName topActivity = runningTasks.get(0).topActivity;
        if (topActivity.getPackageName().equals(getPackageName())) {
            isRunBackground = false;
        } else {
            isRunBackground = true;
        }

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


}
