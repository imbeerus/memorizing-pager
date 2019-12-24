package com.lockwood.pagerdemo

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lockwood.memorizingpager.NavigationHistory


abstract class BaseNavigationActivity(contentLayoutId: Int) :
    AppCompatActivity(contentLayoutId), BottomNavigationView.OnNavigationItemSelectedListener {

    protected lateinit var navigation: BottomNavigationView

    protected lateinit var navigationHistory: NavigationHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationHistory = if (savedInstanceState == null) {
            NavigationHistory()
        } else {
            savedInstanceState.getParcelable<Parcelable>(EXTRA_NAV_HISTORY) as NavigationHistory
        }
        // initBottomNavigation
        navigation = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_NAV_HISTORY, navigationHistory)
    }

    protected fun isItemSelected(itemId: Int): Boolean = navigation.selectedItemId == itemId

    companion object {

        const val HOME_NAVIGATION_ITEM = 0

        private const val EXTRA_NAV_HISTORY = "nav_history"
    }

}