package com.aminography.primecalendar.sample

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.*

/**
 * Test the functionality of [CivilCalendar] vs [java.util.GregorianCalendar]
 */
class CivilCalendarUnitTest {

    @Test
    fun dayOfWeekInMonth() {
        val civil = CivilCalendar().apply {
            set(YEAR, 2019)
            set(MONTH, 9)
            set(DAY_OF_MONTH, 90)
            print(DAY_OF_WEEK_IN_MONTH)
        }

        val gregorian = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, 9)
            set(DAY_OF_MONTH, 90)
            print(DAY_OF_WEEK_IN_MONTH)
        }

        println("-------------------------------------------------------------------")

        civil.apply {
            set(DAY_OF_WEEK_IN_MONTH, -1)
            print(DAY_OF_WEEK_IN_MONTH)
        }

        gregorian.apply {
            set(DAY_OF_WEEK_IN_MONTH, -1)
            print(DAY_OF_WEEK_IN_MONTH)
        }

        assertEquals(civil, gregorian)
    }

    @Test
    fun weekOfYear() {
        val civil = CivilCalendar().apply {
            set(YEAR, 2019)
            set(MONTH, 5)
            set(DAY_OF_MONTH, 10)
            print(WEEK_OF_YEAR)
        }

        val gregorian = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, 5)
            set(DAY_OF_MONTH, 10)
            print(WEEK_OF_YEAR)
        }

        println("-------------------------------------------------------------------")

        civil.apply {
            set(WEEK_OF_YEAR, -10)
            print(WEEK_OF_YEAR)
        }

        gregorian.apply {
            set(WEEK_OF_YEAR, -10)
            print(WEEK_OF_YEAR)
        }

        assertEquals(civil, gregorian)
    }

    // ---------------------------------------------------------------------------------------------

    private fun CivilCalendar.print(field: Int) {
        println("$longDateString  ->  ${BaseCalendar.fieldName(field)}: ${get(field)}")
    }

    private fun Calendar.print(field: Int) {
        println("$longDateString  ->  ${BaseCalendar.fieldName(field)}: ${get(field)}")
    }

    private fun assertEquals(civil: CivilCalendar, gregorian: Calendar) {
        assertEquals(civil.get(YEAR), gregorian.get(YEAR))
        assertEquals(civil.get(MONTH), gregorian.get(MONTH))
        assertEquals(civil.get(DAY_OF_MONTH), gregorian.get(DAY_OF_MONTH))
    }

    private val Calendar.longDateString: String
        get() = "${getDisplayName(DAY_OF_WEEK, LONG, Locale.ENGLISH)},  " +
                "${get(DAY_OF_MONTH)}  ${getDisplayName(MONTH, LONG, Locale.ENGLISH)}  ${get(YEAR)}"

}
