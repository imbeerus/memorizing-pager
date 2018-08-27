package com.lockwood.memorizingpager_demo

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.lockwood.memorizingpager.NavigationHistory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationHistory: NavigationHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationHistory = if (savedInstanceState == null) {
            NavigationHistory()
        } else {
//            savedInstanceState.getParcelable<Parcelable>(NAV_HISTORY_EXTRA) as NavigationHistory
            NavigationHistory()
        }
        initViewPager()
        initBottomNavigation()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = with(item) {
        return if (isItemSelected(itemId)) {
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
            pager.setCurrentItem(navigationHistory.onBackPressed(), false)
        } else if (pager.currentItem != 0) {
            pager.setCurrentItem(0, false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPageSelected(itemOrder: Int) {
        navigation.menu.getItem(itemOrder).isChecked = true
    }

    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    private fun initViewPager() {
        pager.adapter = (object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return navigation.menu.size()
            }

            override fun getItem(p0: Int): Fragment {
                return MessageFragment.newInstance(p0)
            }
        })
        pager.addOnPageChangeListener(this)
    }

    private fun initBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun isItemSelected(itemId: Int): Boolean = navigation.selectedItemId != itemId
}
