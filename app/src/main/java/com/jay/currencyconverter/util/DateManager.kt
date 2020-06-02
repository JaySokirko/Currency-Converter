package com.jay.currencyconverter.util

import android.content.Context
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import java.lang.IllegalStateException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateManager {

    val dateList: MutableList<String> = mutableListOf()
    val dayNameList: MutableList<String> = mutableListOf()
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyyMMdd")

    private val currentDate: String = dateFormat.format(Date())
    private val context: Context = BaseApplication.baseComponent.application.baseContext

    /**
     * Allows to get a short weekday name depends on an input date
     * @throws IllegalStateException
     * @param date the calendar date in which needs get a short weekday name
     * @param dateFormat the format which will be parsing to get a weekday. For instance "dd/MM/yyyy"
     * @return the short weekday name
     */
    @Throws(exceptionClasses = [IllegalStateException::class])
    fun getDayName(date: String, dateFormat: SimpleDateFormat) : String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(date)
        return when(calendar[Calendar.DAY_OF_WEEK]) {
            1 -> context.resources.getString(R.string.sun)
            2 -> context.resources.getString(R.string.mon)
            3 -> context.resources.getString(R.string.tue)
            4 -> context.resources.getString(R.string.wed)
            5 -> context.resources.getString(R.string.thu)
            6 -> context.resources.getString(R.string.fri)
            7 -> context.resources.getString(R.string.sat)
            else -> throw IllegalStateException("invalid number of a week day")
        }
    }

    /**
     * Allows to get a special date depending on the deviation from the current date.
     * @param amount the deviation from the current date.
     * For instance if today is 12.12.2020 and amount is "-2" the method will return 10.12.2020.
     * @param dateFormat the format in which the method will return the date
     * @return the formatted specific date
     */
    fun getDate(amount: Int, dateFormat: SimpleDateFormat) : String{
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, amount)
        return dateFormat.format(calendar.time)
    }

    init {
        dateList.add(currentDate)
        dateList.add(getDate(-1, dateFormat))
        dateList.add(getDate(-2, dateFormat))
        dateList.add(getDate(-3, dateFormat))
        dateList.add(getDate(-4, dateFormat))

        dayNameList.add(getDayName(currentDate, dateFormat))
        dayNameList.add(getDayName(getDate(-1, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-2, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-3, dateFormat), dateFormat))
        dayNameList.add(getDayName(getDate(-4, dateFormat), dateFormat))
    }
}