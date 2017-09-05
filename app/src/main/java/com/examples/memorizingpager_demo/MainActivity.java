package com.examples.memorizingpager_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.coloredpanda.memorizingpager.BottomNavigationViewHelper;
import com.coloredpanda.memorizingpager.MemorizingPager;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private MemorizingPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (MemorizingPager) findViewById(R.id.vp_content);
        mViewPager.setAdapter(new MessagePagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(this);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    @Override
    public void onBackPressed() {
        // If navigation history is not empty then set the last item like a current
        if (!mViewPager.isNavigationHistoryEmpty()) {
            mViewPager.setCurrentItem(mViewPager.removeLastSelectedItem(), false);
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