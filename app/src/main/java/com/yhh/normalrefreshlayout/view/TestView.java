package com.yhh.normalrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by yx on 2017/2/7.
 */

public class TestView extends ViewGroup {

    private int mLayoutContentHeight;
    private Scroller mLayoutScroller;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutScroller = new Scroller(context);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mLayoutContentHeight = 0;
        for (int j = 0; j < getChildCount(); j++){
            View child = getChildAt(j);
            child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
            mLayoutContentHeight += child.getMeasuredHeight();
        }
    }

    private int mLastMoveY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:
                int dy = mLastMoveY - y;
                Log.e("Test",dy+"");
                scrollBy(0, dy);
                break;
            case MotionEvent.ACTION_UP:
                mLayoutScroller.startScroll(0, 500, 0, -500,650);
                invalidate();
                break;
        }
        mLastMoveY = y;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0;i < getChildCount();i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }
}
