package com.aminography.primecalendar.civil

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.convertCivilToHijri
import com.aminography.primecalendar.common.convertCivilToPersian
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
class CivilCalendar(
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

    override var firstDayOfWeek: Int = SUNDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.CIVIL

    init {
        invalidate()
        setInternalFirstDayOfWeek(firstDayOfWeek)
    }

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

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = this

    override fun toPersian(): PersianCalendar = convertCivilToPersian(this)

    override fun toHijri(): HijriCalendar = convertCivilToHijri(this)

}
