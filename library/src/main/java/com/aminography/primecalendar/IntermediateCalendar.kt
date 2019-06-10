package com.aminography.primecalendar

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.common.CalendarFactory
import java.util.Calendar.*

/**
 * @author aminography
 */
abstract class IntermediateCalendar : BaseCalendar() {

    abstract val minimum: Map<Int, Int>

    abstract val maximum: Map<Int, Int>

    abstract val leastMaximum: Map<Int, Int>

    override fun get(field: Int): Int {
        return when (field) {
            ERA -> super.get(ERA)
            YEAR -> year
            MONTH -> month
            WEEK_OF_YEAR -> weekOfYear()
            WEEK_OF_MONTH -> weekOfMonth()
            DAY_OF_MONTH -> dayOfMonth // also DATE
            DAY_OF_YEAR -> dayOfYear()
            DAY_OF_WEEK -> super.get(DAY_OF_WEEK)
            DAY_OF_WEEK_IN_MONTH -> when (dayOfMonth) {
                in 1..7 -> 1
                in 8..14 -> 2
                in 15..21 -> 3
                in 22..28 -> 4
                else -> 5
            }
            else -> super.get(field)
        }
    }

    override fun add(field: Int, amount: Int) {
        if (amount == 0) return
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            YEAR -> {
                val y = year + amount
                val m = month
                var d = dayOfMonth
                if (d > monthLength(y, m)) d = monthLength(y, m)
                set(y, m, d)
            }
            MONTH -> {
                if (amount > 0) {
                    val y = year + (month + amount) / 12
                    val m = (month + amount) % 12
                    var d = dayOfMonth
                    if (d > monthLength(y, m)) d = monthLength(y, m)
                    set(y, m, d)
                } else {
                    val y = year - (12 - (month + amount + 1)) / 12
                    val m = (12 + (month + amount)) % 12
                    var d = dayOfMonth
                    if (d > monthLength(y, m)) d = monthLength(y, m)
                    set(y, m, d)
                }
            }
            else -> {
                super.add(field, amount)
                invalidate()
            }
        }
    }

    override fun set(field: Int, value: Int) {
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()
        checkRange(field, value)

        when (field) {
            ERA -> {
                super.set(field, value)
                invalidate()
            }
            YEAR -> {
                year = value
            }
            MONTH -> {
                month = value
            }
            DAY_OF_MONTH -> { // also DATE
                dayOfMonth = value
            }
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(year, 0, 1)
                    val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                    val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                    val move = (value - 1) * 7 + (dayOfWeek - baseDayOfWeek)
                    base.add(DAY_OF_YEAR, move)
                    set(base.year, base.month, base.dayOfMonth)
                }
            }
            WEEK_OF_MONTH -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(year, month, 1)
                    val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                    val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                    val move = (value - 1) * 7 + (dayOfWeek - baseDayOfWeek)
                    base.add(DAY_OF_YEAR, move)
                    set(base.year, base.month, base.dayOfMonth)
                }
            }
            DAY_OF_YEAR -> {
                dayOfYear(year, value).let {
                    set(it.year, it.month, it.dayOfMonth)
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
                            base.set(year, month, dayOfMonth)
                            val move = (value - get(DAY_OF_WEEK_IN_MONTH)) * 7
                            base.add(DAY_OF_YEAR, move)
                            set(base.year, base.month, base.dayOfMonth)
                        }
                    }
                    value == 0 -> {
                        CalendarFactory.newInstance(calendarType).also { base ->
                            base.set(year, month, 1)
                            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                            val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                            var move = (dayOfWeek - baseDayOfWeek)
                            if (move >= 0) move += -7
                            base.add(DAY_OF_YEAR, move)
                            set(base.year, base.month, base.dayOfMonth)
                        }
                    }
                    value < 0 -> {
                        CalendarFactory.newInstance(calendarType).also { base ->
                            base.set(year, month, monthLength)
                            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
                            val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))

                            val move = (dayOfWeek - baseDayOfWeek) + 7 * (value + 1)
                            base.add(DAY_OF_YEAR, move)
                            set(base.year, base.month, base.dayOfMonth)
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

    override fun roll(field: Int, amount: Int) {
        if (amount == 0) return
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            MONTH -> {
                var targetMonth = (month + amount) % 12
                if (targetMonth < 0) targetMonth += 12

                val targetMonthLength = monthLength(year, targetMonth)
                var targetDayOfMonth = dayOfMonth
                if (targetDayOfMonth > targetMonthLength) targetDayOfMonth = targetMonthLength

                set(year, targetMonth, targetDayOfMonth)
            }
            DAY_OF_MONTH -> {
                val targetMonthLength = monthLength
                var targetDayOfMonth = (dayOfMonth + amount) % targetMonthLength
                if (targetDayOfMonth <= 0) targetDayOfMonth += targetMonthLength

                set(year, month, targetDayOfMonth)
            }
            DAY_OF_YEAR -> {
                val targetYearLength = yearLength(year)
                var targetDayOfYear = (dayOfYear() + amount) % targetYearLength
                if (targetDayOfYear <= 0) targetDayOfYear += targetYearLength

                dayOfYear(year, targetDayOfYear).let {
                    set(it.year, it.month, it.dayOfMonth)
                }
            }
            DAY_OF_WEEK -> {
                if (amount % 7 == 0) return

                val dayOfWeek = adjustDayOfWeekOffset(get(DAY_OF_WEEK))
                var targetDayOfWeek = (dayOfWeek + amount) % 7
                if (targetDayOfWeek < 0) targetDayOfWeek += 7

                val move = targetDayOfWeek - dayOfWeek
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(year, month, dayOfMonth)
                    base.add(DAY_OF_YEAR, move)
                    set(base.year, base.month, base.dayOfMonth)
                }
            }
            WEEK_OF_YEAR -> {
                val day = dayOfYear()
                val maxDay = yearLength(year)
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

                dayOfYear(year, targetDayOfYear).let {
                    set(it.year, it.month, it.dayOfMonth)
                }
            }
            WEEK_OF_MONTH -> {
                val day = dayOfMonth
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

                set(year, month, targetDayOfMonth)
            }
            DAY_OF_WEEK_IN_MONTH -> {
                val day = dayOfMonth
                val maxDay = monthLength

                val list = arrayListOf<Int>()
                list.add(day)

                var x = day
                while (x + 7 <= maxDay) {
                    x += 7
                    list.add(x)
                }

                var dayIndex = 0
                x = day
                while (x - 7 > 0) {
                    x -= 7
                    list.add(0, x)
                    dayIndex++
                }

                var targetIndex = (dayIndex + amount) % list.size
                if (targetIndex < 0) targetIndex += list.size
                val targetDayOfMonth = list[targetIndex]
                list.clear()

                set(year, month, targetDayOfMonth)
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
                CalendarFactory.newInstance(calendarType).let { base ->
                    base.year = year
                    base.set(DAY_OF_YEAR, yearLength(year))
                    base.weekOfYear()
                }
            }
            WEEK_OF_MONTH -> {
                CalendarFactory.newInstance(calendarType).let { base ->
                    base.set(year, month, monthLength)
                    base.weekOfMonth()
                }
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