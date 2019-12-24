package com.lockwood.pagerdemo

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lockwood.pagerdemo.fragment.MessageFragment
import kotlinx.android.synthetic.main.activity_pager.*

class PagerActivity : BaseNavigationActivity(R.layout.activity_pager) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
    }

    override fun onBackPressed() {
        if (!navigationHistory.isEmpty()) {
            // Use false to transition immediately
            // before 2.0.4 was navigationHistory.onBackPressed()
            pager.setCurrentItem(navigationHistory.popLastSelected(), false)
        } else if (pager.currentItem != HOME_NAVIGATION_ITEM) {
            // return to default/home item
            pager.setCurrentItem(HOME_NAVIGATION_ITEM, false)
        } else {
            super.onBackPressed()
        }
    }

    private fun initViewPager() = with(pager) {
        adapter = (object : FragmentPagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = navigation.menu.size()
            override fun getItem(p0: Int): Fragment = MessageFragment.newInstance(p0)
        })
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        })
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

}
