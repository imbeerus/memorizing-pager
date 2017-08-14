package com.coloredpanda.memorizingpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import java.lang.reflect.Field;

public class MemorizingPager extends ViewPager {

  private NavigationManager mNavigationManager;

  public MemorizingPager(Context context) {
    super(context);
    initViewPager(context, null);
  }

  public MemorizingPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    initViewPager(context, attrs);
  }

  private void initViewPager(Context context, AttributeSet attrs) {
    int numElements = 0;
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(
          attrs,
          R.styleable.MemorizingPager,
          0, 0);

      try {
        numElements = a.getInteger(R.styleable.MemorizingPager_numPages, 0);
      } finally {
        a.recycle();
      }
    }
    mNavigationManager = new NavigationManager(numElements);
    setScroller(new MotionlessScroller(getContext())); // Set Scroller with disabled swiping
  }

  private void setScroller(Scroller scroller) {
    try {
      Class<?> viewpager = ViewPager.class;
      Field scrollerField = viewpager.getDeclaredField("mScroller");
      scrollerField.setAccessible(true);
      scrollerField.set(this, scroller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setCurrentItem(int item, boolean addToHistory) {
    super.setCurrentItem(item, false); // Always disable smooth scroll
    if (addToHistory) mNavigationManager.pushItem(item);
  }

  @Override
  public void setCurrentItem(int item) {
    setCurrentItem(item, true); // Add selected item to history by default
  }

  public NavigationManager getNavigationManager() {
    return mNavigationManager;
  }

  public void setNavigationManager(NavigationManager navigationManager) {
    mNavigationManager = navigationManager;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return false;
  }

  private static final class MotionlessScroller extends Scroller {

    MotionlessScroller(Context context) {
      super(context, new DecelerateInterpolator());
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
      super.startScroll(startX, startY, dx, dy, 0);
    }

  }
}
