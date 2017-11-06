package com.examples.memorizingpager_demo

import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.coloredpanda.memorizingpager.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    fun initViewPager() {
        pager.adapter = MessagePagerAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(this)
    }

    fun initNavigation() {
        navigation.setOnNavigationItemSelectedListener(this)
        BottomNavigationViewHelper.disableShiftMode(navigation)
    }

    fun isItemSelected(itemId: Int): Boolean = navigation.selectedItemId != itemId

    override fun onPageSelected(position: Int) {
        // Get selected item by position of ViewPager and set checked on it
        // Page of ViewPager already set and remains only highlight item of BottomNavigationView
        navigation.menu.getItem(position).isChecked = true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}