# `PrimeCalendar` :zap:

**`PrimeCalendar`** provides all [`java.util.Calendar`](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) functionalities for `Persian` and `Hijri` dates.
**`PrimeCalendar`** can be used in every **JVM-based** projects such as **Java/kotlin** applications, **Android** apps, etc.

This library contains three types of calendar systems as well as their conversion to each other.

  | Calendar System | Provided Class | Descriptions |
  | --- | --- | --- |
  |[Iranian](https://en.wikipedia.org/wiki/Iranian_calendars)| [PersianCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/persian/PersianCalendar.kt) | The most accurate solar calendar in use today. |
  |[Islamic](https://en.wikipedia.org/wiki/Islamic_calendar)| [HijriCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/hijri/HijriCalendar.kt) | A lunar calendar consisting of 12 lunar months in a year of 354 or 355 days. |
  |[Gregorian](https://en.wikipedia.org/wiki/Gregorian_calendar)| [CivilCalendar](https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/civil/CivilCalendar.kt) | The common calendar used in most of the world. |

![](static/prime_logo.png)
  
Download
--------
**`PrimeCalendar`** is available on bintray to download using build tools systems.

### • Gradle
Add the following lines to your `build.gradle` file:

```gradle
repositories {
    jcenter()
}
  
dependencies {
    implementation 'com.aminography:primecalendar:1.2.8'
}
```

### • Maven
Add the following lines to your `pom.xml` file:

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.aminography</groupId>
        <artifactId>primecalendar</artifactId>
        <version>1.2.8</version>
    </dependency>
</dependencies>
```

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
val calendar = PersianCalendar()
// or
val calendar = CalendarFactory.newInstance(CalendarType.PERSIAN)
```

### • Functionalities
Almost all of the standard `Calendar` functionalities are implemented in **`PrimeCalendar`** including `set`, `add`, `roll`, etc. To see list of methods and fields, refer to the [wiki](https://github.com/aminography/PrimeCalendar/wiki) page.

```kotlin
val civil = CivilCalendar()
civil.set(2019, 5, 17)
println(civil.longDateString)

civil.add(Calendar.DAY_OF_MONTH, 18)
println(civil.longDateString)

civil.add(Calendar.DAY_OF_WEEK, -3)
println(civil.longDateString)

----------------------
> Monday, 17 June 2019
> Friday, 5 July 2019
> Tuesday, 2 July 2019
```

### • Date Conversion
Conversion of dates to different types is possible by calling the converter methods.

> **Java**
```java
// Converting calendar instance to PersianCalendar:
PersianCalendar persian = calendar.toPersian();

// Converting calendar instance to HijriCalendar:
HijriCalendar hijri = calendar.toHijri();

// Converting calendar instance to CivilCalendar:
CivilCalendar civil = calendar.toCivil();
```

> **Kotlin**
```kotlin
// Converting calendar instance to PersianCalendar:
val persian = calendar.toPersian()

// Converting calendar instance to HijriCalendar:
val hijri = calendar.toHijri()

// Converting calendar instance to CivilCalendar:
val civil = calendar.toCivil()
```

### • Locale
When you're using Persian and Hijri calendars, you can localize digits, month names, and week day names by passing locale in constructor.

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
