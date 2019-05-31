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
