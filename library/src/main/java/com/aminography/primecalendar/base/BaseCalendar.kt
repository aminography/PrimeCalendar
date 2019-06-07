package com.aminography.primecalendar.base

import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.IConverter
import java.util.*

/**
 * @author aminography
 */
abstract class BaseCalendar : GregorianCalendar, IConverter {

    abstract var year: Int

    abstract var month: Int

    abstract var dayOfMonth: Int

    abstract val monthName: String

    abstract val weekDayName: String

    abstract val monthLength: Int

    abstract val isLeapYear: Boolean

    abstract val weekStartDay: Int

    abstract val calendarType: CalendarType

    val longDateString: String
        get() = "$weekDayName,  $dayOfMonth  $monthName  $year"

    @Suppress("MemberVisibilityCanBePrivate")
    val shortDateString: String
        get() = normalize(year) + delimiter + normalize(month + 1) + delimiter + normalize(dayOfMonth)

    @Suppress("unused")
    val monthDayString: String
        get() = monthName + " " + normalize(dayOfMonth)

    open fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        super.set(year, month, dayOfMonth)
    }

    override fun toString(): String {
        val s = super.toString()
        return "${s.substring(0, s.length - 1)}, Date=$shortDateString]"
    }

    // ---------------------------------------------------------------------------------------------

    private fun weekOffset(dayOfWeek: Int): Int {
        val day = if (dayOfWeek < weekStartDay) dayOfWeek + 7 else dayOfWeek
        return (day - weekStartDay) % 7
    }

    val weekOfMonth: Int
        get() {
            val firstDayOfMonthDayOfWeek =
                    CalendarFactory.newInstance(calendarType).let {
                        it.setDate(year, month, 1)
                        it.get(Calendar.DAY_OF_WEEK)
                    }
            val offset = weekOffset(firstDayOfMonthDayOfWeek)
            val dividend = (offset + dayOfMonth) / 7
            val remainder = (offset + dayOfMonth) % 7
            return dividend + if (remainder > 0) 1 else 0
        }

    val weekOfYear: Int
        get() {
            val firstDayOfYearDayOfWeek =
                    CalendarFactory.newInstance(calendarType).let {
                        it.setDate(year, 0, 1)
                        it.get(Calendar.DAY_OF_WEEK)
                    }
            val offset = weekOffset(firstDayOfYearDayOfWeek)
            val dividend = (offset + dayOfYear) / 7 // TODO
            val remainder = (offset + dayOfYear) % 7
            return dividend + if (remainder > 0) 1 else 0
        }

    // ---------------------------------------------------------------------------------------------

    constructor() : super()

    constructor(zone: TimeZone) : super(zone)

    constructor(aLocale: Locale) : super(aLocale)

    constructor(zone: TimeZone, aLocale: Locale) : super(zone, aLocale)

    constructor(year: Int, month: Int, dayOfMonth: Int) : super(year, month, dayOfMonth)

    constructor(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) : super(year, month, dayOfMonth, hourOfDay, minute)

    constructor(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) : super(year, month, dayOfMonth, hourOfDay, minute, second)

}
