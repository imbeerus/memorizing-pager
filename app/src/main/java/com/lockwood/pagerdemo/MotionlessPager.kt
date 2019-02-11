package com.lockwood.pagerdemo

import android.annotation.SuppressLint
import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.Scroller

class MotionlessPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    init {
        try {
            val viewpager = ViewPager::class.java
            val scrollerField = viewpager.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField.set(this, object : Scroller(context) {
                override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
                    super.startScroll(startX, startY, dx, 0)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    companion object {
        private val TAG = "MotionlessPager"
    }
}