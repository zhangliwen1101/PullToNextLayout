package com.stone.verticalslide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;


public class CustListView extends ListView {
    private static final int MODE_IDLE = 0;
    private static final int MODE_HORIZONTAL = 1;
    private static final int MODE_VERTICAL = 2;

    private int scrollMode;
    private float downX, downY;

    boolean isAtTop = true; // 如果是true，则允许拖动至底部的下一�?
    private int mTouchSlop = 4; // 判定为滑动的阈�?�，单位是像�?

    public CustListView(Context arg0) {
        this(arg0, null);
    }

    public CustListView(Context arg0, AttributeSet arg1) {
        this(arg0, arg1, 0);
    }

    public CustListView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getRawX();
            downY = ev.getRawY();
            isAtTop = isAtTop();
            scrollMode = MODE_IDLE;
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (scrollMode == MODE_IDLE) {
                float xDistance = Math.abs(downX - ev.getRawX());
                float yDistance = Math.abs(downY - ev.getRawY());
                if (xDistance > yDistance && xDistance > mTouchSlop) {
                    scrollMode = MODE_HORIZONTAL;
                } else if (yDistance > xDistance && yDistance > mTouchSlop) {
                    scrollMode = MODE_VERTICAL;
                    if (downY < ev.getRawY() && isAtTop) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断listView是否在顶�?
     *
     * @return 是否在顶�?
     */
    public boolean isAtTop() {
        boolean resultValue = false;
        int childNum = getChildCount();
        if (childNum == 0) {
            // 没有child，肯定在顶部
            resultValue = true;
        } else {
            if (getFirstVisiblePosition() == 0) {
                // 根据第一个childView来判定是否在顶部
                View firstView = getChildAt(0);
                if (Math.abs(firstView.getTop()) < 2) {
                    resultValue = true;
                }
            }
        }

        return resultValue;
    }
}