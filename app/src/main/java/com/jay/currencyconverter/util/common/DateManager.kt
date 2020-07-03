package com.jay.currencyconverter.util.common

import android.content.Context
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

class DateManager {

    var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyyMMdd")

    /**
     * Returns current date in format yyyyMMdd. For example 20200703
     */
    val currentDate: String = dateFormat.format(Date())

    /**
     * Allows to get a short weekday name depends on an input date
     * @throws IllegalStateException
     * @param date the calendar date in which needs get the short weekday name
     * @param dateFormat the format which will be parsing to get the weekday.
     * For instance "dd/MM/yyyy"
     * @return the short weekday name
     */
    @Throws(exceptionClasses = [IllegalStateException::class, NullPointerException::class])
    fun getDayName(
        context: Context,
        date: String,
        dateFormat: SimpleDateFormat = this.dateFormat
    ) : String {
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
            else -> throw IllegalStateException("invalid number of the week day")
        }
    }

    /**
     * Allows to get a special date depending on the deviation from the current date.
     * @param amount the deviation from the current date.
     * For instance if today is 12.12.2020 and amount is "-2" the method will return 10122020
     * which means 10.12.2020
     * @param dateFormat the format in which the method will return the date
     * @return the formatted specific date
     */
    fun getDate(amount: Int, dateFormat: SimpleDateFormat) : String{
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, amount)
        return dateFormat.format(calendar.time)
    }
}