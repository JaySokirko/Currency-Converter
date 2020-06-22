package com.jay.currencyconverter.ui.adapter.diffUtil

import com.jay.currencyconverter.model.exchangeRate.Currency

class CurrencyDiffUtil : BaseDiffUtil<Currency>() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}