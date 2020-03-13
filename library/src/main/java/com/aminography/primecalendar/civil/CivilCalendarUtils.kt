package com.aminography.primecalendar.civil

import com.aminography.primecalendar.common.DateHolder

/**
 * @author aminography
 */
object CivilCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val normalMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val leapYearMonthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private val normalMonthLengthAggregated = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366)

    // Internal Calculation Methods ----------------------------------------------------------------

    fun monthLength(year: Int, month: Int): Int =
        if (isGregorianLeapYear(year))
            leapYearMonthLength[month]
        else normalMonthLength[month]

    fun yearLength(year: Int): Int =
        if (isGregorianLeapYear(year))
            leapYearMonthLengthAggregated[12]
        else normalMonthLengthAggregated[12]

    internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
        if (isGregorianLeapYear(year))
            leapYearMonthLengthAggregated[month] + dayOfMonth
        else normalMonthLengthAggregated[month] + dayOfMonth

    internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
        val monthLengthAggregated = if (isGregorianLeapYear(year))
            leapYearMonthLengthAggregated
        else normalMonthLengthAggregated

        var month = 0
        for (i in monthLengthAggregated.indices) {
            if (dayOfYear > monthLengthAggregated[i] && dayOfYear <= monthLengthAggregated[i + 1]) {
                month = i
            }
        }
        val dayOfMonth = dayOfYear - monthLengthAggregated[month]
        return DateHolder(year, month, dayOfMonth)
    }

    internal fun isGregorianLeapYear(year: Int): Boolean =
        ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

}