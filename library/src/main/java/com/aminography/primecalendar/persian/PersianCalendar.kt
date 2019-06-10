package com.aminography.primecalendar.persian

import com.aminography.primecalendar.IntermediateCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.hijri.HijriCalendar
import java.util.Calendar.*

/**
 * @author aminography
 */
class PersianCalendar : IntermediateCalendar() {

    private var persianYear: Int = 0
    private var persianMonth: Int = 0
    private var persianDayOfMonth: Int = 0

    override var year: Int
        get() = persianYear
        set(value) {
            set(value, persianMonth, persianDayOfMonth)
        }

    override var month: Int
        get() = persianMonth
        set(value) {
            set(persianYear, value, persianDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = persianDayOfMonth
        set(value) {
            set(persianYear, persianMonth, value)
        }

    override val monthName: String
        get() = PersianCalendarUtils.persianMonthNames[persianMonth]

    override val weekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> PersianCalendarUtils.persianWeekDays[0]
            SUNDAY -> PersianCalendarUtils.persianWeekDays[1]
            MONDAY -> PersianCalendarUtils.persianWeekDays[2]
            TUESDAY -> PersianCalendarUtils.persianWeekDays[3]
            WEDNESDAY -> PersianCalendarUtils.persianWeekDays[4]
            THURSDAY -> PersianCalendarUtils.persianWeekDays[5]
            FRIDAY -> PersianCalendarUtils.persianWeekDays[6]
            else -> throw IllegalArgumentException()
        }

    override val monthLength: Int
        get() = PersianCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = PersianCalendarUtils.isPersianLeapYear(year)

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

    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        checkRange(YEAR, year)
        checkRange(MONTH, month)
        checkRange(DAY_OF_MONTH, dayOfMonth)

        persianYear = year
        persianMonth = month
        persianDayOfMonth = dayOfMonth

        PersianCalendarUtils.persianToGregorian(
                DateHolder(persianYear, persianMonth, persianDayOfMonth)
        ).let {
            super.set(it.year, it.month, it.dayOfMonth)
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
            persianYear = it.year
            persianMonth = it.month
            persianDayOfMonth = it.dayOfMonth
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun dayOfYear(): Int =
            PersianCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun monthLength(year: Int, month: Int): Int =
            PersianCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
            PersianCalendarUtils.yearLength(year)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
            PersianCalendarUtils.dayOfYear(year, dayOfYear)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertPersianToCivil(this)

    override fun toPersian(): PersianCalendar = this

    override fun toHijri(): HijriCalendar = convertPersianToHijri(this)

}