# `PrimeCalendar` :zap:
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PrimeCalendar-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7744)
![mavenCentral](https://img.shields.io/maven-central/v/com.aminography/primecalendar?color=blue)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7bba3b163d4444c4a8913f82386ad379)](https://www.codacy.com/manual/aminography/PrimeCalendar?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=aminography/PrimeCalendar&amp;utm_campaign=Badge_Grade)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

**`PrimeCalendar`** provides all the [`java.util.Calendar`](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) functionalities for `Persian`, `Hijri`, and `Japanese` dates.
**`PrimeCalendar`** can be used in every **JVM-based** projects such as **Java/kotlin** applications, **Android** apps, *etc*.  

This library contains three types of calendar systems as well as their conversion to each other.

  | Calendar System | Provider Class | Descriptions |
  | --- | --- | --- |
  |[Iranian](https://en.wikipedia.org/wiki/Iranian_calendars)| [PersianCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/persian/PersianCalendar.kt) | The most accurate solar calendar in use today. |
  |[Islamic](https://en.wikipedia.org/wiki/Islamic_calendar)| [HijriCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/hijri/HijriCalendar.kt) | A lunar calendar consisting of 12 lunar months in a year of 354 or 355 days. |
  |[Gregorian](https://en.wikipedia.org/wiki/Gregorian_calendar)| [CivilCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/civil/CivilCalendar.kt) | The common calendar which is used in most of the world. |
  |[Japanese](https://en.wikipedia.org/wiki/Japanese_calendar)| [JapaneseCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/japanese/JapaneseCalendar.kt) | The calendar which is used in Japan. |

![](static/prime_logo.png)
  
Download
--------
**`PrimeCalendar`** is available on `MavenCentral` to download using build tools systems.

### • Gradle
Add the following lines to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.aminography:primecalendar:1.7.0'
}
```

### • Maven
Add the following lines to your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>com.aminography</groupId>
        <artifactId>primecalendar</artifactId>
        <version>1.7.0</version>
    </dependency>
</dependencies>
```

<br/>

Usage
-----
Calendar objects can be instantiated by the class constructors or using [`CalendarFactory`](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/common/CalendarFactory.kt).

> **Java**
```java
PrimeCalendar calendar = new PersianCalendar();
// or
PrimeCalendar calendar = CalendarFactory.newInstance(CalendarType.PERSIAN);
```

> **Kotlin**
```kotlin
val calendar = HijriCalendar()
// or
val calendar = CalendarFactory.newInstance(CalendarType.HIJRI)
```

<br/>

### • Functionalities
Exactly all of the standard `Calendar` functionalities are implemented in **`PrimeCalendar`** including `set`, `add`, `roll`, *etc*.  
To see list of [**methods**](https://github.com/aminography/PrimeCalendar/wiki#methods) and [**fields**](https://github.com/aminography/PrimeCalendar/wiki#fields), refer to the [wiki page](https://github.com/aminography/PrimeCalendar/wiki).

```kotlin
val civil = CivilCalendar()
civil.set(2019, 5, 17)
println(civil.longDateString)

civil.set(Calendar.DAY_OF_YEAR, 192)
println(civil.longDateString)

civil.add(Calendar.WEEK_OF_YEAR, 14)
println(civil.longDateString)

civil.roll(Calendar.DAY_OF_WEEK, -3)
println(civil.longDateString)

---------------------------
> Monday, 17 June 2019
> Thursday, 11 July 2019
> Thursday, 17 October 2019
> Monday, 14 October 2019
```

<br/>

### • Date Conversion
Conversion of dates to each other is simply possible by calling the converter methods.

```kotlin
// Converting calendar instance to PersianCalendar:
val persian = calendar.toPersian()

// Converting calendar instance to HijriCalendar:
val hijri = calendar.toHijri()

// Converting calendar instance to CivilCalendar:
val civil = calendar.toCivil()

// Converting calendar instance to JapaneseCalendar:
val japanese = calendar.toJapanese()
```

<br/>

Also, it is possible to convert an instance of `java.util.Calendar` to an instance of `PrimeCalendar`. For example:
```kotlin
import java.util.Calendar

val calendar = Calendar.getInstance()

// Converting to PersianCalendar:
val persian = calendar.toPersian()
```

<br/>

### • Kotlin Operators
There is a different way to use `get`, `set`, and `add` methods. Using operators you can do it much simpler.
Suppose that the `calendar` is an instance of `PrimeCalendar`:

> get
```kotlin
val year = calendar.get(Calendar.YEAR)

// equivalent operations:
val year = calendar[Calendar.YEAR]
val year = calendar.year
```

> set
```kotlin
calendar.set(Calendar.MONTH, 7)

// equivalent operations:
calendar[Calendar.MONTH] = 7
calendar.set(Month(7))
calendar.set(7.month)
calendar.month = 7
```

> add
```kotlin
calendar.add(Calendar.DAY_OF_MONTH, 27)

// equivalent operations:
calendar[Calendar.DAY_OF_MONTH] += 27
calendar += DayOfMonth(27)
calendar += 27.dayOfMonth
```

<br/>

### • Localization
You can localize digits, month names, and week day names by passing locale in constructor. For Persian and Hijri calendars, the default locale is set to Farsi and Arabic respectively.

```kotlin
val persian = PersianCalendar()
println(persian.longDateString)

---------------------------
> پنج‌شنبه، ۲۳ خرداد ۱۳۹۸
```

```kotlin
val persian = PersianCalendar(Locale.ENGLISH)
println(persian.longDateString)

---------------------------
> Thursday, 23 Khordad 1398
```

<br/>

Third Party Libraries
---------------------
**• ThreeTen-Backport** (https://www.threeten.org/threetenbp)

<br/>

Change Log
----------
### Version 1.4.0
- Migrating to MavenCentral.

### Version 1.3.2
- Improving Arabic digits.

### Version 1.3.0
- Adding getter/setter field for all the calendar fields, such as dayOfWeek, hour, *etc*.
- Adding date conversion extension functions for `java.util.Calendar` instances.
- Adding calendar fields extensions for numbers, *e.g.* `calendar += 27.dayOfMonth`

### Version 1.2.21
- Japanese month names and other temporal names are changed.
- Month constants are added into calendar classes.

<br/>

License
--------
```
Copyright 2019 Mohammad Amin Hassani.

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
