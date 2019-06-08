package com.aminography.primecalendar.persian

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.hijri.HijriCalendar
import java.util.Calendar.*

/**
 * @author aminography
 */
class PersianCalendar : BaseCalendar() {

    private var persianYear: Int = 0
    private var persianMonth: Int = 0
    private var persianDayOfMonth: Int = 0

    override var year: Int
        get() = persianYear
        set(value) {
            set(value, persianMonth, persianDayOfMonth)
        }

    override var month: Int
        get() = persianMonth
        set(value) {
            set(persianYear, value, persianDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = persianDayOfMonth
        set(value) {
            set(persianYear, persianMonth, value)
        }

    override val monthName: String
        get() = PersianCalendarUtils.persianMonthNames[persianMonth]

    override val weekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> PersianCalendarUtils.persianWeekDays[0]
            SUNDAY -> PersianCalendarUtils.persianWeekDays[1]
            MONDAY -> PersianCalendarUtils.persianWeekDays[2]
            TUESDAY -> PersianCalendarUtils.persianWeekDays[3]
            WEDNESDAY -> PersianCalendarUtils.persianWeekDays[4]
            THURSDAY -> PersianCalendarUtils.persianWeekDays[5]
            FRIDAY -> PersianCalendarUtils.persianWeekDays[6]
            else -> throw IllegalArgumentException()
        }

    override val monthLength: Int
        get() = PersianCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = PersianCalendarUtils.isPersianLeapYear(year)

    override var firstDayOfWeek: Int = SATURDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.PERSIAN

    init {
        invalidate()
        setInternalFirstDayOfWeek(firstDayOfWeek)
    }

    // ---------------------------------------------------------------------------------------------

    override fun get(field: Int): Int {
        return when (field) {
            ERA -> super.get(ERA)
            YEAR -> year
            MONTH -> month
            WEEK_OF_YEAR -> calculateWeekOfYear()
            WEEK_OF_MONTH -> calculateWeekOfMonth()
            DAY_OF_MONTH -> dayOfMonth // also DATE
            DAY_OF_YEAR -> calculateDayOfYear()
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
            YEAR -> set(persianYear + amount, persianMonth, persianDayOfMonth)
            MONTH -> {
                if (amount > 0) {
                    set(persianYear + (persianMonth + amount) / 12, (persianMonth + amount) % 12, persianDayOfMonth)
                } else {
                    set(persianYear - (12 - (persianMonth + amount + 1)) / 12, (12 + (persianMonth + amount)) % 12, persianDayOfMonth)
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
                if (value > PersianCalendarUtils.yearLength(year)) {
                    throw IllegalArgumentException()
                } else {
                    PersianCalendarUtils.dayOfYear(year, value).let {
                        set(it.year, it.month, it.dayOfMonth)
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

    override fun set(year: Int, month: Int, dayOfMonth: Int) {
        checkRange(YEAR, year)
        checkRange(MONTH, month)
        checkRange(DAY_OF_MONTH, dayOfMonth)

        persianYear = year
        persianMonth = month
        persianDayOfMonth = dayOfMonth

        PersianCalendarUtils.persianToGregorian(
                DateHolder(persianYear, persianMonth, persianDayOfMonth)
        ).let {
            super.set(it.year, it.month, it.dayOfMonth)
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

        // TODO
    }

    override fun getMinimum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> 1
            WEEK_OF_MONTH -> 0
            DAY_OF_MONTH -> 1
            DAY_OF_YEAR -> 1
            DAY_OF_WEEK_IN_MONTH -> 1
            else -> super.getMinimum(field)
        }
    }

    override fun getMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> 54
            WEEK_OF_MONTH -> 6
            DAY_OF_MONTH -> 31
            DAY_OF_YEAR -> 366
            DAY_OF_WEEK_IN_MONTH -> 5
            else -> super.getMaximum(field)
        }
    }

    override fun getGreatestMinimum(field: Int): Int {
        return getMinimum(field)
    }

    override fun getLeastMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> 53
            WEEK_OF_MONTH -> 5
            DAY_OF_MONTH -> 29
            DAY_OF_YEAR -> 365
            DAY_OF_WEEK_IN_MONTH -> 5
            else -> super.getLeastMaximum(field)
        }
    }

    override fun getActualMinimum(field: Int): Int {
        return getMinimum(field)
    }

    override fun getActualMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> {
                CalendarFactory.newInstance(calendarType).let { base ->
                    base.set(DAY_OF_YEAR, if (isLeapYear) 366 else 365)
                    base.calculateWeekOfYear()
                }
            }
            WEEK_OF_MONTH -> {
                CalendarFactory.newInstance(calendarType).let { base ->
                    base.dayOfMonth = monthLength
                    base.calculateWeekOfMonth()
                }
            }
            DAY_OF_MONTH -> monthLength
            DAY_OF_YEAR -> if (isLeapYear) 366 else 365
            DAY_OF_WEEK_IN_MONTH -> 5
            else -> super.getActualMaximum(field)
        }
    }

    override fun invalidate() {
        PersianCalendarUtils.gregorianToPersian(
                DateHolder(
                        super.get(YEAR),
                        super.get(MONTH),
                        super.get(DAY_OF_MONTH)
                )
        ).also {
            persianYear = it.year
            persianMonth = it.month
            persianDayOfMonth = it.dayOfMonth
        }
    }

    override fun calculateDayOfYear(): Int = PersianCalendarUtils.dayOfYear(year, month, dayOfMonth)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertPersianToCivil(this)

    override fun toPersian(): PersianCalendar = this

    override fun toHijri(): HijriCalendar = convertPersianToHijri(this)

}