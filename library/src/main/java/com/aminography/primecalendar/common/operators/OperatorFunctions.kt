@file:Suppress("unused")

package com.aminography.primecalendar.common.operators

import com.aminography.primecalendar.PrimeCalendar
import java.util.*

/**
 * @author aminography
 *
 * Experimental design.
 */

operator fun PrimeCalendar.invoke() = timeInMillis

operator fun PrimeCalendar.plus(field: CalendarField): PrimeCalendar {
    return clone().also {
        it.add(field.field, field.amount)
    }
}

operator fun PrimeCalendar.plusAssign(field: CalendarField) {
    add(field.field, field.amount)
}

operator fun PrimeCalendar.minus(field: CalendarField): PrimeCalendar {
    return clone().also {
        it.add(field.field, -field.amount)
    }
}

operator fun PrimeCalendar.minusAssign(field: CalendarField) {
    add(field.field, -field.amount)
}

operator fun PrimeCalendar.rem(field: CalendarField): PrimeCalendar {
    return clone().also {
        it.roll(field.field, field.amount)
    }
}

operator fun PrimeCalendar.remAssign(field: CalendarField) {
    roll(field.field, field.amount)
}

// -------------------------------------------------------------------------------------------------

sealed class CalendarField(internal val field: Int) {
    abstract val amount: Int
}

class Year(override val amount: Int) : CalendarField(Calendar.YEAR)
class Month(override val amount: Int) : CalendarField(Calendar.MONTH)
class WeekOfYear(override val amount: Int) : CalendarField(Calendar.WEEK_OF_YEAR)
class WeekOfMonth(override val amount: Int) : CalendarField(Calendar.WEEK_OF_MONTH)
class Date(override val amount: Int) : CalendarField(Calendar.DATE)
class DayOfMonth(override val amount: Int) : CalendarField(Calendar.DAY_OF_MONTH)
class DayOfYear(override val amount: Int) : CalendarField(Calendar.DAY_OF_YEAR)
class DayOfWeek(override val amount: Int) : CalendarField(Calendar.DAY_OF_WEEK)
class DayOfWeekInMonth(override val amount: Int) : CalendarField(Calendar.DAY_OF_WEEK_IN_MONTH)
class Hour(override val amount: Int) : CalendarField(Calendar.HOUR)
class HourOfDay(override val amount: Int) : CalendarField(Calendar.HOUR_OF_DAY)
class Minute(override val amount: Int) : CalendarField(Calendar.MINUTE)
class Second(override val amount: Int) : CalendarField(Calendar.SECOND)
class Millisecond(override val amount: Int) : CalendarField(Calendar.MILLISECOND)

val Number.year: CalendarField
    get() = Year(this.toInt())

val Number.month: CalendarField
    get() = Month(this.toInt())

val Number.weekOfYear: CalendarField
    get() = WeekOfYear(this.toInt())

val Number.weekOfMonth: CalendarField
    get() = WeekOfMonth(this.toInt())

val Number.date: CalendarField
    get() = Date(this.toInt())

val Number.dayOfMonth: CalendarField
    get() = DayOfMonth(this.toInt())

val Number.dayOfYear: CalendarField
    get() = DayOfYear(this.toInt())

val Number.dayOfWeek: CalendarField
    get() = DayOfWeek(this.toInt())

val Number.dayOfWeekInMonth: CalendarField
    get() = DayOfWeekInMonth(this.toInt())

val Number.hour: CalendarField
    get() = Hour(this.toInt())

val Number.hourOfDay: CalendarField
    get() = HourOfDay(this.toInt())

val Number.minute: CalendarField
    get() = Minute(this.toInt())

val Number.second: CalendarField
    get() = Second(this.toInt())

val Number.millisecond: CalendarField
    get() = Millisecond(this.toInt())
