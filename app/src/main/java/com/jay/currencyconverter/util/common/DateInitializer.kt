package com.jay.currencyconverter.util.common

import com.jay.currencyconverter.util.common.DateManager.currentDate
import com.jay.currencyconverter.util.common.DateManager.dateFormat
import com.jay.currencyconverter.util.common.DateManager.getDate
import com.jay.currencyconverter.util.common.DateManager.getDayName

object DateInitializer {

    private val dateList: MutableList<String> = mutableListOf()
    private val dayNameList: MutableList<String> = mutableListOf()

    fun getDefaultDateList(): List<String> {
        dateList.clear()
        dateList.add(currentDate)
        dateList.add(getDate(-1, dateFormat))
        dateList.add(getDate(-2, dateFormat))
        dateList.add(getDate(-3, dateFormat))
        dateList.add(getDate(-4, dateFormat))
        return dateList
    }

    fun getDefaultDayNameList(): List<String> {
        dayNameList.clear()
        dayNameList.add(getDayName(currentDate, dateFormat))
        dayNameList.add(getDayName(getDate(-1, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-2, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-3, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-4, dateFormat), dateFormat))
        return dayNameList
    }

}