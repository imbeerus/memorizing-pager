package com.examples.memorizingpager_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.coloredpanda.memorizingpager.BottomNavigationViewHelper;
import com.coloredpanda.memorizingpager.NavigationHistory;

public class MainActivityExtra extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String NAV_HISTORY_EXTRA = "nav_history";

    /*
     * Remember that when you use ViewPager you can swipe also.
     * Implementation of memorization using swiping not planned for this library.
     * If you want to disable swiping in your ViewPager you can inherit MotionlessPager.
     */
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    private NavigationHistory mNavigationHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationHistory = savedInstanceState == null ?
                new NavigationHistory() : (NavigationHistory) savedInstanceState.getParcelable(NAV_HISTORY_EXTRA);

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.setAdapter(new MessagePagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(this);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NAV_HISTORY_EXTRA, mNavigationHistory);
    }

    @Override
    public void onBackPressed() {
        if (!mNavigationHistory.isEmpty()) {
            // Use false to transition immediately
            mViewPager.setCurrentItem(mNavigationHistory.popLastSelectedItem(), false);
        } else if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0, false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (mBottomNavigationView.getSelectedItemId() != item.getItemId()) {
            int itemId = item.getOrder();
            mViewPager.setCurrentItem(itemId, false);
            mNavigationHistory.pushItem(itemId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}