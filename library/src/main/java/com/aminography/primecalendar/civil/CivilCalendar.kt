package com.aminography.primecalendar.civil

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.convertCivilToHijri
import com.aminography.primecalendar.common.convertCivilToPersian
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
class CivilCalendar : BaseCalendar() {

    private var civilYear: Int = 0
    private var civilMonth: Int = 0
    private var civilDayOfMonth: Int = 0

    override var year: Int
        get() = civilYear
        set(value) {
            set(value, civilMonth, civilDayOfMonth)
        }

    override var month: Int
        get() = civilMonth
        set(value) {
            set(civilYear, value, civilDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = civilDayOfMonth
        set(value) {
            set(civilYear, civilMonth, value)
        }

    override val monthName: String
        get() = internalCalendar.getDisplayName(MONTH, LONG, Locale.ENGLISH)

    override val weekDayName: String
        get() = internalCalendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.ENGLISH)

    override val monthLength: Int
        get() = CivilCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = CivilCalendarUtils.isGregorianLeapYear(year)

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

    override fun add(field: Int, amount: Int) {
        super.add(field, amount)
        invalidate()
    }

    override fun set(field: Int, value: Int) {
        super.set(field, value)
        invalidate()
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        super.set(year, month, dayOfMonth)
        invalidate()
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        super.set(year, month, dayOfMonth, hourOfDay, minute)
        invalidate()
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
        super.set(year, month, dayOfMonth, hourOfDay, minute, second)
        invalidate()
    }

    override fun roll(field: Int, amount: Int) {
        super.roll(field, amount)
        invalidate()
    }

    override fun invalidate() {
        civilYear = internalCalendar.get(YEAR)
        civilMonth = internalCalendar.get(MONTH)
        civilDayOfMonth = internalCalendar.get(DAY_OF_MONTH)
    }

    override fun dayOfYear(): Int = internalCalendar.get(DAY_OF_YEAR)

    override fun monthLength(year: Int, month: Int): Int =
            CivilCalendarUtils.monthLength(year, month)

    override fun yearLength(year: Int): Int =
            CivilCalendarUtils.yearLength(year)

    override fun dayOfYear(year: Int, dayOfYear: Int): DateHolder =
            CivilCalendarUtils.dayOfYear(year, dayOfYear)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = this

    override fun toPersian(): PersianCalendar = convertCivilToPersian(this)

    override fun toHijri(): HijriCalendar = convertCivilToHijri(this)

}
