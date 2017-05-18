package com.example.he.myim.module.home.contact;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;
import com.example.he.myim.evenbus.User;

import java.util.List;

public class AddFriendActivity extends BaseActivity implements AddFriendContract.AddFriendView {

    private Toolbar mToolbar;
    private ImageView mIv_nodata;
    private RecyclerView mRv_add;
    private SearchView mSearchView;
    private AddFriendPresenterImpl mAddFriendPresenter;
    private RecyclerView mRecyclerView;
    private AddFriendAdapter mAddFriendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mIv_nodata = (ImageView) findViewById(R.id.iv_nodata);
        mRv_add = (RecyclerView) findViewById(R.id.rv_friends);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddFriendPresenter = new AddFriendPresenterImpl(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_friends);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serach, menu);
        MenuItem item = menu.findItem(R.id.contact_serach);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setQueryHint("请输入搜索关键字！");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    showToast("请输入搜索关键字！");
                    return false;
                }
                mAddFriendPresenter.QueryContact(getUserName(), query);
                InputMethodManager tmm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                tmm.hideSoftInputFromWindow(mSearchView.getWindowToken(),0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.contact_serach:
                break;
        }
        return true;
    }

    @Override
    public void onQueryResult(List<User> resutl, boolean success, String msg, List<String>
            contacts) {

        if (success) {
            mIv_nodata.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mIv_nodata.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            return;
        }

        if (mAddFriendAdapter == null) {
            mAddFriendAdapter = new AddFriendAdapter(resutl, this, contacts);
            mRecyclerView.setAdapter(mAddFriendAdapter);
            mAddFriendAdapter.setAddFriendListener(new AddFriendAdapter.AddFriendListener() {
                @Override
                public void onAddFriendClick(String username) {
                    mAddFriendPresenter.addContact(username);
                }
            });

        } else {
            mAddFriendAdapter.clean();
            mAddFriendAdapter.setUserList(resutl);
            mAddFriendAdapter.setContactList(contacts);
            mAddFriendAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddFriend(boolean success, String msg, String username) {
        if (success) {
            showToast("已邀请" + username + "成为好基友/姬友!");
        } else {
            showToast("邀请失败:" + msg);
        }
    }
}
