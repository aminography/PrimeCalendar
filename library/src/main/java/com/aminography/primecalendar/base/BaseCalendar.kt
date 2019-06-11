package com.aminography.primecalendar.base

import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import java.util.Calendar.*

/**
 * @author aminography
 */
abstract class BaseCalendar : PrimeCalendar() {

    abstract val minimum: Map<Int, Int>

    abstract val maximum: Map<Int, Int>

    abstract val leastMaximum: Map<Int, Int>

    override fun get(field: Int): Int {
        return when (field) {
            ERA -> super.get(ERA)
            YEAR -> internalYear
            MONTH -> internalMonth
            WEEK_OF_YEAR -> weekOfYear()
            WEEK_OF_MONTH -> weekOfMonth()
            DAY_OF_MONTH -> internalDayOfMonth // also DATE
            DAY_OF_YEAR -> dayOfYear()
            DAY_OF_WEEK -> super.get(DAY_OF_WEEK)
            DAY_OF_WEEK_IN_MONTH -> when (internalDayOfMonth) {
                in 1..7 -> 1
                in 8..14 -> 2
                in 15..21 -> 3
                in 22..28 -> 4
                else -> 5
            }
            else -> super.get(field)
        }
    }

    override fun set(field: Int, value: Int) {
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            ERA -> {
                super.set(field, value)
                invalidate()
            }
            YEAR -> {
                val min = getActualMinimum(field)
                val max = getActualMaximum(field)
                when (value) {
                    in min..max -> {
                        internalYear = value
                        store()
                    }
                    else -> throw IllegalArgumentException("${fieldName(field)}=$value is out of feasible range. [Min: $min , Max: $max]")
                }
            }
            MONTH -> { // Fortunately, add() contains day of month checking
                val min = getActualMinimum(field)
                val max = getActualMaximum(field)
                when {
                    value in min..max -> {
                        internalMonth = value
                        store()
                    }
                    value < min -> {
                        internalMonth = min
                        store()
                        add(field, value - min)
                    }
                    value > max -> {
                        internalMonth = max
                        store()
                        add(field, value - max)
                    }
                }
            }
            DAY_OF_MONTH -> { // also DATE
                val min = getActualMinimum(field)
                val max = getActualMaximum(field)
                when {
                    value in min..max -> {
                        internalDayOfMonth = value
                        store()
                    }
                    value < min -> {
                        internalDayOfMonth = min
                        store()
                        add(field, value - min)
                    }
                    value > max -> {
                        internalDayOfMonth = max
                        store()
                        add(field, value - max)
                    }
                }
            }
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, 0, 1) // set base to first day of year
                    val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                    val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                    val move = (value - 1) * 7 + (dayOfWeek - baseDayOfWeek)
                    base.add(DATE, move)

                    internalYear = base.year
                    internalMonth = base.month
                    internalDayOfMonth = base.dayOfMonth
                    store()
                }
            }
            WEEK_OF_MONTH -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, internalMonth, 1) // set base to first day of month
                    val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                    val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                    val move = (value - 1) * 7 + (dayOfWeek - baseDayOfWeek)
                    base.add(DATE, move)

                    internalYear = base.year
                    internalMonth = base.month
                    internalDayOfMonth = base.dayOfMonth
                    store()
                }
            }
            DAY_OF_YEAR -> {
                val min = getActualMinimum(field)
                val max = getActualMaximum(field)
                when {
                    value in min..max -> {
                        dayOfYear(internalYear, value).let {
                            internalYear = it.year
                            internalMonth = it.month
                            internalDayOfMonth = it.dayOfMonth
                            store()
                        }
                    }
                    value < min -> {
                        dayOfYear(internalYear, min).let {
                            internalYear = it.year
                            internalMonth = it.month
                            internalDayOfMonth = it.dayOfMonth
                            store()
                        }
                        add(field, value - min)
                    }
                    value > max -> {
                        dayOfYear(internalYear, max).let {
                            internalYear = it.year
                            internalMonth = it.month
                            internalDayOfMonth = it.dayOfMonth
                            store()
                        }
                        add(field, value - max)
                    }
                }
            }
            DAY_OF_WEEK -> {
                super.set(field, value)
                invalidate()
            }
            DAY_OF_WEEK_IN_MONTH -> {
                when {
                    value > 0 -> {
                        CalendarFactory.newInstance(calendarType).also { base ->
                            base.set(internalYear, internalMonth, internalDayOfMonth) // set base to current date
                            val move = (value - get(DAY_OF_WEEK_IN_MONTH)) * 7
                            base.add(DATE, move)

                            internalYear = base.year
                            internalMonth = base.month
                            internalDayOfMonth = base.dayOfMonth
                            store()
                        }
                    }
                    value == 0 -> {
                        CalendarFactory.newInstance(calendarType).also { base ->
                            base.set(internalYear, internalMonth, 1)  // set base to first day of month
                            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                            val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                            var move = (dayOfWeek - baseDayOfWeek)
                            if (move >= 0) move += -7
                            base.add(DATE, move)

                            internalYear = base.year
                            internalMonth = base.month
                            internalDayOfMonth = base.dayOfMonth
                            store()
                        }
                    }
                    value < 0 -> {
                        CalendarFactory.newInstance(calendarType).also { base ->
                            base.set(internalYear, internalMonth, monthLength)  // set base to last day of month
                            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                            val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                            val offsetDiff = dayOfWeek - baseDayOfWeek
                            val move = when {
                                offsetDiff < 0 -> offsetDiff
                                offsetDiff > 0 -> offsetDiff - 7
                                else -> 0
                            } + 7 * (value + 1)

                            base.add(DATE, move)

                            internalYear = base.year
                            internalMonth = base.month
                            internalDayOfMonth = base.dayOfMonth
                            store()
                        }
                    }
                }
            }
            else -> {
                super.set(field, value)
                invalidate()
            }
        }
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        internalYear = year

        val monthMin = getActualMinimum(MONTH)
        val monthMax = getActualMaximum(MONTH)
        when {
            month in monthMin..monthMax -> {
                internalMonth = month
            }
            month < monthMin -> {
                val diff = month - monthMin
                internalYear -= (12 - (monthMin + diff + 1)) / 12
                internalMonth = 12 + (monthMin + diff) % 12
            }
            month > monthMax -> {
                val diff = month - monthMax
                internalYear += (monthMax + diff) / 12
                internalMonth = (monthMax + diff) % 12
            }
        }

        var finalMove = 0
        val dayMin = getActualMinimum(DAY_OF_MONTH)
        val dayMax = monthLength(internalYear, internalMonth)
        when {
            dayOfMonth in dayMin..dayMax -> {
                internalDayOfMonth = dayOfMonth
            }
            dayOfMonth < dayMin -> {
                internalDayOfMonth = dayMin
                finalMove = dayOfMonth - dayMin
            }
            dayOfMonth > dayMax -> {
                internalDayOfMonth = dayMax
                finalMove = dayOfMonth - dayMax
            }
        }

        store()

        if (finalMove != 0) {
            add(DATE, finalMove)
        }
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        set(year, month, dayOfMonth)
        super.set(HOUR_OF_DAY, hourOfDay)
        super.set(MINUTE, minute)
    }

    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
        set(year, month, dayOfMonth)
        super.set(HOUR_OF_DAY, hourOfDay)
        super.set(MINUTE, minute)
        super.set(SECOND, second)
    }

    override fun add(field: Int, amount: Int) {
        if (amount == 0) return
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            YEAR -> {
                val y = internalYear + amount
                val m = internalMonth
                var d = internalDayOfMonth
                if (d > monthLength(y, m)) d = monthLength(y, m)

                internalYear = y
                internalMonth = m
                internalDayOfMonth = d
                store()
            }
            MONTH -> {
                val y: Int
                val m: Int
                var d: Int = internalDayOfMonth

                if (amount > 0) {
                    y = internalYear + (internalMonth + amount) / 12
                    m = (internalMonth + amount) % 12
                } else {
                    y = internalYear - (12 - (internalMonth + amount + 1)) / 12
                    m = 12 + (internalMonth + amount) % 12
                }
                if (d > monthLength(y, m)) d = monthLength(y, m)

                internalYear = y
                internalMonth = m
                internalDayOfMonth = d
                store()
            }
            else -> {
                super.add(field, amount)
                invalidate()
            }
        }
    }

    override fun roll(field: Int, amount: Int) {
        if (amount == 0) return
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            MONTH -> {
                var targetMonth = (internalMonth + amount) % 12
                if (targetMonth < 0) targetMonth += 12

                val targetMonthLength = monthLength(internalYear, targetMonth)
                var targetDayOfMonth = internalDayOfMonth
                if (targetDayOfMonth > targetMonthLength) targetDayOfMonth = targetMonthLength

                internalMonth = targetMonth
                internalDayOfMonth = targetDayOfMonth
                store()
            }
            DAY_OF_MONTH -> {
                val targetMonthLength = monthLength
                var targetDayOfMonth = (internalDayOfMonth + amount) % targetMonthLength
                if (targetDayOfMonth <= 0) targetDayOfMonth += targetMonthLength

                internalDayOfMonth = targetDayOfMonth
                store()
            }
            DAY_OF_YEAR -> {
                val targetYearLength = yearLength(internalYear)
                var targetDayOfYear = (dayOfYear() + amount) % targetYearLength
                if (targetDayOfYear <= 0) targetDayOfYear += targetYearLength

                dayOfYear(internalYear, targetDayOfYear).let {
                    internalYear = it.year
                    internalMonth = it.month
                    internalDayOfMonth = it.dayOfMonth
                    store()
                }
            }
            DAY_OF_WEEK -> {
                if (amount % 7 == 0) return

                val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))
                var targetDayOfWeek = (dayOfWeek + amount) % 7
                if (targetDayOfWeek < 0) targetDayOfWeek += 7

                val move = targetDayOfWeek - dayOfWeek
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, internalMonth, internalDayOfMonth) // set base to current date
                    base.add(DATE, move)

                    internalYear = base.year
                    internalMonth = base.month
                    internalDayOfMonth = base.dayOfMonth
                    store()
                }
            }
            WEEK_OF_YEAR -> {
                val day = dayOfYear()
                val maxDay = yearLength(internalYear)
                val woy = get(WEEK_OF_YEAR)
                val maxWoy = getActualMaximum(WEEK_OF_YEAR)

                val array = IntArray(maxWoy)
                array[woy - 1] = day
                for (i in woy until maxWoy) {
                    array[i] =
                            if (array[i - 1] + 7 <= maxDay)
                                array[i - 1] + 7
                            else maxDay
                }
                for (i in (woy - 2) downTo 0) {
                    array[i] =
                            if (array[i + 1] - 7 >= 1)
                                array[i + 1] - 7
                            else 1
                }

                var targetIndex = (woy - 1 + amount) % maxWoy
                if (targetIndex < 0) targetIndex += maxWoy
                val targetDayOfYear = array[targetIndex]

                dayOfYear(internalYear, targetDayOfYear).let {
                    internalYear = it.year
                    internalMonth = it.month
                    internalDayOfMonth = it.dayOfMonth
                    store()
                }
            }
            WEEK_OF_MONTH -> {
                val day = internalDayOfMonth
                val maxDay = monthLength
                val wom = get(WEEK_OF_MONTH)
                val maxWom = getActualMaximum(WEEK_OF_MONTH)

                val array = IntArray(maxWom)
                array[wom - 1] = day
                for (i in wom until maxWom) {
                    array[i] =
                            if (array[i - 1] + 7 <= maxDay)
                                array[i - 1] + 7
                            else maxDay
                }
                for (i in (wom - 2) downTo 0) {
                    array[i] =
                            if (array[i + 1] - 7 >= 1)
                                array[i + 1] - 7
                            else 1
                }

                var targetIndex = (wom - 1 + amount) % maxWom
                if (targetIndex < 0) targetIndex += maxWom
                val targetDayOfMonth = array[targetIndex]

                internalDayOfMonth = targetDayOfMonth
                store()
            }
            DAY_OF_WEEK_IN_MONTH -> {
                val day = dayOfMonth
                val maxDay = monthLength

                val list = arrayListOf<Int>()
                list.add(day)

                var d = day
                while (d + 7 <= maxDay) {
                    d += 7
                    list.add(d)
                }

                var dayIndex = 0
                d = day
                while (d - 7 > 0) {
                    d -= 7
                    list.add(0, d)
                    dayIndex++
                }

                var targetIndex = (dayIndex + amount) % list.size
                if (targetIndex < 0) targetIndex += list.size
                val targetDayOfMonth = list[targetIndex]
                list.clear()

                internalDayOfMonth = targetDayOfMonth
                store()
            }
            else -> {
                super.roll(field, amount)
            }
        }
    }

    override fun getMinimum(field: Int): Int {
        return minimum.getOrElse(field) {
            return super.getMinimum(field)
        }
    }

    override fun getMaximum(field: Int): Int {
        return maximum.getOrElse(field) {
            return super.getMaximum(field)
        }
    }

    override fun getGreatestMinimum(field: Int): Int {
        return getMinimum(field)
    }

    override fun getLeastMaximum(field: Int): Int {
        return leastMaximum.getOrElse(field) {
            return super.getLeastMaximum(field)
        }
    }

    override fun getActualMinimum(field: Int): Int {
        return getMinimum(field)
    }

    override fun getActualMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, internalMonth, internalDayOfMonth) // set base to current date
                    base.set(DAY_OF_YEAR, yearLength(year))
                }.weekOfYear()
            }
            WEEK_OF_MONTH -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(year, month, monthLength) // set base to last day of month
                }.weekOfMonth()
            }
            DAY_OF_MONTH -> monthLength
            DAY_OF_YEAR -> yearLength(year)
            DAY_OF_WEEK_IN_MONTH -> when (monthLength) {
                in 1..7 -> 1
                in 8..14 -> 2
                in 15..21 -> 3
                in 22..28 -> 4
                else -> 5
            }
            else -> super.getActualMaximum(field)
        }
    }

}