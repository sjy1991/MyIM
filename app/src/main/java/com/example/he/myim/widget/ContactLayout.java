package com.example.he.myim.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.he.myim.R;

/**
 * Created by he on 17-5-3.
 */

public class ContactLayout extends RelativeLayout {

    private SlideBar mSlideBar;
    private TextView mTv_fitst;

    public ContactLayout(Context context) {
        this(context, null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_contact, this, true);
        mTv_fitst = (TextView) findViewById(R.id.tv_first);
        mSlideBar = (SlideBar) findViewById(R.id.slidebar);
    }

    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
