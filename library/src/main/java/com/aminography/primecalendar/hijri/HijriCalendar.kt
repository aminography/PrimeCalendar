package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.convertHijriToCivil
import com.aminography.primecalendar.common.convertHijriToPersian
import com.aminography.primecalendar.persian.PersianCalendar
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*


/**
 * @author aminography
 */
class HijriCalendar(
        timeZone: TimeZone = TimeZone.getDefault(),
        locale: Locale = Locale("ar")
) : BaseCalendar(timeZone, locale) {

    override val monthName: String
        get() = HijriCalendarUtils.monthName(internalMonth, locale)

    override val monthNameShort: String
        get() = HijriCalendarUtils.shortMonthName(internalMonth, locale)

    override val weekDayName: String
        get() = HijriCalendarUtils.weekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val weekDayNameShort: String
        get() = HijriCalendarUtils.shortWeekDayName(internalCalendar.get(DAY_OF_WEEK), locale)

    override val monthLength: Int
        get() = HijriCalendarUtils.monthLength(internalYear, internalMonth)

    override val isLeapYear: Boolean
        get() = HijriCalendarUtils.isHijriLeapYear(internalYear)

    override var firstDayOfWeek: Int = SATURDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.HIJRI

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
                WEEK_OF_YEAR to 52,
                WEEK_OF_MONTH to 6,
                DAY_OF_MONTH to 30,
                DAY_OF_YEAR to 355,
                DAY_OF_WEEK_IN_MONTH to 5
        )

    override val leastMaximum: Map<Int, Int>
        get() = mapOf(
                WEEK_OF_YEAR to 51,
                WEEK_OF_MONTH to 5,
                DAY_OF_MONTH to 29,
                DAY_OF_YEAR to 354,
                DAY_OF_WEEK_IN_MONTH to 5
        )

    init {
        invalidate()
        setInternalFirstDayOfWeek(firstDayOfWeek)
    }

    // ---------------------------------------------------------------------------------------------

    override fun store() {
        HijriCalendarUtils.hijriToGregorian(
                DateHolder(internalYear, internalMonth, internalDayOfMonth)
        ).let {
            internalCalendar.set(it.year, it.month, it.dayOfMonth)
        }
    }

    override fun invalidate() {
        HijriCalendarUtils.gregorianToHijri(
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
                "ar" -> {
                    eras = HijriCalendarUtils.eras
                    months = HijriCalendarUtils.monthNames
                    shortMonths = HijriCalendarUtils.shortMonthNames
                    weekdays = HijriCalendarUtils.weekDays
                    shortWeekdays = HijriCalendarUtils.shortWeekDays
                    amPmStrings = HijriCalendarUtils.amPm
                }
                else -> {
                    eras = HijriCalendarUtils.erasEn
                    months = HijriCalendarUtils.monthNamesEn
                    shortMonths = HijriCalendarUtils.shortMonthNamesEn
                    weekdays = HijriCalendarUtils.weekDaysEn
                    shortWeekdays = HijriCalendarUtils.shortWeekDaysEn
                    amPmStrings = HijriCalendarUtils.amPmEn
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun monthLength(year: Int, month: Int): Int =
            HijriCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
            HijriCalendarUtils.yearLength(year)

    override fun dayOfYear(): Int =
            HijriCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
            HijriCalendarUtils.dayOfYear(year, dayOfYear)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertHijriToCivil(this)

    override fun toPersian(): PersianCalendar = convertHijriToPersian(this)

    override fun toHijri(): HijriCalendar = this

}
