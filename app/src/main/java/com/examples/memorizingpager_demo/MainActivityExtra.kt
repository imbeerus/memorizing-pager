package com.examples.memorizingpager_demo

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import com.coloredpanda.memorizingpager.NavigationHistory
import kotlinx.android.synthetic.main.activity_main.pager

class MainActivityExtra : BaseActivity() {

    /*
     * Remember that when you use ViewPager you can swipe also.
     * Implementation of memorization using swiping not planned for this library.
     * If you want to disable swiping in your ViewPager you can inherit MotionlessPager.
     */

    private lateinit var navigationHistory: NavigationHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationHistory = if (savedInstanceState == null) {
            NavigationHistory()
        } else {
            savedInstanceState.getParcelable<Parcelable>(NAV_HISTORY_EXTRA) as NavigationHistory
        }

        initViewPager()
        initNavigation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(NAV_HISTORY_EXTRA, navigationHistory)
    }

    override fun onBackPressed() {
        if (!navigationHistory.isEmpty) {
            // Use false to transition immediately
            pager.setCurrentItem(navigationHistory.popLastSelectedItem(), false)
        } else if (pager.currentItem != 0) {
            pager.setCurrentItem(0, false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return if (isItemSelected(item.itemId)) {
            pager.setCurrentItem(item.order, false)
            navigationHistory.pushItem(item.order)
            true
        } else {
            false
        }
    }

    companion object {

        private val NAV_HISTORY_EXTRA = "nav_history"
    }

}