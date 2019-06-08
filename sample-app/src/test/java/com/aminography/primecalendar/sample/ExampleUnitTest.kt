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
        val calendar = CivilCalendar()
//        calendar.year = 2019
        calendar.month = 6
//        calendar.dayOfMonth = 1
//
//        calendar.set(Calendar.WEEK_OF_YEAR, 1)

//        val calendar = PersianCalendar()
//        calendar.year = 1398
//        calendar.month = 0
        calendar.dayOfMonth = 25

        println(calendar.longDateString)
        println("DAY_OF_WEEK_IN_MONTH: ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")

        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1)

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
        calendar.dayOfMonth = 31

        println(calendar.longDateString)
        println("WEEK_OF_YEAR: ${calendar.get(Calendar.WEEK_OF_YEAR)}")

        calendar.set(Calendar.WEEK_OF_YEAR, 1)
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

        calendar.set(Calendar.DAY_OF_YEAR, 365)

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

}
