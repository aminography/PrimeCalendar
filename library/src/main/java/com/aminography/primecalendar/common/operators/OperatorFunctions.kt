//package com.aminography.primecalendar.common.operators
//
//import com.aminography.primecalendar.PrimeCalendar
//import java.util.*
//
///**
// * @author aminography
// */
//
//operator fun PrimeCalendar.plus(unit: PrimeUnit): PrimeCalendar {
//    return clone().also {
//        it.add(unit.field, unit.value)
//    }
//}
//
//operator fun PrimeCalendar.plusAssign(unit: PrimeUnit) {
//    add(unit.field, unit.value)
//}
//
//operator fun PrimeCalendar.minus(unit: PrimeUnit): PrimeCalendar {
//    return clone().also {
//        it.add(unit.field, -unit.value)
//    }
//}
//
//operator fun PrimeCalendar.minusAssign(unit: PrimeUnit) {
//    add(unit.field, -unit.value)
//}
//
//operator fun PrimeCalendar.rem(unit: PrimeUnit): PrimeCalendar {
//    return clone().also {
//        it.roll(unit.field, unit.value)
//    }
//}
//
//operator fun PrimeCalendar.remAssign(unit: PrimeUnit) {
//    roll(unit.field, unit.value)
//}
//
//// -------------------------------------------------------------------------------------------------
//
//abstract class PrimeUnit {
//    abstract val field: Int
//    abstract val value: Int
//}
//
//data class YEAR(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.YEAR
//}
//
//data class MONTH(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.MONTH
//}
//
//data class WEEK_OF_YEAR(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.WEEK_OF_YEAR
//}
//
//data class WEEK_OF_MONTH(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.WEEK_OF_MONTH
//}
//
//data class DATE(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.DATE
//}
//
//data class DAY_OF_MONTH(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.DAY_OF_MONTH
//}
//
//data class DAY_OF_YEAR(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.DAY_OF_YEAR
//}
//
//data class DAY_OF_WEEK(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.DAY_OF_WEEK
//}
//
//data class DAY_OF_WEEK_IN_MONTH(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.DAY_OF_WEEK_IN_MONTH
//}
//
//data class HOUR(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.HOUR
//}
//
//data class HOUR_OF_DAY(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.HOUR_OF_DAY
//}
//
//data class MINUTE(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.MINUTE
//}
//
//data class SECOND(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.SECOND
//}
//
//data class MILLISECOND(override val value: Int) : PrimeUnit() {
//    override val field: Int = Calendar.MILLISECOND
//}
