package com.aminography.primecalendar.civil

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
class CivilCalendar @JvmOverloads constructor(
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale.getDefault()
) : BaseCalendar(timeZone, locale) {

    override val monthName: String
        get() = internalCalendar.getDisplayName(MONTH, LONG, locale)

    override val monthNameShort: String
        get() = internalCalendar.getDisplayName(MONTH, SHORT, locale)

    override val weekDayName: String
        get() = internalCalendar.getDisplayName(DAY_OF_WEEK, LONG, locale)

    override val weekDayNameShort: String
        get() = internalCalendar.getDisplayName(DAY_OF_WEEK, SHORT, locale)

    override val monthLength: Int
        get() = CivilCalendarUtils.monthLength(internalYear, internalMonth)

    override val isLeapYear: Boolean
        get() = CivilCalendarUtils.isGregorianLeapYear(internalYear)

    override var firstDayOfWeek: Int = DEFAULT_FIRST_DAY_OF_WEEK
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.CIVIL

    override val minimum: Map<Int, Int>
        get() = mapOf(
            WEEK_OF_YEAR to 1,
            WEEK_OF_MONTH to 0,
            DAY_OF_MONTH to 1,
            DAY_OF_YEAR to 1,
            DAY_OF_WEEK_IN_MONTH to 1
        )

    override val maximum: Map<Int, Int>
        get() = mapOf(
            WEEK_OF_YEAR to 53, // Why not 54?
            WEEK_OF_MONTH to 6,
            DAY_OF_MONTH to 31,
            DAY_OF_YEAR to 366,
            DAY_OF_WEEK_IN_MONTH to 6 // Why not 5?
        )

    override val leastMaximum: Map<Int, Int>
        get() = mapOf(
            WEEK_OF_YEAR to 52, // Why not 53?
            WEEK_OF_MONTH to 4,
            DAY_OF_MONTH to 28,
            DAY_OF_YEAR to 365,
            DAY_OF_WEEK_IN_MONTH to 4
        )

    init {
        invalidate()
        setInternalFirstDayOfWeek(firstDayOfWeek)
    }

    // ---------------------------------------------------------------------------------------------

    override fun store() {
        internalCalendar.set(internalYear, internalMonth, internalDayOfMonth)
    }

    override fun invalidate() {
        internalYear = internalCalendar.get(YEAR)
        internalMonth = internalCalendar.get(MONTH)
        internalDayOfMonth = internalCalendar.get(DAY_OF_MONTH)
    }

    override fun configSymbols(symbols: DateFormatSymbols) {
    }

    // ---------------------------------------------------------------------------------------------

    override fun monthLength(year: Int, month: Int): Int =
        CivilCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
        CivilCalendarUtils.yearLength(year)

    override fun dayOfYear(): Int =
        CivilCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
        CivilCalendarUtils.dayOfYear(year, dayOfYear)

    @Suppress("unused")
    companion object {
        const val DEFAULT_FIRST_DAY_OF_WEEK = SUNDAY
        const val DEFAULT_LOCALE = "en"

        const val JANUARY = 0
        const val FEBRUARY = 1
        const val MARCH = 2
        const val APRIL = 3
        const val MAY = 4
        const val JUNE = 5
        const val JULY = 6
        const val AUGUST = 7
        const val SEPTEMBER = 8
        const val OCTOBER = 9
        const val NOVEMBER = 10
        const val DECEMBER = 11
    }

}
