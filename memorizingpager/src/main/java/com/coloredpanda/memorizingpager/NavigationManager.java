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
 * NavigationManager it class that allows you to store called items by {@link
 * android.support.v4.view.ViewPager} and navigate in the opposite direction.
 *
 * @author Ivan Zinovyev
 * @version 1.0
 */

public final class NavigationManager implements Parcelable {

  private static final Creator<NavigationManager> CREATOR = new Creator<NavigationManager>() {
    @Override
    public NavigationManager createFromParcel(Parcel in) {
      return new NavigationManager(in);
    }

    @Override
    public NavigationManager[] newArray(int size) {
      return new NavigationManager[size];
    }
  };

  private Deque<Integer> mSelectedPages;
  private int mPushedCount;
  private int mBackupItemId;
  private boolean mFirstSelect;

  /**
   * Constructs an empty manager with an initial capacity of zero.
   */

  public NavigationManager() {
    init(0);
  }

  /**
   * Constructs a manager with the specified initial capacity.
   *
   * @param initialCapacity the initial capacity of the manager
   */

  public NavigationManager(int initialCapacity) {
    init(initialCapacity);
  }

  private NavigationManager(Parcel in) {
    int size = in.readInt();
    int[] array = new int[size];
    in.readIntArray(array);
    mSelectedPages = intArrayToDeque(array, size);

    mPushedCount = in.readInt();
    mBackupItemId = in.readInt();
    mFirstSelect = in.readByte() != 0;
  }

  private void init(int numElements) {
    mSelectedPages = new ArrayDeque<>(numElements);
    mPushedCount = 0;
    mBackupItemId = 0;
    mFirstSelect = false;
  }

  final void pushItem(int item) {
    if (mSelectedPages.contains(item)) {
      mSelectedPages.remove(item);
    }

    if (mBackupItemId != 0) {
      mSelectedPages.push(mBackupItemId);
      mBackupItemId = 0;
    }

    if (!mSelectedPages.contains(item)) {
      mSelectedPages.push(item);
    }

    mPushedCount++;
    mFirstSelect = mPushedCount == 1;
    mPushedCount = mSelectedPages.size();
  }

  /**
   * Removes and returns the first item of this manager.
   *
   * @return the item at the front of this manager
   */
  public final int removeLastSelectedItem() {
    if (mFirstSelect) {
      mSelectedPages.clear();
      return 0;
    }

    if (mSelectedPages.size() == mPushedCount) {
      mSelectedPages.pop();
    }

    mPushedCount--;
    if (!mSelectedPages.isEmpty()) {
      mBackupItemId = mSelectedPages.peek();
      return mSelectedPages.pop();
    } else {
      mBackupItemId = 0;
      return 0;
    }
  }

  /**
   * Returns {@code true} if this manager contains no elements.
   *
   * @return {@code true} if this manager contains no elements
   */
  public final boolean isEmpty() {
    return mSelectedPages == null || mSelectedPages.isEmpty();
  }

  /**
   * Retrieves, but does not remove, the head of the manager, or returns {@code null} if this
   * manager is empty.
   *
   * @return the head item of the manager, or {@code null} if this manager is empty
   */
  public final int getLastSelectedItem() {
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
    int size = mSelectedPages.size();
    parcel.writeInt(size);
    int[] array = dequeToIntArray(mSelectedPages, size);
    parcel.writeIntArray(array);

    parcel.writeInt(mPushedCount);
    parcel.writeInt(mBackupItemId);
    parcel.writeByte((byte) (mFirstSelect ? 1 : 0));
  }

  private int[] dequeToIntArray(Deque<Integer> deque, int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = deque.pollLast();
    }
    return array;
  }

  private Deque<Integer> intArrayToDeque(int[] array, int size) {
    Deque<Integer> deque = new ArrayDeque<>(size);
    for (int i = 0; i < size; i++) {
      deque.push(array[i]);
    }
    return deque;
  }
}
