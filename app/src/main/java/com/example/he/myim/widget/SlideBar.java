package com.example.he.myim.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.module.interfaze.IContactable;

import java.util.List;

/**
 * Created by he on 17-5-3.
 */

public class SlideBar extends View {

    private Paint mPaint;
    private static final String[] SECTIONS = {"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
            , "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mAvgWidth;
    private int mAvgHeight;
    private TextView mTvTitle;
    private RecyclerView mRecyclerView;

    public SlideBar(Context context) {
        super(context);
        init();
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext
                ().getResources().getDisplayMetrics()));

        mPaint.setColor(Color.parseColor("#9c9c9c"));


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mAvgWidth = width / 2;
        mAvgHeight = height / SECTIONS.length;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTvTitle == null) {
            ViewGroup parent = (ViewGroup) getParent();
            mTvTitle = (TextView) parent.findViewById(R.id.tv_first);
            mRecyclerView = (RecyclerView) parent.findViewById(R.id.contact_recyclerview);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1.显示背景色
                setBackgroundResource(R.drawable.slidebar_bg);
            case MotionEvent.ACTION_MOVE:

                int y = (int) event.getY();
                mTvTitle.setVisibility(View.VISIBLE);
                showTitleAndRecyclerView(y);

                break;

            case MotionEvent.ACTION_UP:
                mTvTitle.setVisibility(View.GONE);
                setBackgroundResource(android.R.color.transparent);
                break;

        }
        return true;
    }

    private void showTitleAndRecyclerView(int y) {
        int index = (y / mAvgHeight);
        if (index < 0) {
            index = 0;
        } else if (index > SECTIONS.length - 1) {
            index = SECTIONS.length - 1;
        }
        // 获取数据
        IContactable contactable = (IContactable) mRecyclerView.getAdapter();
        List<String> datas = contactable.getDatas();
        for (int i = 0; i < datas.size(); i++) {
            String s = datas.get(i);
            if (s.equals(SECTIONS[index])) {
                mRecyclerView.smoothScrollToPosition(i);
            }
        }
        mTvTitle.setText(SECTIONS[index]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i], mAvgWidth, mAvgHeight * (i + 1), mPaint);
        }
    }
}
