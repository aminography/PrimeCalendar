package com.aminography.primecalendar.common

/**
 * @author aminography
 */
internal data class DateHolder(
        var year: Int,
        var month: Int,
        var dayOfMonth: Int
) {

    override fun toString(): String = "$year$delimiter$month$delimiter$dayOfMonth"

}