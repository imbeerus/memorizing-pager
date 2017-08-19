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

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * NavigationManager it class that allows you to store called items by {@link
 * android.support.v4.view.ViewPager} and get these items in the opposite direction.
 *
 * @author Ivan Zinovyev
 * @version 1.1
 */

final class NavigationHistory implements Parcelable {

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

  NavigationHistory() {}

  private NavigationHistory(Parcel in) {
    mSelectedPages = (ArrayDeque<Integer>) in.readSerializable();
    mPushedCount = in.readInt();
    mBackupItemId = in.readInt();
    mFirstSelect = in.readByte() != 0;
  }

  void handleAttributes(Context context, AttributeSet attrs) {
    int numElements = 0;
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(
          attrs,
          R.styleable.MemorizingPager,
          0, 0);
      try {
        numElements = a.getInteger(R.styleable.MemorizingPager_numPages, 0);
      } finally {
        a.recycle();
      }
    }
    mSelectedPages = new ArrayDeque<>(numElements);
  }

  final void pushItem(int item) {
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

  final int popLastSelectedItem() {
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

  final boolean isEmpty() {
    return mSelectedPages == null || mSelectedPages.isEmpty();
  }

  final int peekLastSelectedItem() {
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
   * @param f Additional flags about how the object should be written. May be 0 or {@link
   * Parcelable#PARCELABLE_WRITE_RETURN_VALUE}.
   */
  @Override
  public void writeToParcel(Parcel parcel, int f) {
    parcel.writeSerializable((ArrayDeque<Integer>) mSelectedPages);
    parcel.writeInt(mPushedCount);
    parcel.writeInt(mBackupItemId);
    parcel.writeByte((byte) (mFirstSelect ? 1 : 0));
  }
}
