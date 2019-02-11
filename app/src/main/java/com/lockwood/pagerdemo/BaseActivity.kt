package com.lockwood.pagerdemo

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
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