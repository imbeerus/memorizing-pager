package com.coloredpanda.memorizingpager

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log

/**
 * BottomNavigationViewHelper it class that allows to disable [BottomNavigationView]
 * shift mode
 *
 * @version 1.0
 */

object BottomNavigationViewHelper {

    private val TAG = "BNVHelper"

    /**
     * Disable animation of [BottomNavigationView] when used more than three top-level destinations
     *
     * Remember, you'll need to execute this method each time you change menu items in your
     * [BottomNavigationView]
     *
     * @param view BottomNavigationView for which you want to disable shift mode
     */
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView

                item.setShiftingMode(false)
                // set once again checked value, so view will be updated

                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Unable to change value of shift mode", e)
        }

    }

}
