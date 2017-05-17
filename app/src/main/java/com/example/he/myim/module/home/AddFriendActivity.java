package com.example.he.myim.module.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serach, menu);
        MenuItem item = menu.findItem(R.id.contact_serach);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mAddFriendPresenter.QueryContact(getUserName(), query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showToast(newText);
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
    public void onQueryResult(List<User> resutl, boolean success, String msg, List<String> contacts) {

        if (mAddFriendAdapter == null) {
            mAddFriendAdapter = new AddFriendAdapter(resutl, this, contacts);
            mRecyclerView.setAdapter(mAddFriendAdapter);
            mAddFriendAdapter.notifyDataSetChanged();

        }else {
            mAddFriendAdapter.clean();
            mAddFriendAdapter.setUserList(resutl);
            mAddFriendAdapter.setContactList(contacts);
            mAddFriendAdapter.notifyDataSetChanged();
        }
    }
}
