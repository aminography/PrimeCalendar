package com.aminography.primecalendar.common.operators

import com.aminography.primecalendar.PrimeCalendar
import java.util.*

/**
 * @author aminography
 *
 * Experimental design.
 */

operator fun PrimeCalendar.plus(unit: PrimeUnit): PrimeCalendar {
    return clone().also {
        it.add(unit.field, unit.value)
    }
}

operator fun PrimeCalendar.plusAssign(unit: PrimeUnit) {
    add(unit.field, unit.value)
}

operator fun PrimeCalendar.minus(unit: PrimeUnit): PrimeCalendar {
    return clone().also {
        it.add(unit.field, -unit.value)
    }
}

operator fun PrimeCalendar.minusAssign(unit: PrimeUnit) {
    add(unit.field, -unit.value)
}

operator fun PrimeCalendar.rem(unit: PrimeUnit): PrimeCalendar {
    return clone().also {
        it.roll(unit.field, unit.value)
    }
}

operator fun PrimeCalendar.remAssign(unit: PrimeUnit) {
    roll(unit.field, unit.value)
}

// -------------------------------------------------------------------------------------------------

sealed class PrimeUnit(internal val field: Int) {
    abstract val value: Int
}

class Year(override val value: Int) : PrimeUnit(Calendar.YEAR)
class Month(override val value: Int) : PrimeUnit(Calendar.MONTH)
class WeekOfYear(override val value: Int) : PrimeUnit(Calendar.WEEK_OF_YEAR)
class WeekOfMonth(override val value: Int) : PrimeUnit(Calendar.WEEK_OF_MONTH)
class Date(override val value: Int) : PrimeUnit(Calendar.DATE)
class DayOfMonth(override val value: Int) : PrimeUnit(Calendar.DAY_OF_MONTH)
class DayOfYear(override val value: Int) : PrimeUnit(Calendar.DAY_OF_YEAR)
class DayOfWeek(override val value: Int) : PrimeUnit(Calendar.DAY_OF_WEEK)
class DayOfWeekInMonth(override val value: Int) : PrimeUnit(Calendar.DAY_OF_WEEK_IN_MONTH)
class Hour(override val value: Int) : PrimeUnit(Calendar.HOUR)
class HourOfDay(override val value: Int) : PrimeUnit(Calendar.HOUR_OF_DAY)
class Minute(override val value: Int) : PrimeUnit(Calendar.MINUTE)
class Second(override val value: Int) : PrimeUnit(Calendar.SECOND)
class Millisecond(override val value: Int) : PrimeUnit(Calendar.MILLISECOND)
