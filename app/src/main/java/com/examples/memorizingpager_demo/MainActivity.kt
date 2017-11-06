package com.examples.memorizingpager_demo

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.pager

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
        initNavigation()
    }

    override fun onBackPressed() {
        when {
        // If navigation history is not empty then set the last item like a current
            !pager.isNavigationHistoryEmpty -> pager.setCurrentItem(pager.removeLastSelectedItem(), false)
        // If navigation history is empty and current item is not first then set it
            pager.currentItem != 0 -> pager.setCurrentItem(0, false)
        // If navigation history is empty and current item is first then call super.onBackPressed();
            else -> super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // If current item does not equal a new then set it and return true
        // In other words: don't call setCurrentItem item if it already set
        return if (isItemSelected(item.itemId)) {
            pager.setCurrentItem(item.order, true)
//            pager.currentItem = item.order
            true
        } else {
            // Otherwise return false
            false
        }
    }

}