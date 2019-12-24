package com.lockwood.pagerdemo

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.lockwood.pagerdemo.fragment.MessageFragment
import kotlinx.android.synthetic.main.activity_pager_2.*

class PagerSecondActivity : BaseNavigationActivity(R.layout.activity_pager_2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
    }

    override fun onBackPressed() {
        if (!navigationHistory.isEmpty()) {
            // Use false to transition immediately
            pager.setCurrentItem(navigationHistory.popLastSelected(), false)
        } else if (pager.currentItem != HOME_NAVIGATION_ITEM) {
            // return to default/home item
            pager.setCurrentItem(HOME_NAVIGATION_ITEM, false)
        } else {
            super.onBackPressed()
        }
    }

    private fun initViewPager() = with(pager) {
        adapter = object : FragmentStateAdapter(this@PagerSecondActivity) {
            override fun getItemCount(): Int = navigation.menu.size()
            override fun createFragment(p0: Int): Fragment = MessageFragment.newInstance(p0)
        }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigation.menu.getItem(position).isChecked = true
            }
        })
        // disable swipe
        isUserInputEnabled = false
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
