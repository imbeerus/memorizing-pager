package com.lockwood.memorizingpager

import java.util.Deque
import java.util.ArrayDeque

class NavigationHistory {

    private var mSelectedPages: Deque<Int> = ArrayDeque<Int>(MAX_BOTTOM_DESTINATIONS)
    private var isBackPressed = false

    fun pushItem(item: Int) {
        // remove if already was selected, move it to front
        if (mSelectedPages.contains(item)) mSelectedPages.remove(item)
        mSelectedPages.push(item)
        isBackPressed = false
    }

    fun onBackPressed(): Int {
        return if (mSelectedPages.size == 1 && !isBackPressed) {
            mSelectedPages.clear()
            0
        } else if (mSelectedPages.size >= 2 && !isBackPressed) {
            isBackPressed = true
            mSelectedPages.pop()
            mSelectedPages.pop()
        } else {
            mSelectedPages.pop()
        }
    }

    fun isEmpty() = mSelectedPages.isEmpty()

    companion object {
        private const val MAX_BOTTOM_DESTINATIONS = 5
    }
}