# memorizing-pager

[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](https://img.shields.io/badge/license-Apache%202-red.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![](https://jitpack.io/v/lndmflngs/memorizing-pager.svg)](https://jitpack.io/#lndmflngs/memorizing-pager)

<img src="https://github.com/lndmflngs/memorizing-pager/blob/master/screenshots/ezgif.com-crop.gif?raw=true" width="45%" />

## What?
Lightweight library for BottomNavigationView which allows navigating in the opposite direction when calling onBackPressed in Activity

## Setup
1. You should add id's for you navigation menu items and android:orderInCategory ([navigation.xml][2])
2. Add [NavigationHistory][3] to you activity ([BaseNavigationActivity][4])
3. Setup NavigationHistory with you navigation type [ViewPager][5] | [ViewPager2][6] | [FrameLayout][7] 

## Download
Download the [latest release][1] AAR or grab via Gradle:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
    	implementation 'com.github.lndmflngs:memorizing-pager:2.0.5'
}
```
## Issue Tracking
Found a bug? Have an idea for an improvement? Feel free to [add an issue](../../issues)

## License

```
Copyright (C) 2018-2019 Ivan Zinovyev (https://github.com/lndmflngs)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
[1]: https://github.com/lndmflngs/memorizing-pager/releases/latest
[2]: https://github.com/lndmflngs/memorizing-pager/blob/master/app/src/main/res/menu/navigation.xml
[3]: https://github.com/lndmflngs/memorizing-pager/blob/master/memorizingpager/src/main/java/com/lockwood/memorizingpager/NavigationHistory.kt
[4]: https://github.com/lndmflngs/memorizing-pager/blob/master/app/src/main/java/com/lockwood/pagerdemo/BaseNavigationActivity.kt
[5]: https://github.com/lndmflngs/memorizing-pager/blob/master/app/src/main/java/com/lockwood/pagerdemo/PagerActivity.kt
[6]: https://github.com/lndmflngs/memorizing-pager/blob/master/app/src/main/java/com/lockwood/pagerdemo/PagerSecondActivity.kt
[7]: https://github.com/lndmflngs/memorizing-pager/blob/master/app/src/main/java/com/lockwood/pagerdemo/FrameActivity.kt
