package com.lockwood.pagerdemo

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import com.lockwood.memorizingpager.NavigationHistory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var navigationHistory: NavigationHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationHistory = if (savedInstanceState == null) {
            NavigationHistory()
        } else {
            savedInstanceState.getParcelable<Parcelable>(EXTRA_NAV_HISTORY) as NavigationHistory
        }
        initViewPager()
        initBottomNavigation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_NAV_HISTORY, navigationHistory)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = with(item) {
        return if (!isItemSelected(itemId)) {
            pager.setCurrentItem(order, false)
            navigationHistory.pushItem(order)
            true
        } else {
            false
        }
    }

    override fun onBackPressed() {
        if (!navigationHistory.isEmpty()) {
            // Use false to transition immediately
            pager.setCurrentItem(navigationHistory.popLastSelected(), false)
        } else if (pager.currentItem != 0) {
            pager.setCurrentItem(0, false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPageSelected(itemOrder: Int) {
        navigation.menu.getItem(itemOrder).isChecked = true
    }

    override fun onPageScrollStateChanged(p0: Int) {}
    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    companion object {
        private const val EXTRA_NAV_HISTORY = "nav_history"
    }
}
