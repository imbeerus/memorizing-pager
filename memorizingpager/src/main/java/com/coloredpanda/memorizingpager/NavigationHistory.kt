/*
 * Copyright (C) 2017 Ivan Zinovyev (https://github.com/kekc42)
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
package com.coloredpanda.memorizingpager

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayDeque
import java.util.Deque

/**
 * NavigationHistory it class that allows to store called items by [ ] and get these items in the opposite direction.
 *
 * @author Ivan Zinovyev
 * @version 1.2
 */

class NavigationHistory : Parcelable {

    private var mSelectedPages: Deque<Int>? = null
    private var mPushedCount: Int = 0
    private var mBackupItemId: Int = 0
    private var mFirstSelect: Boolean = false

    /**
     * Returns `true` if navigation history contains no items.
     *
     * @return `true` if navigation history contains no items
     */
    val isEmpty: Boolean
        get() = mSelectedPages == null || mSelectedPages!!.isEmpty()

    constructor() {
        mSelectedPages = ArrayDeque(MAX_BOTTOM_DESTINATIONS)
    }

    private constructor(`in`: Parcel) {
        mSelectedPages = `in`.readSerializable() as ArrayDeque<Int>
        mPushedCount = `in`.readInt()
        mBackupItemId = `in`.readInt()
        mFirstSelect = `in`.readByte().toInt() != 0
    }

    /**
     * Pushes an item onto the stack represented by this deque. In other words, inserts the item at the front of this deque.
     *
     * @param item the item to push
     */
    fun pushItem(item: Int) {
        if (mSelectedPages!!.contains(item)) mSelectedPages!!.remove(item)

        if (mBackupItemId != 0) {
            mSelectedPages!!.push(mBackupItemId)
            mBackupItemId = 0
        }

        if (!mSelectedPages!!.contains(item)) mSelectedPages!!.push(item)

        mPushedCount++
        mFirstSelect = mPushedCount == 1
        mPushedCount = mSelectedPages!!.size
    }

    /**
     * Removes and returns the first item of navigation history.
     *
     * @return the item at the front of navigation history
     */
    fun popLastSelectedItem(): Int {
        if (mFirstSelect) {
            mSelectedPages!!.clear()
            mPushedCount = 0
            return 0
        }

        if (mSelectedPages!!.size == mPushedCount) mSelectedPages!!.pop()

        return if (!mSelectedPages!!.isEmpty()) {
            mPushedCount--
            mBackupItemId = mSelectedPages!!.peek()
            mSelectedPages!!.pop()
        } else {
            mPushedCount = 0
            mBackupItemId = 0
            0
        }
    }

    /**
     * Retrieves, but does not remove, the head of the navigation history, or returns `null`
     * if this history is empty.
     *
     * @return the head item of the manager, or `null` if this manager is empty
     */
    fun peekLastSelectedItem(): Int {
        return if (!mSelectedPages!!.isEmpty()) {
            mSelectedPages!!.peek()
        } else {
            0
        }
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled
     * representation.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable
     * object instance. Value is either 0 or [Parcelable.CONTENTS_FILE_DESCRIPTOR].
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param parcel The Parcel in which the object should be written.
     * @param f      Additional flags about how the object should be written. May be 0 or [               ][Parcelable.PARCELABLE_WRITE_RETURN_VALUE].
     */
    override fun writeToParcel(parcel: Parcel, f: Int) {
        parcel.writeSerializable(mSelectedPages as ArrayDeque<Int>?)
        parcel.writeInt(mPushedCount)
        parcel.writeInt(mBackupItemId)
        parcel.writeByte((if (mFirstSelect) 1 else 0).toByte())
    }

    companion object {

        private val MAX_BOTTOM_DESTINATIONS = 5

        internal val CREATOR: Parcelable.Creator<NavigationHistory> = object : Parcelable.Creator<NavigationHistory> {
            override fun createFromParcel(`in`: Parcel): NavigationHistory {
                return NavigationHistory(`in`)
            }

            override fun newArray(size: Int): Array<NavigationHistory?> {
                return arrayOfNulls(size)
            }
        }
    }
}
