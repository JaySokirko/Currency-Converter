package com.jay.currencyconverter.util.common

import android.content.Context
import com.jay.currencyconverter.BaseApplication

class Filter<T> {

    val context: Context = BaseApplication.baseComponent.application.baseContext

    fun getFilteredResult(
        search: String,
        initialList: MutableList<T>,
        predicate: (item: T) -> Boolean
    ): MutableList<T> {

        val filteredList: MutableList<T> = mutableListOf()
        return if (search.isBlank()) { initialList }
        else {
            for (item: T in initialList) {
                if (predicate(item)) {
                    filteredList.add(item)
                }
            }
            if (filteredList.isEmpty()) { initialList }
            else filteredList
        }
    }
}