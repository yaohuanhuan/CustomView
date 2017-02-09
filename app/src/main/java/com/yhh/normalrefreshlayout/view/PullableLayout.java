package com.yhh.normalrefreshlayout.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.yhh.normalrefreshlayout.R;

/**
 * Created by yx on 2017/2/7.
 */

public class PullableLayout extends ViewGroup {

    private View mHeader;
    private View mFooter;
    private int mLayoutContentHeight;
    private int mLastMoveY;
    private int mLastY;
    //滑动有效距离
    private int effectiveScrollY = 300;
    //文字提示
    private TextView tvPullHeader;

    private Scroller mLayoutScroller;

    public PullableLayout(Context context) {
        super(context);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeader = LayoutInflater.from(context).inflate(R.layout.header,null);
        mFooter = LayoutInflater.from(context).inflate(R.layout.footer,null);
        tvPullHeader = (TextView) mHeader.findViewById(R.id.tv_header);
        mLayoutScroller = new Scroller(context);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (canChildScrollUp()){
                    intercept = false;
                }else {
                    intercept = true;
                }
                break;

        }
        return intercept;
    }

    protected boolean canChildScrollUp() {
        View targetView = getChildAt(1);
        if (Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastMoveY - y;
                if(dy < 0) {
                    if(Math.abs(getScrollY()) <= mHeader.getMeasuredHeight()) {
                        scrollBy(0, dy);
                        if(Math.abs(getScrollY()) >= effectiveScrollY){
                            tvPullHeader.setText("松开刷新"+getScrollY());
                        }else{
                            tvPullHeader.setText("下来刷新"+getScrollY());
                        }
                    }
                }else {
                    scrollBy(0, dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(getScrollY()) >= effectiveScrollY){
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() ),400);
                    tvPullHeader.setText("刷新啦");
                }else{
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY(),400);
                    tvPullHeader.setText("距离不够");
                }
                break;
        }

        mLastMoveY = y;
        mLastY = (int) event.getY();
        return true;

    }
    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mLayoutScroller.computeScrollOffset()) {
            scrollTo(0, mLayoutScroller.getCurrY());
            invalidate();
        }
    }
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mLayoutContentHeight = 0;
        // 置位
        for (int j = 0; j < getChildCount(); j++){
            View child = getChildAt(j);
            if (child == mHeader) { // 头视图隐藏在顶端
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == mFooter) { // 尾视图隐藏在layout所有内容视图之后
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
            } else { // 内容视图根据定义(插入)顺序,按由上到下的顺序在垂直方向进行排列
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                mLayoutContentHeight += child.getMeasuredHeight();
            }
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        mHeader.setLayoutParams(params);
        mFooter.setLayoutParams(params);
        addView(mHeader);
        addView(mFooter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0;i < getChildCount();i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }
}
