package com.coloredpanda.memorizingpager;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

/**
 * MemorizingPager it class which with {@link BottomNavigationView} connection allows navigating in
 * the opposite direction when calling {@link Activity#onBackPressed()}.
 *
 * @version 1.0
 */

public class MemorizingPager extends MotionlessPager {

    private NavigationHistory mNavigationHistory = new NavigationHistory();

    public MemorizingPager(Context context) {
        super(context);
    }

    public MemorizingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set the currently selected page. Always {@code False} for smoothScroll to transition immediately.
     *
     * @param item         Item index to select
     * @param addToHistory {@code True} to add an item to selections history, {@code false} not to add
     */
    @Override
    public void setCurrentItem(int item, boolean addToHistory) {
        super.setCurrentItem(item, false);
        if (addToHistory) mNavigationHistory.pushItem(item);
    }

    /**
     * Set the currently selected page.
     *
     * @param item Item index to select
     * @see #setCurrentItem(int, boolean)
     */
    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.navigationHistory = mNavigationHistory;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        Parcelable superState = ss.getSuperState();
        super.onRestoreInstanceState(superState);
        mNavigationHistory = ss.navigationHistory;
    }

    /**
     * Returns {@code true} if navigation history contains no items.
     *
     * @return {@code true} if navigation history contains no items
     */
    public boolean isNavigationHistoryEmpty() {
        return mNavigationHistory.isEmpty();
    }

    /**
     * Removes and returns the first item of navigation history.
     *
     * @return the item at the front of navigation history
     */
    public int removeLastSelectedItem() {
        return mNavigationHistory.popLastSelectedItem();
    }

    /**
     * Retrieves, but does not remove, the head of the navigation history, or returns {@code null}
     * if this history is empty.
     *
     * @return the head item of the manager, or {@code null} if this manager is empty
     */
    public int getLastSelectedItem() {
        return mNavigationHistory.peekLastSelectedItem();
    }

    static class SavedState extends BaseSavedState {

        static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        NavigationHistory navigationHistory;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            navigationHistory = in.readParcelable(NavigationHistory.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeParcelable(navigationHistory, flags);
        }
    }
}