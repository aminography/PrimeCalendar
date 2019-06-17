package com.aminography.primecalendar.sample

import com.aminography.primecalendar.civil.CivilCalendar
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
//        assertEquals(4, 2 + 2)

        val civil = CivilCalendar()
        civil.set(Calendar.YEAR, 2019)
        civil.set(Calendar.MONTH, 5)
        civil.set(Calendar.DAY_OF_MONTH, 17)
        println(civil.longDateString)

        civil.add(Calendar.WEEK_OF_YEAR, 14)
        println(civil.longDateString)

        civil.roll(Calendar.DAY_OF_WEEK, -3)
        println(civil.longDateString)
    }

}
