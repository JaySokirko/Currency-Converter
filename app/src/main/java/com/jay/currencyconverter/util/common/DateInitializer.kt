package com.jay.currencyconverter.util.common

import android.content.Context

class DateInitializer {

    private val dateList: MutableList<String> = mutableListOf()
    private val dayNameList: MutableList<String> = mutableListOf()
    private var manager: DateManager = DateManager()

    fun getDefaultDateList(): List<String> {
        dateList.clear()
        dateList.add(manager.currentDate)
        dateList.add(manager.getDate(-1, manager.dateFormat))
        dateList.add(manager.getDate(-2, manager.dateFormat))
        dateList.add(manager.getDate(-3, manager.dateFormat))
        dateList.add(manager.getDate(-4, manager.dateFormat))
        return dateList
    }

    fun getDefaultDayNameList(context: Context): List<String> {
        dayNameList.clear()
        dayNameList.add(manager.getDayName(context, manager.currentDate, manager.dateFormat))
        dayNameList.add(manager.getDayName(context, manager.getDate(-1, manager.dateFormat), manager.dateFormat))
        dayNameList.add(manager.getDayName(context, manager.getDate(-2, manager.dateFormat), manager.dateFormat))
        dayNameList.add(manager.getDayName(context, manager.getDate(-3, manager.dateFormat), manager.dateFormat))
        dayNameList.add(manager.getDayName(context, manager.getDate(-4, manager.dateFormat), manager.dateFormat))
        return dayNameList
    }

}