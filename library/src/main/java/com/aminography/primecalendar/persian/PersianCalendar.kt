package com.aminography.primecalendar.persian

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
class PersianCalendar @JvmOverloads constructor(
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale(FARSI_IRANIAN_LOCALE)
) : BaseCalendar(timeZone, locale) {

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

    override var firstDayOfWeek: Int = DEFAULT_FIRST_DAY_OF_WEEK
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
                FARSI_IRANIAN_LOCALE -> {
                    eras = PersianCalendarUtils.eras
                    months = PersianCalendarUtils.monthNames
                    shortMonths = PersianCalendarUtils.shortMonthNames
                    weekdays = PersianCalendarUtils.weekDays
                    shortWeekdays = PersianCalendarUtils.shortWeekDays
                    amPmStrings = PersianCalendarUtils.amPm
                }
                FARSI_AFGHAN_LOCALE -> {
                    eras = PersianCalendarUtils.eras
                    months = PersianCalendarUtils.monthNamesAf
                    shortMonths = PersianCalendarUtils.monthNamesAf
                    weekdays = PersianCalendarUtils.weekDays
                    shortWeekdays = PersianCalendarUtils.shortWeekDays
                    amPmStrings = PersianCalendarUtils.amPm
                }
                PASHTO_LOCALE -> {
                    eras = PersianCalendarUtils.eras
                    months = PersianCalendarUtils.monthNamesPs
                    shortMonths = PersianCalendarUtils.monthNamesPs
                    weekdays = PersianCalendarUtils.weekDays
                    shortWeekdays = PersianCalendarUtils.shortWeekDays
                    amPmStrings = PersianCalendarUtils.amPm
                }
                KURDISH_LOCALE -> {
                    eras = PersianCalendarUtils.eras
                    months = PersianCalendarUtils.monthNamesKu
                    shortMonths = PersianCalendarUtils.monthNamesKu
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

    @Suppress("unused")
    companion object {
        const val DEFAULT_FIRST_DAY_OF_WEEK = SATURDAY
        const val FARSI_IRANIAN_LOCALE = "fa"
        const val FARSI_AFGHAN_LOCALE = "fa-af"
        const val PASHTO_LOCALE = "ps"
        const val KURDISH_LOCALE = "ku"

        const val FARVARDIN = 0
        const val ORDIBEHESHT = 1
        const val KHORDAD = 2
        const val TIR = 3
        const val MORDAD = 4
        const val SHAHRIVAR = 5
        const val MEHR = 6
        const val ABAN = 7
        const val AZAR = 8
        const val DEY = 9
        const val BAHMAN = 10
        const val ESFAND = 11
    }

}
