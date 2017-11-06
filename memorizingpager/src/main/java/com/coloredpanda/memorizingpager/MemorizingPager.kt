package com.coloredpanda.memorizingpager

import android.app.Activity
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import android.view.View

/**
 * MemorizingPager it class which with [BottomNavigationView] connection allows navigating in
 * the opposite direction when calling [Activity.onBackPressed].
 *
 * @version 1.0
 */

class MemorizingPager : MotionlessPager {

    private var mNavigationHistory = NavigationHistory()

    /**
     * Returns `true` if navigation history contains no items.
     *
     * @return `true` if navigation history contains no items
     */
    val isNavigationHistoryEmpty: Boolean
        get() = mNavigationHistory.isEmpty

    /**
     * Retrieves, but does not remove, the head of the navigation history, or returns `null`
     * if this history is empty.
     *
     * @return the head item of the manager, or `null` if this manager is empty
     */
    val lastSelectedItem: Int
        get() = mNavigationHistory.peekLastSelectedItem()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * Set the currently selected page. Always `False` for smoothScroll to transition immediately.
     *
     * @param item         Item index to select
     * @param addToHistory `True` to add an item to selections history, `false` not to add
     */
    override fun setCurrentItem(item: Int, addToHistory: Boolean) {
        super.setCurrentItem(item, false)
        if (addToHistory) mNavigationHistory.pushItem(item)
    }

    /**
     * Set the currently selected page.
     *
     * @param item Item index to select
     * @see .setCurrentItem
     */
    override fun setCurrentItem(item: Int) {
        setCurrentItem(item, true)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.navigationHistory = mNavigationHistory
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        mNavigationHistory = ss.navigationHistory
    }

    /**
     * Removes and returns the first item of navigation history.
     *
     * @return the item at the front of navigation history
     */
    fun removeLastSelectedItem(): Int {
        return mNavigationHistory.popLastSelectedItem()
    }

    internal class SavedState : View.BaseSavedState {

        lateinit var navigationHistory: NavigationHistory

        constructor(superState: Parcelable) : super(superState)

        private constructor(`in`: Parcel) : super(`in`) {
            navigationHistory = `in`.readParcelable(NavigationHistory::class.java.classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(navigationHistory, flags)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}