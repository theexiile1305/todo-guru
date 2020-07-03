package edu.hm.cs.ma.todoguru.task

import edu.hm.cs.ma.todoguru.database.PeriodConverter
import org.junit.Assert
import org.junit.Test
import java.time.Period

class PeriodConverterTest {

    @Test
    fun testToPeriod() {
        val periodConverter = PeriodConverter()
        val period = Period.of(2020, 1, 1)
        val periodString = String.format("P%dY%dM%dD", period.years, period.months, period.days)
        Assert.assertEquals(period, periodConverter.toPeriod(periodString))
    }

    @Test
    fun testNullToPeriod() {
        val periodConverter = PeriodConverter()
        Assert.assertNull(periodConverter.toPeriod(null))
    }

    @Test
    fun testEmptyStringToPeriod() {
        val periodConverter = PeriodConverter()
        Assert.assertNull(periodConverter.toPeriod(""))
    }

    @Test
    fun testToPeriodString() {
        val periodConverter = PeriodConverter()
        val period = Period.of(2020, 1, 1)
        val periodString = String.format("P%dY%dM%dD", period.years, period.months, period.days)
        Assert.assertEquals(periodString, periodConverter.toPeriodString(period))
    }

    @Test
    fun testNullToPeriodString() {
        val periodConverter = PeriodConverter()
        Assert.assertEquals("", periodConverter.toPeriodString(null))
    }
}
