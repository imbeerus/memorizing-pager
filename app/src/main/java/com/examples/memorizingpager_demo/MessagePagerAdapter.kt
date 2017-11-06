package com.examples.memorizingpager_demo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

internal class MessagePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = MessageFragment.newInstance(position)
    override fun getCount(): Int = NUM_PAGES

    companion object {

        private val NUM_PAGES = 4
    }

}