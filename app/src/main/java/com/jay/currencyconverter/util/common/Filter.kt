package com.jay.currencyconverter.util.common

class Filter<T> {

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