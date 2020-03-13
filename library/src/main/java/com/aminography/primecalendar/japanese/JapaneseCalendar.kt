package com.aminography.primecalendar.japanese

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*


/**
 * @author aminography
 */
class JapaneseCalendar constructor(
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale(DEFAULT_LOCALE)
) : BaseCalendar(timeZone, locale) {

    constructor(
        timeZone: TimeZone = TimeZone.getDefault()
    ) : this(timeZone, Locale(DEFAULT_LOCALE))

    constructor(
        locale: Locale = Locale(DEFAULT_LOCALE)
    ) : this(TimeZone.getDefault(), locale)

    constructor(
    ) : this(TimeZone.getDefault(), Locale(DEFAULT_LOCALE))

    override val monthName: String
        get() = JapaneseCalendarUtils.monthName(internalMonth, locale)

    override val monthNameShort: String
        get() = JapaneseCalendarUtils.shortMonthName(internalMonth, locale)

    override val weekDayName: String
        get() = JapaneseCalendarUtils.weekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val weekDayNameShort: String
        get() = JapaneseCalendarUtils.shortWeekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val monthLength: Int
        get() = JapaneseCalendarUtils.monthLength(internalYear, internalMonth)

    override val isLeapYear: Boolean
        get() = JapaneseCalendarUtils.isJapaneseLeapYear(internalYear)

    override var firstDayOfWeek: Int = SUNDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.JAPANESE

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
        JapaneseCalendarUtils.japaneseToGregorian(
            DateHolder(internalYear, internalMonth, internalDayOfMonth)
        ).let {
            internalCalendar.set(it.year, it.month, it.dayOfMonth)
        }
    }

    override fun invalidate() {
        JapaneseCalendarUtils.gregorianToJapanese(
            DateHolder(
                internalCalendar.get(YEAR),
                internalCalendar.get(MONTH),
                internalCalendar.get(DAY_OF_MONTH)
            )
        ).also {
            internalYear = it.year
            internalMonth = it.month
            internalDayOfMonth = it.dayOfMonth
        }
    }

    override fun configSymbols(symbols: DateFormatSymbols) {
        symbols.apply {
            when (locale.language) {
                DEFAULT_LOCALE -> {
                    eras = JapaneseCalendarUtils.eras
                    months = JapaneseCalendarUtils.monthNames
                    shortMonths = JapaneseCalendarUtils.shortMonthNames
                    weekdays = JapaneseCalendarUtils.weekDays
                    shortWeekdays = JapaneseCalendarUtils.shortWeekDays
                    amPmStrings = JapaneseCalendarUtils.amPm
                }
                else -> {
                    eras = JapaneseCalendarUtils.erasEn
                    months = JapaneseCalendarUtils.monthNamesEn
                    shortMonths = JapaneseCalendarUtils.shortMonthNamesEn
                    weekdays = JapaneseCalendarUtils.weekDaysEn
                    shortWeekdays = JapaneseCalendarUtils.shortWeekDaysEn
                    amPmStrings = JapaneseCalendarUtils.amPmEn
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun monthLength(year: Int, month: Int): Int =
        JapaneseCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
        JapaneseCalendarUtils.yearLength(year)

    override fun dayOfYear(): Int =
        JapaneseCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
        JapaneseCalendarUtils.dayOfYear(year, dayOfYear)

    companion object {
        internal const val DEFAULT_LOCALE = "ja"
    }

}
