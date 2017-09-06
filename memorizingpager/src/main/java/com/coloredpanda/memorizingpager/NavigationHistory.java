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
package com.coloredpanda.memorizingpager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * NavigationHistory it class that allows to store called items by {@link
 * android.support.v4.view.ViewPager} and get these items in the opposite direction.
 *
 * @author Ivan Zinovyev
 * @version 1.2
 */

public final class NavigationHistory implements Parcelable {

    private static final int MAX_BOTTOM_DESTINATIONS = 5;

    static final Creator<NavigationHistory> CREATOR = new Creator<NavigationHistory>() {
        @Override
        public NavigationHistory createFromParcel(Parcel in) {
            return new NavigationHistory(in);
        }

        @Override
        public NavigationHistory[] newArray(int size) {
            return new NavigationHistory[size];
        }
    };

    private Deque<Integer> mSelectedPages;
    private int mPushedCount;
    private int mBackupItemId;
    private boolean mFirstSelect;

    public NavigationHistory() {
        mSelectedPages = new ArrayDeque<>(MAX_BOTTOM_DESTINATIONS);
    }

    private NavigationHistory(Parcel in) {
        mSelectedPages = (ArrayDeque<Integer>) in.readSerializable();
        mPushedCount = in.readInt();
        mBackupItemId = in.readInt();
        mFirstSelect = in.readByte() != 0;
    }

    /**
     * Pushes an item onto the stack represented by this deque. In other words, inserts the item at the front of this deque.
     *
     * @param item the item to push
     */
    public void pushItem(int item) {
        if (mSelectedPages.contains(item)) mSelectedPages.remove(item);

        if (mBackupItemId != 0) {
            mSelectedPages.push(mBackupItemId);
            mBackupItemId = 0;
        }

        if (!mSelectedPages.contains(item)) mSelectedPages.push(item);

        mPushedCount++;
        mFirstSelect = mPushedCount == 1;
        mPushedCount = mSelectedPages.size();
    }

    /**
     * Removes and returns the first item of navigation history.
     *
     * @return the item at the front of navigation history
     */
    public int popLastSelectedItem() {
        if (mFirstSelect) {
            mSelectedPages.clear();
            mPushedCount = 0;
            return 0;
        }

        if (mSelectedPages.size() == mPushedCount) mSelectedPages.pop();

        if (!mSelectedPages.isEmpty()) {
            mPushedCount--;
            mBackupItemId = mSelectedPages.peek();
            return mSelectedPages.pop();
        } else {
            mPushedCount = 0;
            mBackupItemId = 0;
            return 0;
        }
    }

    /**
     * Returns {@code true} if navigation history contains no items.
     *
     * @return {@code true} if navigation history contains no items
     */
    public boolean isEmpty() {
        return mSelectedPages == null || mSelectedPages.isEmpty();
    }

    /**
     * Retrieves, but does not remove, the head of the navigation history, or returns {@code null}
     * if this history is empty.
     *
     * @return the head item of the manager, or {@code null} if this manager is empty
     */
    public int peekLastSelectedItem() {
        return mSelectedPages.peek();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled
     * representation.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable
     * object instance. Value is either 0 or {@link Parcelable#CONTENTS_FILE_DESCRIPTOR}.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param parcel The Parcel in which the object should be written.
     * @param f      Additional flags about how the object should be written. May be 0 or {@link
     *               Parcelable#PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel parcel, int f) {
        parcel.writeSerializable((ArrayDeque<Integer>) mSelectedPages);
        parcel.writeInt(mPushedCount);
        parcel.writeInt(mBackupItemId);
        parcel.writeByte((byte) (mFirstSelect ? 1 : 0));
    }
}
