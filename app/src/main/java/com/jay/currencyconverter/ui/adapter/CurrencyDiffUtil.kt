package com.jay.currencyconverter.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.Currency

class CurrencyDiffUtil : DiffUtil.Callback() {

    private val context: Context = BaseApplication.baseComponent.application.baseContext
    private val oldList: MutableList<Currency> = mutableListOf()
    private val newList: MutableList<Currency> = mutableListOf()

    fun setData(oldList: List<Currency>, newList: List<Currency>) {
        this.oldList.apply { clear(); addAll(oldList) }
        this.newList.apply { clear(); addAll(newList) }
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getAbr(context) == newList[newItemPosition].getAbr(context)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getAbr(context) == newList[newItemPosition].getAbr(context)
    }
}