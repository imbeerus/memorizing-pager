package com.coloredpanda.memorizingpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller

import java.lang.reflect.Field

/**
 * MotionlessPager it class that allows to disable paging by swiping of [ViewPager]
 *
 * @version 1.0
 */

abstract class MotionlessPager : ViewPager {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        try {
            val viewpager = ViewPager::class.java
            val scrollerField = viewpager.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField.set(this, MotionlessScroller(context))
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    private class MotionlessScroller internal constructor(context: Context) : Scroller(context, DecelerateInterpolator()) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 0)
        }

    }

    companion object {

        private val TAG = "MotionlessPager"
    }

}
