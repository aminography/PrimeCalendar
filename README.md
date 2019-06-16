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
    implementation 'com.aminography:primecalendar:1.2.7'
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
        <version>1.2.7</version>
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

### • Locale


### • Functionalities
A

| Return Type | Method Signature |
| --- | --- |
| void | **add**(int field, int amount) |
| boolean | **after**(Object whenCalendar) |
| boolean | **before**(Object whenCalendar) |
| void | **clear**() |
| void | **clear**(int field) |
| void | **clone**() |
| int | **compareTo**(PrimeCalendar anotherCalendar) |
| boolean | **equals**(Object object) |
| int | **get**(int field) |
| int | **getActualMaximum**(int field) |
| int | **getActualMinimum**(int field) |
| String | **getDisplayName**(int field, int style, Locale locale) |
| Map<String, Integer> | **getDisplayNames**(int field, int style, Locale locale) |
| int | **getFirstDayOfWeek**() |
| int | **getGreatestMinimum**(int field) |
| int | **getLeastMaximum**(int field) |
| int | **getMaximum**(int field) |
| int | **getMinimum**(int field) |
| Date | **getTime**() |
| long | **getTimeInMillis**() |
| TimeZone | **getTimeZone**() |
| int | **hashCode**() |
| boolean | **isSet**(int field) |
| void | **roll**(int field, boolean up) |
| void | **roll**(int field, int amount) |
| void | **set**(int field, int value) |
| void | **set**(int year, int month, int dayOfMonth) |
| void | **set**(int year, int month, int dayOfMonth, int hourOfDay, int minute) |
| void | **set**(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) |
| void | **setFirstDayOfWeek**(int value) |
| void | **setTime**(Date date) |
| void | **setTimeInMillis**(long millis) |
| void | **setTimeZone**(TimeZone value) |
| String | **toString**() |

<table>

  <tr>
    <td><b>Method Signature</b></td>
    <td><b>Return Type</b></td>
  </tr>
  
  <tr>
    <td><b>• add</b> (int field, int amount)</td>
    <td>void</td>
  </tr>
  <tr>
    <td colspan="2"><i>Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules .</i></td>
  </tr>
  
  <tr>
    <td><b>• after</b> (Object whenCalendar)</td>
    <td>boolean</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns whether this Calendar represents a time after the time represented by the specified Object.</i></td>
  </tr>
  
  <tr>
    <td><b>• before</b> (Object whenCalendar)</td>
    <td>boolean</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns whether this Calendar represents a time before the time represented by the specified Object.</i></td>
  </tr>
  
  <tr>
    <td><b>• clear</b> ()</td>
    <td>void</td>
  </tr>
  <tr>
    <td colspan="2"><i>Sets all the calendar field values and the time value (millisecond offset from the Epoch) of this Calendar undefined.</i></td>
  </tr>
  
  <tr>
    <td><b>• clear</b> (int field)</td>
    <td>void</td>
  </tr>
  <tr>
    <td colspan="2"><i>Sets the given calendar field value and the time value (millisecond offset from the Epoch) of this Calendar undefined.</i></td>
  </tr>
  
  <tr>
    <td><b>• clone</b> ()</td>
    <td>void</td>
  </tr>
  <tr>
    <td colspan="2"><i>Creates and returns a copy of this object.</i></td>
  </tr>
  
  <tr>
    <td><b>• compareTo</b> (PrimeCalendar anotherCalendar)</td>
    <td>int</td>
  </tr>
  <tr>
    <td colspan="2"><i>Compares the time values (millisecond offsets from the Epoch) represented by two PrimeCalendar objects.</i></td>
  </tr>
  
  <tr>
    <td><b>• equals</b> (Object object)</td>
    <td>boolean</td>
  </tr>
  <tr>
    <td colspan="2"><i>Compares this Calendar to the specified Object.</i></td>
  </tr>
  
  <tr>
    <td><b>• get</b> (int field)</td>
    <td>int</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns the value of the given calendar field.</i></td>
  </tr>
  
  <tr>
    <td><b>• getActualMaximum</b> (int field)</td>
    <td>int</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns the maximum value that the specified calendar field could have, given the time value of this Calendar.</i></td>
  </tr>
  
  <tr>
    <td><b>• getActualMinimum</b> (int field)</td>
    <td>int</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns the minimum value that the specified calendar field could have, given the time value of this Calendar.</i></td>
  </tr>
  
  <tr>
    <td><b>• getDisplayName</b> (int field, int style, Locale locale)</td>
    <td>String</td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns the string representation of the calendar field value in the given style and locale.</i></td>
  </tr>
  
  <tr>
    <td><b>• getDisplayNames</b> (int field, int style, Locale locale)</td>
    <td>Map<String, Integer></td>
  </tr>
  <tr>
    <td colspan="2"><i>Returns a Map containing all names of the calendar field in the given style and locale and their corresponding field values.</i></td>
  </tr>
  
  <tr>
    <td><b>• getFirstDayOfWeek</b> ()</td>
    <td>int</td>
  </tr>
  <tr>
    <td colspan="2"><i>Gets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in France.</i></td>
  </tr>
  
  <tr>
    <td><b>• getGreatestMinimum</b> (int field)</td>
    <td>int</td>
   </tr>
  <tr>
    <td colspan="2"><i>Returns the highest minimum value for the given calendar field of this Calendar instance.</i></td>
  </tr>
  
  <tr>
    <td><b>• getLeastMaximum</b> (int field)</td>
    <td>int</td>
   </tr>
  <tr>
    <td colspan="2"><i>Returns the lowest maximum value for the given calendar field of this Calendar instance.</i></td>
  </tr>
  
  <tr>
    <td><b>• getMaximum</b> (int field)</td>
    <td>int</td>
   </tr>
  <tr>
    <td colspan="2"><i>Returns the maximum value for the given calendar field of this Calendar instance.</i></td>
  </tr>
  
  <tr>
    <td><b>• getMinimum</b> (int field)</td>
    <td>int</td>
   </tr>
  <tr>
    <td colspan="2"><i>Returns the minimum value for the given calendar field of this Calendar instance.</i></td>
  </tr>
  
  <tr>
    <td><b>• getTime</b> ()</td>
    <td>Date</td>
   </tr>
  <tr>
    <td colspan="2"><i>Returns a Date object representing this Calendar's time value (millisecond offset from the Epoch").</i></td>
  </tr>
  
</table>


```kotlin
val persian = PersianCalendar()
persian.set(1398, 2, 24)
println(persian.longDateString) // -> Friday, 24 Khordad 1398
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

[1]: https://github.com/aminography/PrimeCalendar/blob/master/library/src/main/java/com/aminography/primecalendar/persian/PersianCalendar.kt
