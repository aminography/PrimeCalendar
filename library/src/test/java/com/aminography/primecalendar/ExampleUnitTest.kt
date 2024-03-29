package com.aminography.primecalendar

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.operators.*
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.japanese.JapaneseCalendar
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
        println("DAY_OF_WEEK: ${calendar[Calendar.DAY_OF_WEEK]}")

        calendar.roll(Calendar.DAY_OF_WEEK, -1)

        println(calendar.longDateString)
        println("DAY_OF_WEEK: ${calendar[Calendar.DAY_OF_WEEK]}")
    }

    @Test
    fun persianRollDayOfYear() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 1
        println(calendar.longDateString)
        println("DAY_OF_YEAR: ${calendar[Calendar.DAY_OF_YEAR]}")

        calendar.roll(Calendar.DAY_OF_YEAR, -1)

        println(calendar.longDateString)
        println("DAY_OF_YEAR: ${calendar[Calendar.DAY_OF_YEAR]}")

        println(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH))
    }

    @Test
    fun persianRollWeekOfYear() {
        val calendar = PersianCalendar()
        calendar.month = 11
        calendar.dayOfMonth = 28
        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar[Calendar.WEEK_OF_YEAR]}")

        calendar.roll(Calendar.WEEK_OF_YEAR, 1)

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar[Calendar.WEEK_OF_YEAR]}")
    }

    @Test
    fun calendarRollWeekOfMonth() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 2)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val civil = CivilCalendar()
        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_MONTH: ${civil[Calendar.WEEK_OF_MONTH]}")

        calendar.roll(Calendar.WEEK_OF_MONTH, -1)

        civil.timeInMillis = calendar.timeInMillis
        println(civil.longDateString)
        println("WEEK_OF_MONTH: ${civil[Calendar.WEEK_OF_MONTH]}")
    }

    @Test
    fun persianRollWeekOfMonth() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 27
        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar[Calendar.WEEK_OF_MONTH]}")

        calendar.roll(Calendar.WEEK_OF_MONTH, 1)

        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar[Calendar.WEEK_OF_MONTH]}")
    }

    @Test
    fun persianRollDayOfWeekInMonth() {
        val calendar = PersianCalendar()
        calendar.month = 0
        calendar.dayOfMonth = 7
        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar[Calendar.DAY_OF_WEEK_IN_MONTH]}")

        calendar.roll(Calendar.DAY_OF_WEEK_IN_MONTH, -1)

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar[Calendar.DAY_OF_WEEK_IN_MONTH]}")
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
        println("DAY_OF_WEEK_IN_MONTH: ${calendar[Calendar.DAY_OF_WEEK_IN_MONTH]}")

        calendar[Calendar.DAY_OF_WEEK_IN_MONTH] = -2

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar[Calendar.DAY_OF_WEEK_IN_MONTH]}")
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
        println("WEEK_OF_YEAR: ${calendar[Calendar.WEEK_OF_YEAR]}")

        calendar[Calendar.WEEK_OF_YEAR] = -1
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar[Calendar.WEEK_OF_YEAR]}")
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

        calendar[Calendar.WEEK_OF_MONTH] = -1

        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar[Calendar.WEEK_OF_MONTH]}")
    }

    @Test
    fun persianToCivilConversion() {
        val persian = PersianCalendar()
//        persian.set(1370, 3, 15)
        persian[Calendar.YEAR] = 1370
        persian[Calendar.MONTH] = 3
        persian[Calendar.DAY_OF_MONTH] = 15
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
    fun japaneseToCivilConversion() {
        val japanese = JapaneseCalendar()
        japanese[Calendar.YEAR] = 2019
        japanese[Calendar.MONTH] = 8
        japanese[Calendar.DAY_OF_MONTH] = 17
        println("Japanese Date: ${japanese.longDateString}")

        val civil = japanese.toCivil()
        println("Civil Date: ${civil.longDateString}")

        assertEquals(civil.year, 2019)
        assertEquals(civil.month, 8)
        assertEquals(civil.dayOfMonth, 17)
    }

    @Test
    fun may31Test() {
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
    fun test() {
        val persian = PersianCalendar()
        println(persian.longDateString)

        HijriCalendar.dateAdjustingOffset = 1

        val hijri = HijriCalendar()
        println(hijri.longDateString)

        println("${persian < hijri}")
    }

    @Test
    fun operators() {
        val calendar = CivilCalendar()
        println(calendar.longDateString)
        calendar -= Month(5)
        println(calendar.longDateString)
//        println(calendar())

        val calendar2 = calendar + Year(2)
        println("${calendar < calendar2}")
    }

    @Test
    fun fields() {
        val calendar = PersianCalendar()
        calendar -= 107.dayOfMonth

        assertEquals(calendar.dayOfMonth, calendar.date)

        calendar.date = 9

        assertEquals(calendar.dayOfMonth, calendar.date)
    }

}
