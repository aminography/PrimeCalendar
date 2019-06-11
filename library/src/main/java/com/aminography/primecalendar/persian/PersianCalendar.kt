package com.aminography.primecalendar.persian

import com.aminography.primecalendar.IntermediateCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.hijri.HijriCalendar
import java.text.DateFormatSymbols
import java.util.Calendar.*

/**
 * @author aminography
 */
class PersianCalendar : IntermediateCalendar() {

    override var year: Int
        get() = internalYear
        set(value) {
            set(value, internalMonth, internalDayOfMonth)
        }

    override var month: Int
        get() = internalMonth
        set(value) {
            set(internalYear, value, internalDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = internalDayOfMonth
        set(value) {
            set(internalYear, internalMonth, value)
        }

    override val monthName: String
        get() = PersianCalendarUtils.monthNames[internalMonth]

    override val weekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> PersianCalendarUtils.weekDays[0]
            SUNDAY -> PersianCalendarUtils.weekDays[1]
            MONDAY -> PersianCalendarUtils.weekDays[2]
            TUESDAY -> PersianCalendarUtils.weekDays[3]
            WEDNESDAY -> PersianCalendarUtils.weekDays[4]
            THURSDAY -> PersianCalendarUtils.weekDays[5]
            FRIDAY -> PersianCalendarUtils.weekDays[6]
            else -> throw IllegalArgumentException()
        }

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
            eras = PersianCalendarUtils.eras
            months = PersianCalendarUtils.monthNames
            shortMonths = PersianCalendarUtils.shortMonthNames
            weekdays = PersianCalendarUtils.weekDays
            shortWeekdays = PersianCalendarUtils.shortWeekDays
            amPmStrings = PersianCalendarUtils.amPm
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

}