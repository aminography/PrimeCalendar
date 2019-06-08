package com.aminography.primecalendar.civil

/**
 * @author aminography
 */
object CivilCalendarUtils {

    private val normalMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val leapYearMonthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private val normalMonthLengthAggregated = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366)

    fun monthLength(year: Int, month: Int): Int =
            if (isGregorianLeapYear(year))
                leapYearMonthLength[month]
            else normalMonthLength[month]

    fun yearLength(year: Int): Int =
            if (isGregorianLeapYear(year))
                leapYearMonthLengthAggregated[12]
            else normalMonthLengthAggregated[12]

    fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
            if (isGregorianLeapYear(year))
                leapYearMonthLengthAggregated[month] + dayOfMonth
            else normalMonthLengthAggregated[month] + dayOfMonth

    internal fun isGregorianLeapYear(year: Int): Boolean =
            ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

}