package com.aminography.primecalendar.sample

import com.aminography.primecalendar.persian.PersianCalendar
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun aaaaaa() {
        val persian = PersianCalendar(Locale.ENGLISH)
        persian.set(1398, 2, 24)
        println(persian.longDateString)

        persian.set(Calendar.MONTH, 8)
        println(persian.longDateString)
    }

}
