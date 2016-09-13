package com.example.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * Created by ysnow on 2015/4/20.
 */
public class PageOne extends ScrollView {
    private int height;
    public float oldY;
    private int t;
    private float oldX;

    public PageOne(Context context) {
        super(context);
        init(context, null, 0);

    }

    public PageOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public PageOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageOne);
        int padding = a.getInteger(R.styleable.PageOne_pageone_padding, 0);
        height = UiUtils.getScreenHeight(context) - UiUtils.getStatusBarHeight(context) - UiUtils.dp2px(context, padding);
        a.recycle();


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            /** 说明:
             *滑动的时�?,计算gap,用来判断是向上滑动还是向下滑�?
             */
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float gap = Y - oldY;
                float X = ev.getX();
                float gapHorizontal = X - oldX;

                /** 说明:
                 *如果是横向移�?,就让父控件重新获得触摸事�?
                 */
                if (Math.abs(gapHorizontal) > 120) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }


                //得到scrollview里面空间的高�?
                int childHeight = this.getChildAt(0).getMeasuredHeight();
                //子控件高度减去scrollview向上滑动的距�?
                int padding = childHeight - t;
                //gap<0表示手指正在向上滑动，padding==mScreenHeight表示本scrollview已经滑动到了底部
                if (gap < 0 && padding == height) {
                    //让顶级的scrollview重新获得滑动事件
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            /** 说明:
             *第一�?:执行ACTION_DOWN方法,在这个方法里获取触摸事件,让顶级ScrollView失去触摸事件;
             * 同时,记录手指按下的时候的Y�?
             */
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                oldX = ev.getX();
                break;
            /** 说明:
             *第三�?:滑动完成手指抬起的时�?,让顶级ScrollView失去触摸事件
             */
            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //t表示本scrollview向上滑动的距�?
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }


}
