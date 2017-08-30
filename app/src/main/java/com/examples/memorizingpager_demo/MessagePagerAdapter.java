package com.examples.memorizingpager_demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MessagePagerAdapter extends FragmentPagerAdapter {

  private static final int NUM_PAGES = 4;

  MessagePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return MessageFragment.newInstance(position);
  }

  @Override
  public int getCount() {
    return NUM_PAGES;
  }

}
