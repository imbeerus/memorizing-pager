package com.coloredpanda.memorizingpager;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * BottomNavigationViewHelper it class that allows to disable {@link BottomNavigationView}
 * shift mode
 *
 * @version 1.0
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BNVHelper";

    /**
     * Disable animation of {@link BottomNavigationView} when used more than three top-level destinations
     *
     * Remember, you'll need to execute this method each time you change menu items in your
     * {@link BottomNavigationView}
     *
     * @param view BottomNavigationView for which you want to disable shift mode
     */
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to change value of shift mode", e);
        }
    }

}
