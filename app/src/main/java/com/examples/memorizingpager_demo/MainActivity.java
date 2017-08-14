package com.examples.memorizingpager_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.coloredpanda.memorizingpager.MemorizingPager;
import com.coloredpanda.memorizingpager.NavigationManager;

public class MainActivity extends AppCompatActivity implements
    BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

  private static final String NAV_HISTORY = "nav_history";

  private MemorizingPager mViewPager;
  private BottomNavigationView mBottomNavigationView;

  private NavigationManager mNavigationHistory;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mViewPager = (MemorizingPager) findViewById(R.id.vp_content);
    mViewPager.setAdapter(new MessagePagerAdapter(getSupportFragmentManager()));
    mViewPager.addOnPageChangeListener(this);

    mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
    mBottomNavigationView.setOnNavigationItemSelectedListener(this);

    if (savedInstanceState != null) {
      restoreInstanceState(savedInstanceState);
    } else {
      initNavigationHistory();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(NAV_HISTORY, mNavigationHistory);
  }

  private void restoreInstanceState(Bundle savedInstanceState) {
    mNavigationHistory = savedInstanceState.getParcelable(NAV_HISTORY);
    if (mNavigationHistory != null && !mNavigationHistory.isEmpty()) {
      mViewPager.setNavigationManager(mNavigationHistory);
      mViewPager.setCurrentItem(mNavigationHistory.getLastSelectedItem(), false);
    } else {
      initNavigationHistory();
    }
  }

  private void initNavigationHistory() {
    mNavigationHistory = mViewPager.getNavigationManager();
    //  Or
    //  mNavigationHistory = new NavigationManager(mViewPager.getAdapter().getCount());
    //  mViewPager.setNavigationManager(mNavigationHistory);
  }

  @Override
  public void onBackPressed() {
    // If navigation history is not empty then set the last item like a current
    if (!mNavigationHistory.isEmpty()) {
      mViewPager.setCurrentItem(mNavigationHistory.removeLastSelectedItem(), false);
    }
    // If navigation history is empty and current item is not first then set it
    else if (mViewPager.getCurrentItem() != 0) {
      mViewPager.setCurrentItem(0, false);
    }
    // If navigation history is empty and current item is first then call super.onBackPressed();
    else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    // If current item does not equal a new then set it and return true
    // In other words: don't call setCurrentItem item if it already set
    if (mBottomNavigationView.getSelectedItemId() != item.getItemId()) {
      mViewPager.setCurrentItem(item.getOrder());
      return true;
    } else {
      // Otherwise return false
      return false;
    }
  }

  @Override
  public void onPageSelected(int position) {
    // Get selected item by position of ViewPager and set checked on it
    // Page of ViewPager already set and remains only highlight item of BottomNavigationView
    mBottomNavigationView.getMenu().getItem(position).setChecked(true);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

}