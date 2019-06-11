package com.aminography.primecalendar.base

import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.common.IConverter
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*


/**
 * Short Descriptions
 *
 * More Descriptions
 *
 * @constructor
 * @author aminography
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseCalendar : IConverter {

    protected var internalCalendar = GregorianCalendar(TimeZone.getDefault(), Locale.getDefault())

    protected var internalYear: Int = 0
    protected var internalMonth: Int = 0
    protected var internalDayOfMonth: Int = 0

    abstract var year: Int

    abstract var month: Int

    abstract var dayOfMonth: Int

    abstract val monthName: String

    abstract val weekDayName: String

    abstract val monthLength: Int

    abstract val isLeapYear: Boolean

    abstract val firstDayOfWeek: Int

    abstract val calendarType: CalendarType

    val longDateString: String
        get() = "$weekDayName,  $dayOfMonth  $monthName  $year"

    val shortDateString: String
        get() = normalize(year) + delimiter + normalize(month + 1) + delimiter + normalize(dayOfMonth)

    val monthDayString: String
        get() = monthName + " " + normalize(dayOfMonth)

    // Open Functions ------------------------------------------------------------------------------

    open fun get(field: Int): Int {
        return internalCalendar.get(field)
    }

    open fun add(field: Int, amount: Int) {
        internalCalendar.add(field, amount)
    }

    open fun set(field: Int, value: Int) {
        internalCalendar.set(field, value)
    }

    open fun set(year: Int, month: Int, dayOfMonth: Int) {
        internalCalendar.set(year, month, dayOfMonth)
    }

    open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute)
    }

    open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
        internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute, second)
    }

    open fun getMinimum(field: Int): Int {
        return internalCalendar.getMinimum(field)
    }

    open fun getMaximum(field: Int): Int {
        return internalCalendar.getMaximum(field)
    }

    open fun getGreatestMinimum(field: Int): Int {
        return internalCalendar.getGreatestMinimum(field)
    }

    open fun getLeastMaximum(field: Int): Int {
        return internalCalendar.getLeastMaximum(field)
    }

    open fun getActualMinimum(field: Int): Int {
        return internalCalendar.getActualMinimum(field)
    }

    open fun getActualMaximum(field: Int): Int {
        return internalCalendar.getActualMaximum(field)
    }

    fun roll(field: Int, up: Boolean) {
        roll(field, if (up) +1 else -1)
    }

    open fun roll(field: Int, amount: Int) {
        internalCalendar.roll(field, amount)
    }

    // Final Functions -----------------------------------------------------------------------------

    fun setTimeZone(zone: TimeZone) {
        internalCalendar.timeZone = zone
        invalidate()
    }

    fun getTimeZone(): TimeZone {
        return internalCalendar.timeZone
    }

    var timeInMillis: Long = 0
        get() = internalCalendar.timeInMillis
        set(value) {
            field = value
            internalCalendar.timeInMillis = value
            invalidate()
        }

    // ---------------------------------------------------------------------------------------------

    protected abstract fun store()

    protected abstract fun invalidate()

    internal abstract fun monthLength(year: Int, month: Int): Int

    internal abstract fun yearLength(year: Int): Int

    internal abstract fun dayOfYear(): Int

    internal abstract fun dayOfYear(year: Int, dayOfYear: Int): DateHolder

    protected fun setInternalFirstDayOfWeek(firstDayOfWeek: Int) {
        internalCalendar.firstDayOfWeek = firstDayOfWeek
    }

    // https://kotlinlang.org/docs/reference/kotlin-doc.html
    /**
     * Returns offset of day based on [firstDayOfWeek]
     * @param dayOfWeek standard day of week value defined in [java.util.Calendar]
     * @return day offset, starts from 0
     */
    protected fun adjustDayOfWeekOffset(dayOfWeek: Int): Int {
        val day = if (dayOfWeek < firstDayOfWeek) dayOfWeek + 7 else dayOfWeek
        return (day - firstDayOfWeek) % 7
    }

    private fun weekNumber(day: Int, baseOffset: Int): Int {
        val dividend = (baseOffset + day) / 7
        val remainder = (baseOffset + day) % 7
        return dividend + if (remainder > 0) 1 else 0
    }

    internal fun weekOfMonth(): Int {
        CalendarFactory.newInstance(calendarType).also { base ->
            base.set(year, month, 1)
            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
            return weekNumber(dayOfMonth, baseDayOfWeek)
        }
    }

    internal fun weekOfYear(): Int {
        CalendarFactory.newInstance(calendarType).also { base ->
            base.set(year, 0, 1)
            val baseDayOfWeek = adjustDayOfWeekOffset(base.get(DAY_OF_WEEK))
            return weekNumber(dayOfYear(), baseDayOfWeek)
        }
    }

    // ---------------------------------------------------------------------------------------------

    fun getTime(): Date {
        return Date(timeInMillis)
    }

    fun setTime(date: Date) {
        timeInMillis = date.time
    }

    fun clear() {
        internalCalendar.clear()
        invalidate()
    }

    fun clear(field: Int) {
        internalCalendar.clear(field)
        invalidate()
    }

    fun isSet(field: Int): Boolean {
        return internalCalendar.isSet(field)
    }

//    fun getDisplayName(field: Int, style: Int, locale: Locale): String

    fun getDisplayName(field: Int, style: Int, locale: Locale): String? {
        if (!checkDisplayNameParams(field, style, ALL_STYLES, LONG, locale, ERA_MASK or MONTH_MASK or DAY_OF_WEEK_MASK or AM_PM_MASK)) {
            return null
        }
        val symbols = DateFormatSymbols.getInstance(locale)
        getFieldStrings(field, style, symbols)?.let {
            val fieldValue = get(field)
            if (fieldValue < it.size) {
                return it[fieldValue]
            }
        }
        return null
    }

    fun checkDisplayNameParams(field: Int, style: Int, minStyle: Int, maxStyle: Int, locale: Locale?, fieldMask: Int): Boolean {
        if (field < 0 || field >= FIELD_COUNT || style < minStyle || style > maxStyle) {
            throw IllegalArgumentException()
        }
        if (locale == null) {
            throw NullPointerException()
        }
        return isFieldSet(fieldMask, field)
    }

    private fun getFieldStrings(field: Int, style: Int, symbols: DateFormatSymbols): Array<String>? {
        return when (field) {
            ERA -> symbols.eras
            MONTH -> if (style == LONG) symbols.months else symbols.shortMonths
            DAY_OF_WEEK -> if (style == LONG) symbols.weekdays else symbols.shortWeekdays
            AM_PM -> symbols.amPmStrings
            else -> null
        }
    }

    fun before(whenCalendar: BaseCalendar): Boolean {
        return compareTo(whenCalendar) < 0
    }

    fun after(whenCalendar: BaseCalendar): Boolean {
        return compareTo(whenCalendar) > 0
    }

    operator fun compareTo(anotherCalendar: BaseCalendar): Int {
        return compareTo(anotherCalendar.timeInMillis)
    }

    private operator fun compareTo(t: Long): Int {
        val thisTime = timeInMillis
        return if (thisTime > t) 1 else if (thisTime == t) 0 else -1
    }

    fun clone(): BaseCalendar {
        return CalendarFactory.newInstance(calendarType).also {
            it.internalCalendar = internalCalendar.clone() as GregorianCalendar
            it.invalidate()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        } else if (other is BaseCalendar) {
            return internalCalendar == other.internalCalendar
        }
        return false
    }

    override fun hashCode(): Int {
        return internalCalendar.hashCode()
    }

    override fun toString(): String {
        return super.toString().apply {
            "${substring(0, length - 1)}, Date=$shortDateString]"
        }
    }

    companion object {
        private val FIELD_NAME = arrayOf(
                "ERA",
                "YEAR",
                "MONTH",
                "WEEK_OF_YEAR",
                "WEEK_OF_MONTH",
                "DAY_OF_MONTH",
                "DAY_OF_YEAR",
                "DAY_OF_WEEK",
                "DAY_OF_WEEK_IN_MONTH",
                "AM_PM",
                "HOUR",
                "HOUR_OF_DAY",
                "MINUTE",
                "SECOND",
                "MILLISECOND",
                "ZONE_OFFSET",
                "DST_OFFSET"
        )

        fun fieldName(field: Int): String {
            return FIELD_NAME[field]
        }
    }

}
