package com.aminography.primecalendar.sample

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun roll() {
        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.MONTH, 0)
//        calendar.set(Calendar.DAY_OF_MONTH, 7)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 30)
        val civil = CivilCalendar()
        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")

//        calendar.roll(Calendar.WEEK_OF_YEAR, -1)
        calendar.roll(Calendar.WEEK_OF_YEAR, +1)

        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")
    }

    @Test
    fun persianRollDayOfWeek() {
        val calendar = PersianCalendar()
        calendar.dayOfMonth = 1
        println(calendar.longDateString)
        println("DAY_OF_WEEK: ${calendar.get(Calendar.DAY_OF_WEEK)}")

        calendar.roll(Calendar.DAY_OF_WEEK, -1)

        println(calendar.longDateString)
        println("DAY_OF_WEEK: ${calendar.get(Calendar.DAY_OF_WEEK)}")
    }

    @Test
    fun persianRollDayOfYear() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 1
        println(calendar.longDateString)
        println("DAY_OF_YEAR: ${calendar.get(Calendar.DAY_OF_YEAR)}")

        calendar.roll(Calendar.DAY_OF_YEAR, -1)

        println(calendar.longDateString)
        println("DAY_OF_YEAR: ${calendar.get(Calendar.DAY_OF_YEAR)}")
    }

    @Test
    fun persianRollWeekOfYear() {
        val calendar = PersianCalendar()
        calendar.month = 11
        calendar.dayOfMonth = 28
        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")

        calendar.roll(Calendar.WEEK_OF_YEAR, 1)

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")
    }

    @Test
    fun calendarRollWeekOfMonth() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 2)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val civil = CivilCalendar()
        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_MONTH: ${civil.get(Calendar.WEEK_OF_MONTH)}")

        calendar.roll(Calendar.WEEK_OF_MONTH, -1)

        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_MONTH: ${civil.get(Calendar.WEEK_OF_MONTH)}")
    }

    @Test
    fun persianRollWeekOfMonth() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 27
        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar.get(Calendar.WEEK_OF_MONTH)}")

        calendar.roll(Calendar.WEEK_OF_MONTH, 1)

        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar.get(Calendar.WEEK_OF_MONTH)}")
    }

    @Test
    fun persianRollDayOfWeekInMonth() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 7
        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")

        calendar.roll(Calendar.DAY_OF_WEEK_IN_MONTH, -1)

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")
    }

    @Test
    fun setWeekOfYear() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.WEEK_OF_YEAR, 1)
//        calendar.year = 1994
//        calendar.month = 7
//        calendar.dayOfMonth = 11

        val civil = CivilCalendar()
        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")
    }

    @Test
    fun dayOfWeekInMonth() {
//        val calendar = CivilCalendar()
//        calendar.year = 2019
//        calendar.month = 6
//        calendar.dayOfMonth = 1
//
//        calendar.set(Calendar.WEEK_OF_YEAR, 1)

        val calendar = PersianCalendar()
//        calendar.year = 1398
        calendar.month = 4
        calendar.dayOfMonth = 31

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")

        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -2)

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")
    }

    @Test
    fun weekOfYear() {
//        val calendar = CivilCalendar()
//        calendar.year = 2019
//        calendar.month = 5
//        calendar.dayOfMonth = 10
//
//        calendar.set(Calendar.WEEK_OF_YEAR, 1)

        val calendar = PersianCalendar()
        calendar.year = 1398
        calendar.month = 0
        calendar.dayOfMonth = 1

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")

        calendar.set(Calendar.WEEK_OF_YEAR, -1)
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")
    }

    @Test
    fun weekOfMonth() {
//        val calendar = CivilCalendar()
        val calendar = PersianCalendar()
//        calendar.month = 10
//        calendar.dayOfMonth = 14

//        calendar.add(Calendar.DAY_OF_YEAR, 1)
//        println(calendar.longDateString)
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

//        calendar.set(Calendar.DAY_OF_YEAR, 365)

        calendar.set(Calendar.WEEK_OF_MONTH, -1)

        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar.get(Calendar.WEEK_OF_MONTH)}")
    }

    @Test
    fun persianToCivilConversion() {
        val persian = PersianCalendar()
//        persian.set(1370, 3, 15)
        persian.set(Calendar.YEAR, 1370)
        persian.set(Calendar.MONTH, 3)
        persian.set(Calendar.DAY_OF_MONTH, 15)
        println("Persian Date: ${persian.longDateString}")

        val civil = persian.toCivil()
        println("Civil Date: ${civil.longDateString}")

        assertEquals(civil.year, 1991)
        assertEquals(civil.month, 6)
        assertEquals(civil.dayOfMonth, 6)
    }

    @Test
    fun persianToHijriConversion() {
        val persian = PersianCalendar()
        persian.set(1370, 3, 15)
        println("Persian Date: ${persian.longDateString}")

        val hijri = persian.toHijri()
        println("Hijri Date: ${hijri.longDateString}")

        assertEquals(hijri.year, 1411)
        assertEquals(hijri.month, 11)
        assertEquals(hijri.dayOfMonth, 23)
    }

    @Test
    fun May31Test() {
        val calendar = CivilCalendar()
        calendar.set(2019, 4, 31)
//        calendar.set(Calendar.YEAR, 2019)
//        calendar.set(Calendar.MONTH, 4)
//        calendar.set(Calendar.DAY_OF_MONTH, 31)
        println(calendar.longDateString)

        calendar.set(2019, 5, 1)
        println(calendar.longDateString)

        assertEquals(calendar.year, 2019)
        assertEquals(calendar.month, 5)
        assertEquals(calendar.dayOfMonth, 1)
    }

    @Test
    fun DayOfWeek() {
        val calendar = PersianCalendar()
        calendar.month = 0

        calendar.dayOfMonth = 1
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 2
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 3
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 4
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 5
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 6
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 7
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")
        calendar.dayOfMonth = 31
        println("${calendar.longDateString}  ->  ${calendar.get(Calendar.DAY_OF_WEEK)}  adj:${calendar.adjustDayOfWeekOffset(calendar.get(Calendar.DAY_OF_WEEK))}  wom:${calendar.get(Calendar.WEEK_OF_MONTH)}")

        println("ActualMaximum WOM: ${calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)}")
    }

}
