package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.base.BaseCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.Calendar.*


/**
 * @author aminography
 */
class HijriCalendar : BaseCalendar() {

    private var hijriYear: Int = 0
    private var hijriMonth: Int = 0
    private var hijriDayOfMonth: Int = 0

    override var year: Int
        get() = hijriYear
        set(value) {
            set(value, hijriMonth, hijriDayOfMonth)
        }

    override var month: Int
        get() = hijriMonth
        set(value) {
            set(hijriYear, value, hijriDayOfMonth)
        }

    override var dayOfMonth: Int
        get() = hijriDayOfMonth
        set(value) {
            set(hijriYear, hijriMonth, value)
        }

    override val monthName: String
        get() = HijriCalendarUtils.hijriMonthNames[hijriMonth]

    override val weekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> HijriCalendarUtils.hijriWeekDays[0]
            SUNDAY -> HijriCalendarUtils.hijriWeekDays[1]
            MONDAY -> HijriCalendarUtils.hijriWeekDays[2]
            TUESDAY -> HijriCalendarUtils.hijriWeekDays[3]
            WEDNESDAY -> HijriCalendarUtils.hijriWeekDays[4]
            THURSDAY -> HijriCalendarUtils.hijriWeekDays[5]
            FRIDAY -> HijriCalendarUtils.hijriWeekDays[6]
            else -> throw IllegalArgumentException()
        }

    override val monthLength: Int
        get() = HijriCalendarUtils.monthLength(year, month)

    override val isLeapYear: Boolean
        get() = HijriCalendarUtils.isHijriLeapYear(year)

    override var firstDayOfWeek: Int = SATURDAY
        set(value) {
            field = value
            setInternalFirstDayOfWeek(value)
        }

    override val calendarType: CalendarType
        get() = CalendarType.HIJRI

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
        if (amount == 0) {
            return
        }
        if (field < 0 || field > MILLISECOND) {
            throw IllegalArgumentException()
        }

        when (field) {
            YEAR -> set(hijriYear + amount, hijriMonth, hijriDayOfMonth)
            MONTH -> {
                if (amount > 0) {
                    set(hijriYear + (hijriMonth + amount) / 12, (hijriMonth + amount) % 12, hijriDayOfMonth)
                } else {
                    set(hijriYear - (12 - (hijriMonth + amount + 1)) / 12, (12 + (hijriMonth + amount)) % 12, hijriDayOfMonth)
                }
            }
            else -> {
                super.add(field, amount)
                invalidate()
            }
        }
    }

    override fun set(field: Int, value: Int) {
        if (field < 0 || field > MILLISECOND) {
            throw IllegalArgumentException()
        }
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
            DAY_OF_MONTH -> {
                dayOfMonth = value
            } // also DATE
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
                if (value > HijriCalendarUtils.yearLength(year)) {
                    throw IllegalArgumentException()
                } else {
                    HijriCalendarUtils.dayOfYear(year, value).let {
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

        hijriYear = year
        hijriMonth = month
        hijriDayOfMonth = dayOfMonth

        HijriCalendarUtils.hijriToGregorian(
                DateHolder(hijriYear, hijriMonth, hijriDayOfMonth)
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
            WEEK_OF_YEAR -> 52
            WEEK_OF_MONTH -> 6
            DAY_OF_MONTH -> 30
            DAY_OF_YEAR -> 355
            DAY_OF_WEEK_IN_MONTH -> 5
            else -> super.getMaximum(field)
        }
    }

    override fun getGreatestMinimum(field: Int): Int {
        return getMinimum(field)
    }

    override fun getLeastMaximum(field: Int): Int {
        return when (field) {
            WEEK_OF_YEAR -> 51
            WEEK_OF_MONTH -> 5
            DAY_OF_MONTH -> 29
            DAY_OF_YEAR -> 354
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
                    base.set(DAY_OF_YEAR, if (isLeapYear) 355 else 354)
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
            DAY_OF_YEAR -> if (isLeapYear) 355 else 354
            DAY_OF_WEEK_IN_MONTH -> 5
            else -> super.getActualMaximum(field)
        }
    }

    override fun invalidate() {
        HijriCalendarUtils.gregorianToHijri(
                DateHolder(
                        super.get(YEAR),
                        super.get(MONTH),
                        super.get(DAY_OF_MONTH)
                )
        ).also {
            hijriYear = it.year
            hijriMonth = it.month
            hijriDayOfMonth = it.dayOfMonth
        }
    }

    override fun calculateDayOfYear(): Int = HijriCalendarUtils.dayOfYear(year, month, dayOfMonth)

    // ---------------------------------------------------------------------------------------------

    override fun toCivil(): CivilCalendar = convertHijriToCivil(this)

    override fun toPersian(): PersianCalendar = convertHijriToPersian(this)

    override fun toHijri(): HijriCalendar = this

}
