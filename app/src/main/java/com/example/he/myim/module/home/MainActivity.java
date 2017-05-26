package com.example.he.myim.module.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.module.home.contact.AddFriendActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private static final String TAG = "main";
    private Toolbar mToolbar;
    private TextView tvTtiile;
    BottomNavigationBar bottomNavigationBar;
    FrameLayout flContent;
    private int[] titiles = new int[]{R.string.conversation, R.string.contact, R.string.plugin};
    private BadgeItem mBadgeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        unDataUnread();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        tvTtiile = (TextView) findViewById(R.id.tv_title);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        setSupportActionBar(mToolbar);
        tvTtiile.setText(titiles[0]);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initBottomNavigationBar();
    }

    private void initBottomNavigationBar() {

        BottomNavigationItem item = new BottomNavigationItem(R.drawable.conversation_selected_2,
                "消息");
        mBadgeItem = new BadgeItem();
        mBadgeItem.setBackgroundColor(Color.RED);
        mBadgeItem.setBorderColor(Color.WHITE);
        mBadgeItem.setText("5");
        mBadgeItem.setGravity(Gravity.RIGHT);
        mBadgeItem.show(true);
        item.setBadgeItem(mBadgeItem);

        bottomNavigationBar
                .addItem(item)
                .addItem(new BottomNavigationItem(R.drawable.contact_selected_2, "联系人"))
                .addItem(new BottomNavigationItem(R.drawable.plugin_selected_2, "动态"))
                .setActiveColor(R.color.login_normal)
                .setInActiveColor(R.color.in_active)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        initFristFragment();
    }

    private void initFristFragment() {
        /**
         * 解决Activity生命周期导致的重影问题
         */
        for (int i = 0; i < titiles.length; i++) {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentByTag(i + "");
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,FragmentFactory.getFragment(0), "0").commit();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage message){
        unDataUnread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                break;

            case R.id.add_freiend:
                // TODO: 17-5-1 跳转添加好友界面
                startActivity(AddFriendActivity.class, false);
                break;

            case  R.id.scan:
                showToast("分享好友");
                break;

            case R.id.about_me:
                showToast("关于我");
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        menuBuilder.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            ft.add(R.id.fl_content, fragment, String.valueOf(position));
        }
        tvTtiile.setText(titiles[position]);
        ft.show(fragment).commit();

    }

    @Override
    public void onTabUnselected(int position) {
        getSupportFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position)).commit();
    }

    @Override
    public void onTabReselected(int position) {

    }

    private void unDataUnread(){
        int unreadCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
        if (unreadCount > 99) {
            mBadgeItem.setText("99+");
            mBadgeItem.show(true);
        } else if (unreadCount > 0) {
            mBadgeItem.setText(unreadCount + "");
            mBadgeItem.show(true);
        }else {
            mBadgeItem.hide(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
