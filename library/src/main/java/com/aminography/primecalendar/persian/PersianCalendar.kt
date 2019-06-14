package com.aminography.primecalendar.persian

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.convertPersianToCivil
import com.aminography.primecalendar.common.convertPersianToHijri
import com.aminography.primecalendar.hijri.HijriCalendar
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
class PersianCalendar constructor(
        timeZone: TimeZone = TimeZone.getDefault(),
        locale: Locale = Locale(DEFAULT_LOCALE)
) : BaseCalendar(timeZone, locale) {

    constructor(
            timeZone: TimeZone = TimeZone.getDefault()
    ) : this(timeZone, Locale(DEFAULT_LOCALE))

    constructor(
            locale: Locale = Locale(DEFAULT_LOCALE)
    ) : this(TimeZone.getDefault(), locale)

    override val monthName: String
        get() = PersianCalendarUtils.monthName(internalMonth, locale)

    override val monthNameShort: String
        get() = PersianCalendarUtils.shortMonthName(internalMonth, locale)

    override val weekDayName: String
        get() = PersianCalendarUtils.weekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val weekDayNameShort: String
        get() = PersianCalendarUtils.shortWeekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val monthLength: Int
        get() = PersianCalendarUtils.monthLength(internalYear, internalMonth)

    override val isLeapYear: Boolean
        get() = PersianCalendarUtils.isPersianLeapYear(internalYear)

    override var firstDayOfWeek: Int = SATURDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.PERSIAN

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
                WEEK_OF_YEAR to 54,
                WEEK_OF_MONTH to 6,
                DAY_OF_MONTH to 31,
                DAY_OF_YEAR to 366,
                DAY_OF_WEEK_IN_MONTH to 5
        )

    override val leastMaximum: Map<Int, Int>
        get() = mapOf(
                WEEK_OF_YEAR to 53,
                WEEK_OF_MONTH to 5,
                DAY_OF_MONTH to 29,
                DAY_OF_YEAR to 365,
                DAY_OF_WEEK_IN_MONTH to 5
        )

    init {
        invalidate()
        setInternalFirstDayOfWeek(firstDayOfWeek)
    }

    // ---------------------------------------------------------------------------------------------

    override fun store() {
        PersianCalendarUtils.persianToGregorian(
                DateHolder(internalYear, internalMonth, internalDayOfMonth)
        ).let {
            internalCalendar.set(it.year, it.month, it.dayOfMonth)
        }
    }

    override fun invalidate() {
        PersianCalendarUtils.gregorianToPersian(
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
                    eras = PersianCalendarUtils.eras
                    months = PersianCalendarUtils.monthNames
                    shortMonths = PersianCalendarUtils.shortMonthNames
                    weekdays = PersianCalendarUtils.weekDays
                    shortWeekdays = PersianCalendarUtils.shortWeekDays
                    amPmStrings = PersianCalendarUtils.amPm
                }
                else -> {
                    eras = PersianCalendarUtils.erasEn
                    months = PersianCalendarUtils.monthNamesEn
                    shortMonths = PersianCalendarUtils.shortMonthNamesEn
                    weekdays = PersianCalendarUtils.weekDaysEn
                    shortWeekdays = PersianCalendarUtils.shortWeekDaysEn
                    amPmStrings = PersianCalendarUtils.amPmEn
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun monthLength(year: Int, month: Int): Int =
            PersianCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
            PersianCalendarUtils.yearLength(year)

    override fun dayOfYear(): Int =
            PersianCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
            PersianCalendarUtils.dayOfYear(year, dayOfYear)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertPersianToCivil(this)

    override fun toPersian(): PersianCalendar = this

    override fun toHijri(): HijriCalendar = convertPersianToHijri(this)

    companion object {
        internal const val DEFAULT_LOCALE = "fa"
    }

}
