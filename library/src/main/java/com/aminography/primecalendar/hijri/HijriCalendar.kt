package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.IntermediateCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.convertHijriToCivil
import com.aminography.primecalendar.common.convertHijriToPersian
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.Calendar.*


/**
 * @author aminography
 */
class HijriCalendar : IntermediateCalendar() {

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
        get() = HijriCalendarUtils.hijriMonthNames[internalMonth]

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

    override fun apply() {
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
