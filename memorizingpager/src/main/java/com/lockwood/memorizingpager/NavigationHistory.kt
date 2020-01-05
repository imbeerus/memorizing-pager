/*
 * Copyright (C) 2018-2020 Ivan Zinovyev (https://github.com/lndmflngs)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lockwood.memorizingpager

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.util.*

class NavigationHistory() : Parcelable {

    private var selectedPages: Deque<Int> = ArrayDeque<Int>(MAX_BOTTOM_DESTINATIONS)
    private var isBackPressed = false

    val size: Int
        get() = selectedPages.size

    val lastSelectedItem: Int?
        get() = selectedPages.peek()

    constructor(parcel: Parcel) : this() {
        isBackPressed = parcel.readByte() != 0.toByte()
        selectedPages = parcel.readSerializable() as ArrayDeque<Int>
    }

    fun pushItem(item: Int) {
        // remove if already was selected, move it to front
        if (selectedPages.contains(item)) selectedPages.remove(item)
        selectedPages.push(item)
        isBackPressed = false
    }

    fun popLastSelected(): Int {
        return try {
            if (selectedPages.size == 1 && !isBackPressed) {
                selectedPages.clear()
                DEF_ITEM
            } else if (selectedPages.size >= 2 && !isBackPressed) {
                isBackPressed = true
                selectedPages.pop()
                selectedPages.pop()
            } else {
                selectedPages.pop()
            }
        } catch (e: NoSuchElementException) {
            Log.e(TAG, "${e.message} cause ${e.cause}")
            DEF_ITEM
        }
    }

    fun isEmpty() = selectedPages.isEmpty()

    override fun toString(): String {
        return selectedPages.toString()
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeByte((if (isBackPressed) 1 else 0).toByte())
        parcel.writeSerializable(selectedPages as ArrayDeque<Int>)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NavigationHistory> {

        private const val DEF_ITEM = 0

        private const val TAG = "NavigationHistory"

        private const val MAX_BOTTOM_DESTINATIONS = 5

        override fun createFromParcel(parcel: Parcel): NavigationHistory {
            return NavigationHistory(parcel)
        }

        override fun newArray(size: Int): Array<NavigationHistory?> {
            return arrayOfNulls(size)
        }
    }
}
