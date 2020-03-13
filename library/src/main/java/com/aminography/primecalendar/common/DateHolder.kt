package com.aminography.primecalendar.common

/**
 * A simple class which bundles the year, month, and day of month in an object.
 *
 * @author aminography
 */
internal data class DateHolder(
    var year: Int,
    var month: Int,
    var dayOfMonth: Int
) {

    override fun toString(): String = "$year$delimiter$month$delimiter$dayOfMonth"

}