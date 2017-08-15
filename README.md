# memorizing-pager

[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/license-Apache%202-red.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/badge/Download-v.1.0.1-blue.svg)](https://github.com/kekc42/memorizing-pager#download)

<img src="https://raw.githubusercontent.com/kekc42/memorizing-pager/master/graphics/demo.gif" width="30%" />

### What?
Extended ViewPager which with BottomNavigationView connection allows navigating in the opposite direction when calling onBackPressed in Activity. For more information see [the wiki][1].

### Download
Download the [latest JAR][2] or grab via Gradle:

```
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```
```
dependencies {
	compile 'com.github.kekc42:memorizing-pager:1.0.1'
}
```
or Maven:
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://www.jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
	<groupId>com.github.kekc42</groupId>
	<artifactId>memorizing-pager</artifactId>
	<version>1.0.1</version>
</dependency>
```
## License

```
Copyright (C) 2017 Ivan Zinovyev (https://github.com/kekc42)

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
[1]: https://github.com/kekc42/memorizing-pager/wiki
[2]: https://github.com/kekc42/memorizing-pager/releases/download/1.0.1/memorizingpager-sources.jar