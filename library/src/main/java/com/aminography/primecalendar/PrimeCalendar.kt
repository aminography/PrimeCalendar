package com.aminography.primecalendar

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.*
import com.aminography.primecalendar.common.operators.CalendarField
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.japanese.JapaneseCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.text.DateFormatSymbols
import java.util.*
import java.util.Calendar.*


/**
 * `PrimeCalendar` is an abstract class which defines common functions for each calendar-type.
 * These functions are the same as [Calendar] class.
 * It also provides methods for converting between a specific instant in time and a set of
 * calendar fields such as [YEAR], [MONTH], [DAY_OF_MONTH], [HOUR], and so on, and for
 * manipulating the calendar fields, such as getting the date of the next week.
 * An instant in time can be represented by a millisecond value.
 *
 * The constructor constructs a `PrimeCalendar` based on the current time
 * in the given time zone with the given locale.
 *
 * @param timeZone the given time zone.
 * @param locale the given locale.
 *
 * @author aminography
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class PrimeCalendar(
    timeZone: TimeZone,
    val locale: Locale
) : Comparable<PrimeCalendar> {

    /**
     * An internal instance of [Calendar] which is responsible to handle
     * calendar-type independent functionalities.
     */
    protected var internalCalendar = GregorianCalendar(timeZone, locale)

    /**
     * Internal field to hold year value based on current calendar-type.
     */
    protected var internalYear: Int = 0

    /**
     * Internal field to hold month value based on current calendar-type.
     */
    protected var internalMonth: Int = 0

    /**
     * Internal field to hold day of month value based on current calendar-type.
     */
    protected var internalDayOfMonth: Int = 0

    /**
     * A property to handle getter and setter for value of year.
     * Using this property to get and set the value is equivalent to call `get(Calendar.YEAR)`
     * and `set(Calendar.YEAR, value)`.
     */
    var year: Int
        get() = internalYear
        set(value) = set(value, internalMonth, internalDayOfMonth)

    /**
     * A property to handle getter and setter for value of month.
     * Using this property to get and set the value is equivalent to call `get(Calendar.MONTH)`
     * and `set(Calendar.MONTH, value)`.
     */
    var month: Int
        get() = internalMonth
        set(value) = set(internalYear, value, internalDayOfMonth)

    /**
     * A property to handle getter and setter for value of week of year.
     * Using this property to get and set the value is equivalent to call `get(Calendar.WEEK_OF_YEAR)`
     * and `set(Calendar.WEEK_OF_YEAR, value)`.
     */
    var weekOfYear: Int
        get() = get(WEEK_OF_YEAR)
        set(value) = set(WEEK_OF_YEAR, value)

    /**
     * A property to handle getter and setter for value of week of month.
     * Using this property to get and set the value is equivalent to call `get(Calendar.WEEK_OF_MONTH)`
     * and `set(Calendar.WEEK_OF_MONTH, value)`.
     */
    var weekOfMonth: Int
        get() = get(WEEK_OF_MONTH)
        set(value) = set(WEEK_OF_MONTH, value)

    /**
     * A property to handle getter and setter for value of day of month.
     * Using this property to get and set the value is equivalent to call `get(Calendar.DATE)`
     * and `set(Calendar.DATE, value)`.
     */
    var date: Int
        get() = internalDayOfMonth
        set(value) = set(internalYear, internalMonth, value)

    /**
     * A property to handle getter and setter for value of day of month.
     * Using this property to get and set the value is equivalent to call `get(Calendar.DAY_OF_MONTH)`
     * and `set(Calendar.DAY_OF_MONTH, value)`.
     */
    var dayOfMonth: Int
        get() = internalDayOfMonth
        set(value) = set(internalYear, internalMonth, value)

    /**
     * A property to handle getter and setter for value of day of year.
     * Using this property to get and set the value is equivalent to call `get(Calendar.DAY_OF_YEAR)`
     * and `set(Calendar.DAY_OF_YEAR, value)`.
     */
    var dayOfYear: Int
        get() = get(DAY_OF_YEAR)
        set(value) = set(DAY_OF_YEAR, value)

    /**
     * A property to handle getter and setter for value of day of week.
     * Using this property to get and set the value is equivalent to call `get(Calendar.DAY_OF_WEEK)`
     * and `set(Calendar.DAY_OF_WEEK, value)`.
     */
    var dayOfWeek: Int
        get() = get(DAY_OF_WEEK)
        set(value) = set(DAY_OF_WEEK, value)

    /**
     * A property to handle getter and setter for value of day of week in month.
     * Using this property to get and set the value is equivalent to call `get(Calendar.DAY_OF_WEEK_IN_MONTH)`
     * and `set(Calendar.DAY_OF_WEEK_IN_MONTH, value)`.
     */
    var dayOfWeekInMonth: Int
        get() = get(DAY_OF_WEEK_IN_MONTH)
        set(value) = set(DAY_OF_WEEK_IN_MONTH, value)

    /**
     * A property to handle getter and setter for value of hour.
     * Using this property to get and set the value is equivalent to call `get(Calendar.HOUR)`
     * and `set(Calendar.HOUR, value)`.
     */
    var hour: Int
        get() = get(HOUR)
        set(value) = set(HOUR, value)

    /**
     * A property to handle getter and setter for value of hour of day.
     * Using this property to get and set the value is equivalent to call `get(Calendar.HOUR_OF_DAY)`
     * and `set(Calendar.HOUR_OF_DAY, value)`.
     */
    var hourOfDay: Int
        get() = get(HOUR_OF_DAY)
        set(value) = set(HOUR_OF_DAY, value)

    /**
     * A property to handle getter and setter for value of minute.
     * Using this property to get and set the value is equivalent to call `get(Calendar.MINUTE)`
     * and `set(Calendar.MINUTE, value)`.
     */
    var minute: Int
        get() = get(MINUTE)
        set(value) = set(MINUTE, value)

    /**
     * A property to handle getter and setter for value of second.
     * Using this property to get and set the value is equivalent to call `get(Calendar.SECOND)`
     * and `set(Calendar.SECOND, value)`.
     */
    var second: Int
        get() = get(SECOND)
        set(value) = set(SECOND, value)

    /**
     * A property to handle getter and setter for value of millisecond.
     * Using this property to get and set the value is equivalent to call `get(Calendar.MILLISECOND)`
     * and `set(Calendar.MILLISECOND, value)`.
     */
    var millisecond: Int
        get() = get(MILLISECOND)
        set(value) = set(MILLISECOND, value)

    /**
     * A property which returns name of the current month.
     */
    abstract val monthName: String

    /**
     * A property which returns name of the current month in abbreviation manner.
     */
    abstract val monthNameShort: String

    /**
     * A property which returns name of the current week day.
     */
    abstract val weekDayName: String

    /**
     * A property which returns name of the current week day in abbreviation manner.
     */
    abstract val weekDayNameShort: String

    /**
     * A property which returns length of the current month.
     */
    abstract val monthLength: Int

    /**
     * A property which returns leap status of the current year.
     */
    abstract val isLeapYear: Boolean

    /**
     * A property which returns first day of week based on calendar-type.
     */
    abstract var firstDayOfWeek: Int

    /**
     * A property which returns current calendar-type.
     */
    abstract val calendarType: CalendarType

    /**
     * A property which returns a description of the current calendar time.
     *
     * For example:
     * `Friday, 14 June 2019`
     */
    val longDateString: String
        get() = weekDayName +
            "${comma(locale)} " +
            "${localizeNumber(locale, dayOfMonth)} " +
            "$monthName " +
            localizeNumber(locale, year)

    /**
     * A property which returns a short description of the current calendar time.
     *
     * For example:
     * `14/6/2019`
     */
    val shortDateString: String
        get() = normalize(locale, year) +
            delimiter +
            normalize(locale, month + 1) +
            delimiter +
            normalize(locale, dayOfMonth)

    /**
     * A property which returns month name and day of month in current calendar time.
     *
     * For example:
     * `June 14`
     */
    val monthDayString: String
        get() = "$monthName ${normalize(locale, dayOfMonth)}"

    // Open Functions ------------------------------------------------------------------------------

    /**
     * Returns the value of the given calendar field from the internal calendar instance.
     */
    open operator fun get(field: Int): Int {
        return internalCalendar.get(field)
    }

    /**
     * Sets the given [calendarField] to the given value in the internal calendar instance.
     *
     * @param calendarField the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range ([field &lt; 0 || field &gt;= FIELD_COUNT]).
     */
    open fun set(calendarField: CalendarField) {
        internalCalendar.set(calendarField.field, calendarField.amount)
    }

    /**
     * Sets the given calendar field to the given value in the internal calendar instance.
     *
     * @param field the given calendar field.
     * @param value the value to be set for the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range ([field &lt; 0 || field &gt;= FIELD_COUNT]).
     */
    open operator fun set(field: Int, value: Int) {
        internalCalendar.set(field, value)
    }

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], and [DAY_OF_MONTH]
     * in the internal calendar instance.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     */
    open fun set(year: Int, month: Int, dayOfMonth: Int) {
        internalCalendar.set(year, month, dayOfMonth)
    }

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], [DAY_OF_MONTH], [HOUR_OF_DAY],
     * and [MINUTE] in the internal calendar instance.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     */
    open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute)
    }

    /**
     * Sets the values for the calendar fields [YEAR], [MONTH], [DAY_OF_MONTH], [HOUR_OF_DAY],
     * [MINUTE], and [SECOND] in the internal calendar instance.
     *
     * @param year the value used to set the [YEAR] calendar field.
     * @param month the value used to set the [MONTH] calendar field. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the [DAY_OF_MONTH] calendar field.
     * @param hourOfDay the value used to set the [HOUR_OF_DAY] calendar field.
     * @param minute the value used to set the [MINUTE] calendar field.
     * @param second the value used to set the [SECOND] calendar field.
     */
    open fun set(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int) {
        internalCalendar.set(year, month, dayOfMonth, hourOfDay, minute, second)
    }

    /**
     * Adds or subtracts the specified amount of time to the given calendar field,
     * based on the calendar's rules.
     *
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field.
     */
    open fun add(field: Int, amount: Int) {
        internalCalendar.add(field, amount)
    }

    /**
     * Adds the specified (signed) amount to the specified calendar field
     * without changing larger fields.  A negative amount means to roll
     * down.
     *
     * @param field the calendar field.
     * @param amount the signed amount to add to the calendar field.
     */
    open fun roll(field: Int, amount: Int) {
        internalCalendar.roll(field, amount)
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
     */
    open fun getMinimum(field: Int): Int {
        return internalCalendar.getMinimum(field)
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
     */
    open fun getMaximum(field: Int): Int {
        return internalCalendar.getMaximum(field)
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
     */
    open fun getGreatestMinimum(field: Int): Int {
        return internalCalendar.getGreatestMinimum(field)
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
     */
    open fun getLeastMaximum(field: Int): Int {
        return internalCalendar.getLeastMaximum(field)
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
     */
    open fun getActualMinimum(field: Int): Int {
        return internalCalendar.getActualMinimum(field)
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
     */
    open fun getActualMaximum(field: Int): Int {
        return internalCalendar.getActualMaximum(field)
    }

    // Final Functions -----------------------------------------------------------------------------

    /**
     * Adds or subtracts (up/down) a single unit of time on the given time
     * field without changing larger fields. For example, to roll the current
     * date up by one day, you can achieve it by calling:
     * `roll(Calendar.DATE, true)`.
     * When rolling on the year or [Calendar.YEAR] field, it will roll the year
     * value in the range between 1 and the value returned by calling
     * `getMaximum(Calendar.YEAR)`.
     * When rolling on the month or [Calendar.MONTH] field, other fields like
     * date might conflict and, need to be changed. For instance,
     * rolling the month on the date 01/31/96 will result in 02/29/96.
     * When rolling on the hour-in-day or [Calendar.HOUR_OF_DAY] field, it will
     * roll the hour value in the range between 0 and 23, which is zero-based.
     *
     * @param field the time field.
     * @param up indicates if the value of the specified time field is to be
     * rolled up or rolled down. Use true if rolling up, false otherwise.
     *
     * @see add(int,int)
     * @see set(int,int)
     */
    fun roll(field: Int, up: Boolean) {
        roll(field, if (up) +1 else -1)
    }

    /**
     * Sets the time zone with the given time zone value.
     *
     * @param zone the given time zone.
     */
    fun setTimeZone(zone: TimeZone) {
        internalCalendar.timeZone = zone
        invalidate()
    }

    /**
     * Gets the time zone.
     *
     * @return the time zone object associated with this calendar.
     */
    fun getTimeZone(): TimeZone {
        return internalCalendar.timeZone
    }

    /**
     * A property to hold this Calendar's time value in milliseconds.
     */
    var timeInMillis: Long = 0
        /**
         * Returns this Calendar's time value in milliseconds.
         *
         * @return the current time as UTC milliseconds from the epoch.
         *
         * @see getTime()
         */
        get() = internalCalendar.timeInMillis
        /**
         * Sets this Calendar's current time from the given long value.
         *
         * @param value the new time in UTC milliseconds from the epoch.
         * @see setTime(Date)
         */
        set(value) {
            field = value
            internalCalendar.timeInMillis = value
            invalidate()
        }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an instance of [CivilCalendar] which is equivalent to the time of the current calendar.
     */
    fun toCivil(): CivilCalendar =
        CivilCalendar().also { it.timeInMillis = timeInMillis }

    /**
     * Returns an instance of [PersianCalendar] which is equivalent to the time of the current calendar.
     */
    fun toPersian(): PersianCalendar =
        PersianCalendar().also { it.timeInMillis = timeInMillis }

    /**
     * Returns an instance of [HijriCalendar] which is equivalent to the time of the current calendar.
     */
    fun toHijri(): HijriCalendar =
        HijriCalendar().also { it.timeInMillis = timeInMillis }

    /**
     * Returns an instance of [JapaneseCalendar] which is equivalent to the time of the current calendar.
     */
    fun toJapanese(): JapaneseCalendar =
        JapaneseCalendar().also { it.timeInMillis = timeInMillis }

    // ---------------------------------------------------------------------------------------------

    /**
     * Converts (to civil) and stores the value of internal date fields to the internal calendar.
     */
    protected abstract fun store()

    /**
     * Restores the date values from internal calendar then set to internal date fields after
     * conversion to correct date type.
     */
    protected abstract fun invalidate()

    /**
     * Fills the symbol names based on current calendar-type.
     *
     * @param symbols the [DateFormatSymbols] to fill the names in it.
     */
    protected abstract fun configSymbols(symbols: DateFormatSymbols)

    /**
     * Returns month length regarding to the leap status of the year.
     *
     * @param year the year of month to calculate the length.
     * @param month the month to calculate its length.
     * @return month length.
     */
    internal abstract fun monthLength(year: Int, month: Int): Int

    /**
     * Returns year length regarding to the leap status of the year.
     *
     * @param year the year to calculate its length.
     * @return year length.
     */
    internal abstract fun yearLength(year: Int): Int

    /**
     * Returns day of year based on current year, month, and day of month
     *
     * @return day of year.
     */
    internal abstract fun dayOfYear(): Int

    /**
     * Calculates and returns year, month, and day of month for the [dayOfYear] in [year].
     *
     * @param year the year to explore in it.
     * @param dayOfYear the number of day in the [year].
     * @return A bundle of year, month, and day of month.
     */
    internal abstract fun dayOfYear(year: Int, dayOfYear: Int): DateHolder

    /**
     * Sets the day which should be considered as start of week.
     *
     * @param firstDayOfWeek the value of week start day that can be [Calendar.SUNDAY],
     * [Calendar.MONDAY], and so on.
     */
    protected fun setInternalFirstDayOfWeek(firstDayOfWeek: Int) {
        internalCalendar.firstDayOfWeek = firstDayOfWeek
    }

    /**
     * Returns week of month based on current year, month, and day of month.
     *
     * @return week of month.
     */
    internal fun weekOfMonth(): Int {
        return CalendarFactory.newInstance(calendarType).let { base ->
            base.set(year, month, 1)
            val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
            weekNumber(dayOfMonth, baseDayOfWeek)
        }
    }

    /**
     * Returns week of year based on current year, month, and day of month.
     *
     * @return week of year.
     */
    internal fun weekOfYear(): Int {
        return CalendarFactory.newInstance(calendarType).let { base ->
            base.set(year, 0, 1)
            val baseDayOfWeek = adjustDayOfWeekOffset(base[DAY_OF_WEEK])
            weekNumber(dayOfYear(), baseDayOfWeek)
        }
    }

    /**
     * Returns offset of day based on [firstDayOfWeek].
     *
     * @param dayOfWeek day of week value such as [Calendar.SUNDAY], and so on.
     * @return day offset, starts from 0
     */
    protected fun adjustDayOfWeekOffset(dayOfWeek: Int): Int {
        val day = if (dayOfWeek < firstDayOfWeek) dayOfWeek + 7 else dayOfWeek
        return (day - firstDayOfWeek) % 7
    }

    /**
     * Calculates week number of a day regarding to a base offset.
     */
    private fun weekNumber(day: Int, baseOffset: Int): Int {
        val dividend = (baseOffset + day) / 7
        val remainder = (baseOffset + day) % 7
        return dividend + if (remainder > 0) 1 else 0
    }

    internal fun adjustWith(calendar: Calendar) {
        internalCalendar.timeInMillis = calendar.timeInMillis
        firstDayOfWeek = calendar.firstDayOfWeek
        invalidate()
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a [Date] object representing this
     * [Calendar]'s time value (millisecond offset from the <a
     * href="#Epoch">Epoch</a>").
     *
     * @return a [Date] representing the time value.
     *
     * @see setTime(Date)
     * @see timeInMillis
     */
    fun getTime(): Date {
        return Date(timeInMillis)
    }

    /**
     * Sets this Calendar's time with the given [Date].
     *
     * Note: Calling `setTime()` with
     * `Date(Long.MAX_VALUE)` or `Date(Long.MIN_VALUE)`
     * may yield incorrect field values from `get()`.
     *
     * @param date the given Date.
     *
     * @see getTime()
     * @see timeInMillis
     */
    fun setTime(date: Date) {
        timeInMillis = date.time
    }

    /**
     * Sets all the calendar field values and the time value
     * (millisecond offset from the <a href="#Epoch">Epoch</a>) of
     * this [Calendar] undefined. This means that [isSet] will return `false` for all the
     * calendar fields, and the date and time calculations will treat
     * the fields as if they had never been set. A
     * [Calendar] implementation class may use its specific
     * default field values for date/time calculations. For example,
     * [GregorianCalendar] uses 1970 if the
     * [YEAR] field value is undefined.
     *
     * @see clear(int)
     */
    fun clear() {
        internalCalendar.clear()
        invalidate()
    }

    /**
     * Sets the given calendar field value and the time value
     * (millisecond offset from the <a href="#Epoch">Epoch</a>) of
     * this `Calendar` undefined. This means that
     * [isSet] will return `false`, and
     * the date and time calculations will treat the field as if it
     * had never been set. A [Calendar] implementation
     * class may use the field's specific default value for date and
     * time calculations.
     *
     * The [HOUR_OF_DAY], [HOUR] and [AM_PM]
     * fields are handled independently and the resolution rule for the time of
     * day is applied. Clearing one of the fields doesn't reset
     * the hour of day value of this [Calendar]. Use [set]
     * `set(Calendar.HOUR_OF_DAY, 0)` to reset the hour
     * value.
     *
     * @param field the calendar field to be cleared.
     *
     * @see clear()
     */
    fun clear(field: Int) {
        internalCalendar.clear(field)
        invalidate()
    }

    /**
     * Determines if the given calendar field has a value set,
     * including cases that the value has been set by internal fields
     * calculations triggered by a [get] method call.
     *
     * @return `true` if the given calendar field has a value set;
     * `false` otherwise.
     */
    fun isSet(field: Int): Boolean {
        return internalCalendar.isSet(field)
    }

    /**
     * Returns the string representation of the calendar
     * `field` value in the given `style` and
     * `locale`.  If no string representation is
     * applicable, `null` is returned. This method calls
     * [get] to get the calendar
     * `field` value if the string representation is
     * applicable to the given calendar `field`.
     *
     * For example, if this `Calendar` is a
     * `GregorianCalendar` and its date is 2005-01-01, then
     * the string representation of the [MONTH] field would be
     * "January" in the long style in an English locale or "Jan" in
     * the short style. However, no string representation would be
     * available for the [DAY_OF_MONTH] field, and this method
     * would return `null`.
     *
     * <p>The default implementation supports the calendar fields for
     * which a [DateFormatSymbols] has names in the given
     * `locale`.
     *
     * @param field
     *        the calendar field for which the string representation
     *        is returned
     * @param style
     *        the style applied to the string representation; one of
     *        [Calendar.SHORT] or [Calendar.LONG].
     * @param locale
     *        the locale for the string representation
     * @return the string representation of the given
     *        `field` in the given `style`, or
     *        `null` if no string representation is
     *        applicable.
     * @exception IllegalArgumentException
     *        if `field` or `style` is invalid,
     *        or if this `Calendar` is non-lenient and any
     *        of the calendar fields have invalid values
     * @exception NullPointerException if `locale` is null
     */
    fun getDisplayName(field: Int, style: Int, locale: Locale): String? {
        if (!checkDisplayNameParams(field, style, ALL_STYLES, LONG, locale, ERA, MONTH, DAY_OF_WEEK, AM_PM)) {
            return null
        }
        val symbols = DateFormatSymbols.getInstance(locale)
        configSymbols(symbols)

        return getFieldStrings(field, style, symbols)?.let {
            val fieldValue = get(field)
            if (fieldValue < it.size) it[fieldValue]
            else null
        }
    }

    /**
     * Returns a [Map] containing all names of the calendar
     * `field` in the given `style` and
     * `locale` and their corresponding field values.
     *
     * @param field
     *        the calendar field for which the display names are returned
     * @param style
     *        the style applied to the display names; one of [Calendar.SHORT],
     *        [Calendar.LONG], or [Calendar.ALL_STYLES].
     * @param locale
     *        the locale for the display names
     * @return a [Map] containing all display names in
     *        `style` and `locale` and their
     *        field values, or `null` if no display names
     *        are defined for `field`
     * @exception IllegalArgumentException
     *        if `field` or `style` is invalid,
     *        or if this `Calendar` is non-lenient and any
     *        of the calendar fields have invalid values
     * @exception NullPointerException if `locale` is null
     */
    fun getDisplayNames(field: Int, style: Int, locale: Locale): Map<String, Int>? {
        if (!checkDisplayNameParams(field, style, ALL_STYLES, LONG, locale, ERA, MONTH, DAY_OF_WEEK, AM_PM)) {
            return null
        }
        if (style == ALL_STYLES) { // ALL_STYLES
            val shortNames = getDisplayNamesImpl(field, SHORT, locale)
            if (field == ERA || field == AM_PM) {
                return shortNames
            }
            val longNames = getDisplayNamesImpl(field, LONG, locale)
            if (shortNames == null) {
                return longNames
            }
            if (longNames != null) {
                shortNames.putAll(longNames)
            }
            return shortNames
        }
        return getDisplayNamesImpl(field, style, locale)
    }

    private fun getDisplayNamesImpl(field: Int, style: Int, locale: Locale): MutableMap<String, Int>? {
        val symbols = DateFormatSymbols.getInstance(locale)
        configSymbols(symbols)

        return getFieldStrings(field, style, symbols)?.let {
            HashMap<String, Int>().also { names ->
                for (i in it.indices) {
                    if (it[i].isEmpty()) continue
                    names[it[i]] = i
                }
            }
        }
    }

    private fun checkDisplayNameParams(field: Int, style: Int, minStyle: Int, maxStyle: Int, locale: Locale?, vararg fields: Int): Boolean {
        if (field < 0 || field >= FIELD_COUNT || style < minStyle || style > maxStyle) {
            throw IllegalArgumentException()
        }
        if (locale == null) {
            throw NullPointerException()
        }
        return isFieldSet(fields, field)
    }

    private fun isFieldSet(fields: IntArray, field: Int): Boolean {
        return fields.contains(field)
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

    /**
     * Returns whether this [PrimeCalendar] represents a time
     * before the time represented by the specified [whenCalendar].
     *
     * @param whenCalendar the [PrimeCalendar] to be compared
     * @return `true` if the time of this `Calendar` is before the time
     * represented by [whenCalendar]; `false` otherwise.
     *
     * @see compareTo(Calendar)
     */
    fun before(whenCalendar: Any): Boolean {
        return whenCalendar is PrimeCalendar && compareTo(whenCalendar) < 0
    }

    /**
     * Returns whether this `Calendar` represents a time
     * after the time represented by the specified [whenCalendar].
     *
     * @param whenCalendar the [PrimeCalendar] to be compared
     * @return `true` if the time of this `Calendar` is after the time
     * represented by [whenCalendar]; `false` otherwise.
     *
     * @see compareTo(Calendar)
     */
    fun after(whenCalendar: Any): Boolean {
        return whenCalendar is PrimeCalendar && compareTo(whenCalendar) > 0
    }

    /**
     * Compares the time values (millisecond offsets from the <a
     * href="#Epoch">Epoch</a>) represented by two `Calendar` objects.
     *
     * @param other the `Calendar` to be compared.
     * @return the value `0` if the time represented by the argument
     * is equal to the time represented by this `Calendar`; a value
     * less than `0` if the time of this `Calendar` is
     * before the time represented by the argument; and a value greater than
     * `0` if the time of this `Calendar` is after the
     * time represented by the argument.
     *
     * @exception NullPointerException if the specified `Calendar` is `null`.
     * @exception IllegalArgumentException if the time value of the
     * specified `Calendar` object can't be obtained due to
     * any invalid calendar values.
     */
    override operator fun compareTo(other: PrimeCalendar): Int {
        return compareTo(other.timeInMillis)
    }

    private operator fun compareTo(otherTime: Long): Int {
        val thisTime = timeInMillis
        return if (thisTime > otherTime) 1 else if (thisTime == otherTime) 0 else -1
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a copy of this object.
     */
    fun clone(): PrimeCalendar {
        return CalendarFactory.newInstance(calendarType, internalCalendar.timeZone, locale).also {
            it.internalCalendar = internalCalendar.clone() as GregorianCalendar
            it.firstDayOfWeek = firstDayOfWeek
            it.invalidate()
        }
    }

    /**
     * Compares this `Calendar` to the specified
     * `Object`. The result is `true` if and only if
     * the argument is a `PrimeCalendar` object of the same calendar
     * system that represents the same time value.
     *
     * @param other the object to compare with.
     * @return `true` if this object is equal to `obj`;
     * `false` otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        } else if (other is PrimeCalendar) {
            return internalCalendar == other.internalCalendar
        }
        return false
    }

    /**
     * Returns a hash code for this calendar.
     *
     * @return a hash code value for this object.
     */
    override fun hashCode(): Int {
        return internalCalendar.hashCode()
    }

    /**
     * Return a string representation of this calendar. This method
     * is intended to be used only for debugging purposes, and the
     * format of the returned string may vary between implementations.
     * The returned string may be empty.
     *
     * @return  a string representation of this calendar.
     */
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

        /**
         * Returns name of o field based on its value.
         */
        fun fieldName(field: Int): String {
            return FIELD_NAME[field]
        }
    }

}
