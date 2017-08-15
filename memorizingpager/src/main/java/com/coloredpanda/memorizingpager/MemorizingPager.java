package com.coloredpanda.memorizingpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class MemorizingPager extends MotionlessPager {

  private NavigationManager mNavigationManager;

  public MemorizingPager(Context context) {
    super(context);
    init(context, null);
  }

  public MemorizingPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
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

}
