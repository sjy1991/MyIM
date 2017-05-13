package com.example.he.myim.module.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseActivity;

public class AddFriendActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ImageView mIv_nodata;
    private RecyclerView mRv_add;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serach, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.contact_serach:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
