package com.lockwood.pagerdemo

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.lockwood.pagerdemo.fragment.MessageFragment

class FrameActivity : BaseNavigationActivity(R.layout.activity_frame) {

    private var selectedItem: Int = HOME_NAVIGATION_ITEM

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            showMessageFragment(HOME_NAVIGATION_ITEM)
        }
    }

    override fun onBackPressed() = with(navigationHistory) {
        if (!isEmpty()) {
            selectBottomNavigationItem(popLastSelected())
        } else {
            handleBackStackFragments()
        }
    }

    private fun handleBackStackFragments() {
        val tag = currentFragment?.tag
        val backStuckIsEmpty = supportFragmentManager.backStackEntryCount == 1
        val isStartFragment = tag == 0.toString()
        if (backStuckIsEmpty && isStartFragment) {
            finish()
        } else {
            val fragmentPopped = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (fragmentPopped) {
                checkBottomNavigationItem(tag?.toIntOrNull() ?: HOME_NAVIGATION_ITEM)
            } else {
                selectBottomNavigationItem(HOME_NAVIGATION_ITEM)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = with(item) {
        return selectNavigationItem(order)
    }

    private fun checkBottomNavigationItem(id: Int) {
        navigation.menu.getItem(id).isChecked = true
        selectedItem = id
    }

    private fun selectBottomNavigationItem(id: Int) {
        selectNavigationItem(id, false)
        checkBottomNavigationItem(id)
    }

    private fun selectNavigationItem(id: Int, isPush: Boolean = true): Boolean {
        return if (id == selectedItem) {
            // do nothing if already selected
            false
        } else {
            // update selected tem
            selectedItem = id
            if (isPush) {
                navigationHistory.pushItem(id)
            }
            when (id) {
                in HOME_NAVIGATION_ITEM..navigation.menu.size() -> {
                    showMessageFragment(id)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun showMessageFragment(id: Int) {
        val fragment = MessageFragment.newInstance(id)
        val tag = id.toString()
        replaceFragment(fragment, tag)
    }

    private fun replaceFragment(
        fragment: Fragment,
        tag: String
    ) {
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(tag, 0)
        if (!fragmentPopped) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, fragment, tag)
                addToBackStack(tag)
            }.commit()
        }
    }

    companion object {

    }

}
