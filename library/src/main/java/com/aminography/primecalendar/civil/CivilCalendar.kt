package com.aminography.primecalendar.civil

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.convertCivilToHijri
import com.aminography.primecalendar.common.convertCivilToPersian
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.*

/**
 * @author aminography
 */
class CivilCalendar : BaseCalendar() {

    // When we call set(year, month, day) it contains set(field, value) in its internal calculation.
    // So by calling recalculate() which contains get(field) in overrided set(field, value), it causes
    // some faults in calendar internal calculations:
    //
    // [set(year, month, day)]
    //  |
    //  '--> [set(field, value)]
    //        | ------------------> because of recalculate()
    //        '--> [get(field)] :   causes fault
    //
    // Initial Date: 31/4/2019 (31 May, 2019)
    // Setting Date: 1/5/2019 (1 June, 2019)
    // False Date:   1/6/2019

    private var calculatingLevel = 0

    private var civilYear: Int = 0
    private var civilMonth: Int = 0
    private var civilDayOfMonth: Int = 0

    override var year: Int = civilYear
        get() = civilYear
        set(value) {
            field = value
            setDate(value, month, dayOfMonth)
        }

    override var month: Int = civilMonth
        get() = civilMonth
        set(value) {
            field = value
            setDate(year, value, dayOfMonth)
        }

    override var dayOfMonth: Int = civilDayOfMonth
        get() = civilDayOfMonth
        set(value) {
            field = value
            setDate(year, month, value)
        }

    override val monthName: String
        get() = getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)

    override val weekDayName: String
        get() = getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)

    override val monthLength: Int
        get() = CivilCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = CivilCalendarUtils.isGregorianLeapYear(year)

    override val weekStartDay: Int
        get() = Calendar.SUNDAY

    override val calendarType: CalendarType
        get() = CalendarType.CIVIL

    //----------------------------------------------------------------------------------------------

    override fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        calculatingLevel++
        super.setDate(year, month, dayOfMonth)
        calculatingLevel--
        recalculate()
    }

    override fun add(field: Int, amount: Int) {
        calculatingLevel++
        super.add(field, amount)
        calculatingLevel--
        recalculate()
    }

    override fun set(field: Int, value: Int) {
        calculatingLevel++
        super.set(field, value)
        calculatingLevel--
        recalculate()
    }

    override fun setTimeInMillis(millis: Long) {
        calculatingLevel++
        super.setTimeInMillis(millis)
        calculatingLevel--
        recalculate()
    }

    override fun setTimeZone(zone: TimeZone) {
        calculatingLevel++
        super.setTimeZone(zone)
        calculatingLevel--
        recalculate()
    }

    private fun recalculate() {
        if (calculatingLevel == 0) {
            civilYear = get(Calendar.YEAR)
            civilMonth = get(Calendar.MONTH)
            civilDayOfMonth = get(Calendar.DAY_OF_MONTH)
        }
    }

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = this

    override fun toPersian(): PersianCalendar = convertCivilToPersian(this)

    override fun toHijri(): HijriCalendar = convertCivilToHijri(this)

}
