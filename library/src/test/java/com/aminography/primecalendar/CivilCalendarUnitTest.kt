package com.aminography.primecalendar

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
    fun set1() {
        val civil = CivilCalendar().apply {
            set(YEAR, 2019)
            set(MONTH, 5)
            set(DAY_OF_MONTH, 19)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, 5)
            set(DAY_OF_MONTH, 19)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_overValues() {
        val civil = CivilCalendar().apply {
            set(YEAR, 2019)
            set(MONTH, 25)
            set(DAY_OF_MONTH, 38)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, 25)
            set(DAY_OF_MONTH, 38)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_underValues() {
        val civil = CivilCalendar().apply {
            set(YEAR, 2019)
            set(MONTH, -7)
            set(DAY_OF_MONTH, -85)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, -7)
            set(DAY_OF_MONTH, -85)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_YEAR() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_YEAR, 19)
            print(WEEK_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_YEAR, 19)
            print(WEEK_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_YEAR_overValues() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_YEAR, 81)
            print(WEEK_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_YEAR, 81)
            print(WEEK_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_YEAR_underValues() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_YEAR, -33)
            print(WEEK_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_YEAR, -33)
            print(WEEK_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_MONTH() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_MONTH, 3)
            print(WEEK_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_MONTH, 3)
            print(WEEK_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_MONTH_overValues() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_MONTH, 19)
            print(WEEK_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_MONTH, 19)
            print(WEEK_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_WEEK_OF_MONTH_underValues() {
        val civil = CivilCalendar().apply {
            set(WEEK_OF_MONTH, -33)
            print(WEEK_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            set(WEEK_OF_MONTH, -33)
            print(WEEK_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_YEAR() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_YEAR, 70)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_YEAR, 70)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_YEAR_overValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_YEAR, 991)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_YEAR, 991)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_YEAR_underValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_YEAR, -533)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_YEAR, -533)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK, 2)
            print(DAY_OF_WEEK)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK, 2)
            print(DAY_OF_WEEK)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK_overValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK, 31)
            print(DAY_OF_WEEK)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK, 31)
            print(DAY_OF_WEEK)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK_underValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK, -93)
            print(DAY_OF_WEEK)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK, -93)
            print(DAY_OF_WEEK)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK_IN_MONTH() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK_IN_MONTH, 2)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK_IN_MONTH, 2)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK_IN_MONTH_overValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK_IN_MONTH, 31)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK_IN_MONTH, 31)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set1_DAY_OF_WEEK_IN_MONTH_underValues() {
        val civil = CivilCalendar().apply {
            set(DAY_OF_WEEK_IN_MONTH, -93)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        val gregorian = getInstance().apply {
            set(DAY_OF_WEEK_IN_MONTH, -93)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    // ---------------------------------------------------------------------------------------------

    @Test
    fun set2() {
        val civil = CivilCalendar().apply {
            set(2019, 3, 18)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(2019, 3, 18)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set2_overValues() {
        val civil = CivilCalendar().apply {
            set(2019, 21, 47)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(2019, 21, 47)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun set2_underValues() {
        val civil = CivilCalendar().apply {
            set(2019, -38, -96)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(2019, -38, -96)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    // ---------------------------------------------------------------------------------------------

    @Test
    fun add_YEAR() {
        val civil = CivilCalendar().apply {
            add(YEAR, 5)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            add(YEAR, 5)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun add_YEAR_negativeValues() {
        val civil = CivilCalendar().apply {
            add(YEAR, -7)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            add(YEAR, -7)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun add_MONTH() {
        val civil = CivilCalendar().apply {
            add(MONTH, 53)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            add(MONTH, 53)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun add_MONTH_negativeValues() {
        val civil = CivilCalendar().apply {
            set(2019, 5, 30)
            add(MONTH, -4)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            set(2019, 5, 30)
            add(MONTH, -4)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    // ---------------------------------------------------------------------------------------------

    @Test
    fun roll_YEAR() {
        val civil = CivilCalendar().apply {
            roll(YEAR, 19)
            print(YEAR)
        }
        val gregorian = getInstance().apply {
            roll(YEAR, 19)
            print(YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_YEAR_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(YEAR, -21)
            print(YEAR)
        }
        val gregorian = getInstance().apply {
            roll(YEAR, -21)
            print(YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_MONTH() {
        val civil = CivilCalendar().apply {
            roll(MONTH, 193)
            print(MONTH)
        }
        val gregorian = getInstance().apply {
            roll(MONTH, 193)
            print(MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_MONTH_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(MONTH, -121)
            print(MONTH)
        }
        val gregorian = getInstance().apply {
            roll(MONTH, -121)
            print(MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_MONTH() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_MONTH, 193)
            print(DAY_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_MONTH, 193)
            print(DAY_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_MONTH_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_MONTH, -121)
            print(DAY_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_MONTH, -121)
            print(DAY_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_YEAR() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_YEAR, 1973)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_YEAR, 1973)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_YEAR_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_YEAR, -1021)
            print(DAY_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_YEAR, -1021)
            print(DAY_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_WEEK() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_WEEK, 193)
            print(DAY_OF_WEEK)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_WEEK, 193)
            print(DAY_OF_WEEK)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_WEEK_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_WEEK, -121)
            print(DAY_OF_WEEK)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_WEEK, -121)
            print(DAY_OF_WEEK)
        }
        assertEquals(civil, gregorian)
    }

    /**
     * I have reported an anomaly in rolling behaviour [here](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8225641)
     * which causes the difference in result of two following test cases.
     */
    @Test
    fun roll_WEEK_OF_YEAR() {
        val civil = CivilCalendar().apply {
            roll(WEEK_OF_YEAR, 193)
            print(WEEK_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            roll(WEEK_OF_YEAR, 193)
            print(WEEK_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_WEEK_OF_YEAR_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(WEEK_OF_YEAR, -121)
            print(WEEK_OF_YEAR)
        }
        val gregorian = getInstance().apply {
            roll(WEEK_OF_YEAR, -121)
            print(WEEK_OF_YEAR)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_WEEK_OF_MONTH() {
        val civil = CivilCalendar().apply {
            roll(WEEK_OF_MONTH, 193)
            print(WEEK_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(WEEK_OF_MONTH, 193)
            print(WEEK_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_WEEK_OF_MONTH_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(WEEK_OF_MONTH, -121)
            print(WEEK_OF_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(WEEK_OF_MONTH, -121)
            print(WEEK_OF_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_WEEK_IN_MONTH() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_WEEK_IN_MONTH, 193)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_WEEK_IN_MONTH, 193)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    @Test
    fun roll_DAY_OF_WEEK_IN_MONTH_negativeValues() {
        val civil = CivilCalendar().apply {
            roll(DAY_OF_WEEK_IN_MONTH, -121)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        val gregorian = getInstance().apply {
            roll(DAY_OF_WEEK_IN_MONTH, -121)
            print(DAY_OF_WEEK_IN_MONTH)
        }
        assertEquals(civil, gregorian)
    }

    // ---------------------------------------------------------------------------------------------

    private fun CivilCalendar.print(field: Int) {
        println("$longDateString  ->  ${PrimeCalendar.fieldName(field)}: ${get(field)}")
    }

    private fun Calendar.print(field: Int) {
        println("$longDateString  ->  ${PrimeCalendar.fieldName(field)}: ${get(field)}")
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
