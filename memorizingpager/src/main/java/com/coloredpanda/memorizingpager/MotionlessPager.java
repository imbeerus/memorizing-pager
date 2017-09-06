package com.coloredpanda.memorizingpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * MotionlessPager it class that allows to disable paging by swiping of {@link ViewPager}
 *
 * @version 1.0
 */

public abstract class MotionlessPager extends ViewPager {

    private static final String TAG = "MotionlessPager";

    public MotionlessPager(Context context) {
        super(context);
        init();
    }

    public MotionlessPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scrollerField = viewpager.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, new MotionlessScroller(getContext()));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    private static final class MotionlessScroller extends Scroller {

        MotionlessScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 0);
        }

    }

}
