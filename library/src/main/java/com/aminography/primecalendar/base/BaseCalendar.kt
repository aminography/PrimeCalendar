package com.aminography.primecalendar.base

import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import java.util.*
import java.util.Calendar.*

/**
 * `BaseCalendar` is an abstract subclass of [PrimeCalendar] providing some methods which are
 * implemented in such a way that they don't depend on calendar type.
 *
 * @author aminography
 */
abstract class BaseCalendar(
        timeZone: TimeZone,
        locale: Locale
) : PrimeCalendar(timeZone, locale) {

    /**
     * A map of minimum feasible values for calendar fields depending on type of calendar.
     * The map fills in each child class implementing a calendar type.
     *
     * [WEEK_OF_YEAR],
     * [WEEK_OF_MONTH],
     * [DAY_OF_MONTH],
     * [DAY_OF_YEAR],
     * [DAY_OF_WEEK_IN_MONTH]
     */
    abstract val minimum: Map<Int, Int>

    /**
     * A map of maximum feasible values for calendar fields depending on type of calendar.
     * The map fills in each child class implementing a calendar type.
     *
     * [WEEK_OF_YEAR],
     * [WEEK_OF_MONTH],
     * [DAY_OF_MONTH],
     * [DAY_OF_YEAR],
     * [DAY_OF_WEEK_IN_MONTH]
     */
    abstract val maximum: Map<Int, Int>

    /**
     * A map of least maximum feasible values for calendar fields depending on type of calendar.
     * The map fills in each child class implementing a calendar type.
     *
     * [WEEK_OF_YEAR],
     * [WEEK_OF_MONTH],
     * [DAY_OF_MONTH],
     * [DAY_OF_YEAR],
     * [DAY_OF_WEEK_IN_MONTH]
     */
    abstract val leastMaximum: Map<Int, Int>

    /**
     * Returns the value of the given calendar field.
     *
     * @param field the given calendar field.
     * @return the value for the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range ([field &lt; 0 || field &gt;= FIELD_COUNT]).
     *
     * @see set(int,int)
     */
    override operator fun get(field: Int): Int {
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

    /**
     * Sets the given calendar field to the given value.
     *
     * @param field the given calendar field.
     * @param value the value to be set for the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range ([field &lt; 0 || field &gt;= FIELD_COUNT]).
     *
     * @see set(int,int,int)
     * @see set(int,int,int,int,int)
     * @see set(int,int,int,int,int,int)
     * @see get(int)
     */
    override operator fun set(field: Int, value: Int) {
        if (field < 0 || field > MILLISECOND) throw IllegalArgumentException()

        when (field) {
            ERA -> {
                super.set(field, value)
                invalidate()
            }
            YEAR -> {
                val min = getMinimum(field)
                val max = getMaximum(field)
                when (value) {
                    in min..max -> {
                        var d = internalDayOfMonth
                        if (d > monthLength(value, internalMonth)) d = monthLength(value, internalMonth)

                        internalYear = value
                        internalDayOfMonth = d
                        store()
                    }
                    else -> throw IllegalArgumentException("${fieldName(field)}=$value is out of feasible range. [Min: $min , Max: $max]")
                }
            }
            MONTH -> {
                val move = value - internalMonth

                val y: Int
                val m: Int
                var d: Int = internalDayOfMonth

                if (move > 0) {
                    y = internalYear + (internalMonth + move) / 12
                    m = (internalMonth + move) % 12
                } else {
                    y = internalYear - (12 - (internalMonth + move + 1)) / 12
                    m = (12 + (internalMonth + move) % 12) % 12
                }
                if (d > monthLength(y, m)) d = monthLength(y, m)

                internalYear = y
                internalMonth = m
                internalDayOfMonth = d
                store()
            }
            DAY_OF_MONTH -> { // also DATE
                val min = getActualMinimum(field)
                val max = getActualMaximum(field)
                when (value) {
                    in min..max -> {
                        internalDayOfMonth = value
                        store()
                    }
                    else -> {
                        val limit = if (value < min) min else max
                        internalDayOfMonth = limit
                        store()
                        super.add(field, value - limit)
                        invalidate()
                    }
                }
            }
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, 0, 1) // set base to first day of year
                    val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
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
                    val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
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
                when (value) {
                    in min..max -> {
                        dayOfYear(internalYear, value).let {
                            internalYear = it.year
                            internalMonth = it.month
                            internalDayOfMonth = it.dayOfMonth
                            store()
                        }
                    }
                    else -> {
                        val limit = if (value < min) min else max
                        dayOfYear(internalYear, limit).let {
                            internalYear = it.year
                            internalMonth = it.month
                            internalDayOfMonth = it.dayOfMonth
                            store()
                        }
                        super.add(field, value - limit)
                        invalidate()
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
                            val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
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
                            val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
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

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], and [DAY_OF_MONTH].
     * Previous values of other calendar fields are retained.  If this is not desired, call [clear] first.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     *
     * @see set(int,int)
     * @see set(int,int,int,int,int)
     * @see set(int,int,int,int,int,int)
     */
    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        val yearMin = getMinimum(YEAR)
        val yearMax = getMaximum(YEAR)
        when (year) {
            in yearMin..yearMax -> {
                internalYear = year
            }
            else -> throw IllegalArgumentException("${fieldName(YEAR)}=$year is out of feasible range. [Min: $yearMin , Max: $yearMax]")
        }

        val monthMin = 0
        val monthMax = 11
        when {
            month in monthMin..monthMax -> {
                internalMonth = month
            }
            month < monthMin -> {
                val diff = month - monthMin
                internalYear -= (12 - (monthMin + diff + 1)) / 12
                internalMonth = (12 + (monthMin + diff) % 12) % 12
            }
            month > monthMax -> {
                val diff = month - monthMax
                internalYear += (monthMax + diff) / 12
                internalMonth = (monthMax + diff) % 12
            }
        }

        var finalMove = 0
        val dayMin = 1
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
            super.add(DATE, finalMove)
            invalidate()
        }
    }

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], [DAY_OF_MONTH], [HOUR_OF_DAY], and [MINUTE].
     * Previous values of other fields are retained. If this is not desired, call [clear] first.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     * @param hourOfDay the value used to set the [HOUR_OF_DAY] calendar field.
     * @param minute the value used to set the [MINUTE] calendar field.
     *
     * @see set(int,int)
     * @see set(int,int,int)
     * @see set(int,int,int,int,int,int)
     */
    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        set(year, month, dayOfMonth)
        super.set(HOUR_OF_DAY, hourOfDay)
        super.set(MINUTE, minute)
    }

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], [DAY_OF_MONTH], [HOUR_OF_DAY], [MINUTE], and [SECOND].
     * Previous values of other fields are retained. If this is not desired, call [clear] first.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     * @param hourOfDay the value used to set the [HOUR_OF_DAY] calendar field.
     * @param minute the value used to set the [MINUTE] calendar field.
     * @param second the value used to set the [SECOND] calendar field.
     *
     * @see set(int,int)
     * @see set(int,int,int)
     * @see set(int,int,int,int,int,int)
     */
    override fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
        set(year, month, dayOfMonth)
        super.set(HOUR_OF_DAY, hourOfDay)
        super.set(MINUTE, minute)
        super.set(SECOND, second)
    }

    /**
     * Adds or subtracts the specified amount of time to the given calendar field,
     * based on the calendar's rules. For example, to subtract 5 days from
     * the current time of the calendar, you can achieve it by calling:
     * `add(Calendar.DAY_OF_MONTH, -5)`.
     *
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field.
     *
     * @see roll(int,int)
     * @see set(int,int)
     */
    override fun add(field: Int, amount: Int) {
        if (amount == 0) return
        this[field] += amount
    }

    /**
     * Adds the specified (signed) amount to the specified calendar field
     * without changing larger fields.  A negative amount means to roll
     * down.
     *
     * NOTE:  This default implementation on `Calendar` just repeatedly calls the
     * version of [roll] that rolls by one unit.  This may not
     * always do the right thing.  For example, if the [DAY_OF_MONTH] field is 31,
     * rolling through February will leave it set to 28.  The [java.util.GregorianCalendar]
     * version of this function takes care of this problem.  Other subclasses
     * should also provide overrides of this function that do the right thing.
     *
     * @param field the calendar field.
     * @param amount the signed amount to add to the calendar field.
     *
     * @see roll(int,boolean)
     * @see add(int,int)
     * @see set(int,int)
     */
    override fun roll(field: Int, amount: Int) {
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
                val day = internalDayOfMonth
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

    /**
     * Returns the minimum value for the given calendar field of this
     * `Calendar` instance. The minimum value is defined as
     * the smallest value returned by the [get] method
     * for any possible time value. The minimum value depends on
     * calendar system specific parameters of the instance.
     *
     * @param field the calendar field.
     * @return the minimum value for the given calendar field.
     *
     * @see getMaximum(int)
     * @see getGreatestMinimum(int)
     * @see getLeastMaximum(int)
     * @see getActualMinimum(int)
     * @see getActualMaximum(int)
     */
    override fun getMinimum(field: Int): Int {
        return minimum.getOrElse(field) {
            return super.getMinimum(field)
        }
    }

    /**
     * Returns the maximum value for the given calendar field of this
     * `Calendar` instance. The maximum value is defined as
     * the largest value returned by the [get] method
     * for any possible time value. The maximum value depends on
     * calendar system specific parameters of the instance.
     *
     * @param field the calendar field.
     * @return the maximum value for the given calendar field.
     *
     * @see getMinimum(int)
     * @see getGreatestMinimum(int)
     * @see getLeastMaximum(int)
     * @see getActualMinimum(int)
     * @see getActualMaximum(int)
     */
    override fun getMaximum(field: Int): Int {
        return maximum.getOrElse(field) {
            return super.getMaximum(field)
        }
    }

    /**
     * Returns the highest minimum value for the given calendar field
     * of this `Calendar` instance. The highest minimum
     * value is defined as the largest value returned by [getActualMinimum] for
     * any possible time value. The
     * greatest minimum value depends on calendar system specific
     * parameters of the instance.
     *
     * @param field the calendar field.
     * @return the highest minimum value for the given calendar field.
     *
     * @see getMinimum(int)
     * @see getMaximum(int)
     * @see getLeastMaximum(int)
     * @see getActualMinimum(int)
     * @see getActualMaximum(int)
     */
    override fun getGreatestMinimum(field: Int): Int {
        return getMinimum(field)
    }

    /**
     * Returns the lowest maximum value for the given calendar field
     * of this `Calendar` instance. The lowest maximum
     * value is defined as the smallest value returned by [getActualMaximum]
     * for any possible time value. The least
     * maximum value depends on calendar system specific parameters of
     * the instance. For example, a `Calendar` for the
     * Gregorian calendar system returns 28 for the
     * [DAY_OF_MONTH] field, because the 28th is the last
     * day of the shortest month of this calendar, February in a
     * common year.
     *
     * @param field the calendar field.
     * @return the lowest maximum value for the given calendar field.
     *
     * @see getMinimum(int)
     * @see getMaximum(int)
     * @see getGreatestMinimum(int)
     * @see getActualMinimum(int)
     * @see getActualMaximum(int)
     */
    override fun getLeastMaximum(field: Int): Int {
        return leastMaximum.getOrElse(field) {
            return super.getLeastMaximum(field)
        }
    }

    /**
     * Returns the minimum value that the specified calendar field
     * could have, given the time value of this `Calendar`.
     *
     * The default implementation of this method uses an iterative
     * algorithm to determine the actual minimum value for the
     * calendar field. Subclasses should, if possible, override this
     * with a more efficient implementation - in many cases, they can
     * simply return [getMinimum].
     *
     * @param field the calendar field
     * @return the minimum of the given calendar field for the time value of this `Calendar`
     *
     * @see getMinimum(int)
     * @see getMaximum(int)
     * @see getGreatestMinimum(int)
     * @see getLeastMaximum(int)
     * @see getActualMaximum(int)
     */
    override fun getActualMinimum(field: Int): Int {
        return getMinimum(field)
    }

    /**
     * Returns the maximum value that the specified calendar field
     * could have, given the time value of this
     * `Calendar`. For example, the actual maximum value of
     * the [MONTH] field is 12 in some years, and 13 in
     * other years in the Hebrew calendar system.
     *
     * The default implementation of this method uses an iterative
     * algorithm to determine the actual maximum value for the
     * calendar field. Subclasses should, if possible, override this
     * with a more efficient implementation.
     *
     * @param field the calendar field
     * @return the maximum of the given calendar field for the time value of this `Calendar`
     *
     * @see getMinimum(int)
     * @see getMaximum(int)
     * @see getGreatestMinimum(int)
     * @see getLeastMaximum(int)
     * @see getActualMinimum(int)
     */
    override fun getActualMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).also { base ->
                    base.set(internalYear, internalMonth, internalDayOfMonth) // set base to current date
                    base[DAY_OF_YEAR] = yearLength(year)
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