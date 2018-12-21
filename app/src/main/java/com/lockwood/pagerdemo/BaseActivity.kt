package com.lockwood.pagerdemo

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity(),
        ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    protected fun initViewPager() {
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

    protected fun initBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener(this)
    }

    protected fun isItemSelected(itemId: Int): Boolean = navigation.selectedItemId == itemId
}