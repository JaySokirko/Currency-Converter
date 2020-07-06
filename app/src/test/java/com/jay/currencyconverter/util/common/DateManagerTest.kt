package com.jay.currencyconverter.util.common

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DateManagerTest {

    private val dateManager = DateManager()

    @Test
    fun getDate() {
        val date: String = dateManager.getDate(0, dateManager.dateFormat)
        Assert.assertEquals(date, dateManager.currentDate)
    }
}