package com.aminography.primecalendar.sample

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.civil.CivilCalendarUtils
import com.aminography.primecalendar.common.CalendarFactory
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

    private fun weekOffset(dayOfWeek: Int, weekStartDay: Int): Int {
        val day = if (dayOfWeek < weekStartDay) dayOfWeek + 7 else dayOfWeek
        return (day - weekStartDay) % 7
    }

    private fun weekOfMonth(calendar: BaseCalendar): Int {
        val day = calendar.dayOfMonth
        var firstDayOfMonthDayOfWeek: Int
        CalendarFactory.newInstance(calendar.calendarType).apply {
            setDate(calendar.year, calendar.month, 1)
            firstDayOfMonthDayOfWeek = get(Calendar.DAY_OF_WEEK)
        }
        val offset = weekOffset(firstDayOfMonthDayOfWeek, calendar.weekStartDay)
        val dividend = (offset + day) / 7
        val remainder = (offset + day) % 7
        return dividend + if (remainder > 0) 1 else 0
    }

    @Test
    fun weekOfYear() {
        val calendar = CivilCalendar()
//        calendar.year = 1994
//        calendar.month = 7
//        calendar.dayOfMonth = 11

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")
        println("WEEK_OF_YEAR: ${calendar.weekOfYear}")
    }

    @Test
    fun weekOfMonth() {
        val calendar = CivilCalendar()
//        val calendar = PersianCalendar()
        calendar.month = 10
        calendar.dayOfMonth = 14

        println(calendar.longDateString)
        println("WEEK_OF_MONTH: ${calendar.get(Calendar.WEEK_OF_MONTH)}")
        println("WEEK_OF_MONTH: ${weekOfMonth(calendar)}")
        println("WEEK_OF_MONTH: ${calendar.weekOfMonth}")
    }

    @Test
    fun persianToCivilConversion() {
        val persian = PersianCalendar()
        persian.setDate(1370, 3, 15)
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
        persian.setDate(1370, 3, 15)
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
        print(calendar.longDateString)

        calendar.setDate(2019, 5, 1)
        print(calendar.longDateString)

        assertEquals(calendar.year, 2019)
        assertEquals(calendar.month, 5)
        assertEquals(calendar.dayOfMonth, 1)
    }

}
