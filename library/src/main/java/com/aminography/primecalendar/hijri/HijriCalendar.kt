package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.IntermediateCalendar
import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.Calendar.*


/**
 * @author aminography
 */
class HijriCalendar : IntermediateCalendar() {

    private var hijriYear: Int = 0
    private var hijriMonth: Int = 0
    private var hijriDayOfMonth: Int = 0

    override var year: Int
        get() = hijriYear
        set(value) {
            set(value, hijriMonth, hijriDayOfMonth)
        }

    override var month: Int
        get() = hijriMonth
        set(value) {
            set(hijriYear, value, hijriDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = hijriDayOfMonth
        set(value) {
            set(hijriYear, hijriMonth, value)
        }

    override val monthName: String
        get() = HijriCalendarUtils.hijriMonthNames[hijriMonth]

    override val weekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> HijriCalendarUtils.hijriWeekDays[0]
            SUNDAY -> HijriCalendarUtils.hijriWeekDays[1]
            MONDAY -> HijriCalendarUtils.hijriWeekDays[2]
            TUESDAY -> HijriCalendarUtils.hijriWeekDays[3]
            WEDNESDAY -> HijriCalendarUtils.hijriWeekDays[4]
            THURSDAY -> HijriCalendarUtils.hijriWeekDays[5]
            FRIDAY -> HijriCalendarUtils.hijriWeekDays[6]
            else -> throw IllegalArgumentException()
        }

    override val monthLength: Int
        get() = HijriCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = HijriCalendarUtils.isHijriLeapYear(year)

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

    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        checkRange(YEAR, year)
        checkRange(MONTH, month)
        checkRange(DAY_OF_MONTH, dayOfMonth)

        hijriYear = year
        hijriMonth = month
        hijriDayOfMonth = dayOfMonth

        HijriCalendarUtils.hijriToGregorian(
                DateHolder(hijriYear, hijriMonth, hijriDayOfMonth)
        ).let {
            super.set(it.year, it.month, it.dayOfMonth)
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
            hijriYear = it.year
            hijriMonth = it.month
            hijriDayOfMonth = it.dayOfMonth
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun dayOfYear(): Int =
            HijriCalendarUtils.dayOfYear(year, month, dayOfMonth)

    override fun monthLength(year: Int, month: Int): Int =
            HijriCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
            HijriCalendarUtils.yearLength(year)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
            HijriCalendarUtils.dayOfYear(year, dayOfYear)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertHijriToCivil(this)

    override fun toPersian(): PersianCalendar = convertHijriToPersian(this)

    override fun toHijri(): HijriCalendar = this

}
