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
class CivilCalendar : /*PrimeCalendar()*/ BaseCalendar() {

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
        get() = internalCalendar.getDisplayName(MONTH, LONG, Locale.ENGLISH)

    override val weekDayName: String
        get() = internalCalendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.ENGLISH)

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

    // ---------------------------------------------------------------------------------------------

//    override fun add(field: Int, amount: Int) {
//        super.add(field, amount)
//        invalidate()
//    }
//
//    override fun set(field: Int, value: Int) {
//        super.set(field, value)
//        invalidate()
//    }
//
//    override fun set(year: Int, month: Int, dayOfMonth: Int) {
//        super.set(year, month, dayOfMonth)
//        invalidate()
//    }
//
//    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
//        super.set(year, month, dayOfMonth, hourOfDay, minute)
//        invalidate()
//    }
//
//    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
//        super.set(year, month, dayOfMonth, hourOfDay, minute, second)
//        invalidate()
//    }
//
//    override fun roll(field: Int, amount: Int) {
//        super.roll(field, amount)
//        invalidate()
//    }
//
//    override fun invalidate() {
//        civilYear = get(YEAR)
//        civilMonth = get(MONTH)
//        civilDayOfMonth = get(DAY_OF_MONTH)
//    }

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

//    override fun dayOfYear(): Int = get(DAY_OF_YEAR)

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
