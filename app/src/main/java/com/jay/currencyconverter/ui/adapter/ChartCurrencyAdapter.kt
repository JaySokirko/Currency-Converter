package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.ui.adapter.viewHolder.AnimatedViewHolder
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder
import com.jay.currencyconverter.ui.adapter.viewHolder.NbuCurrencyBaseViewHolder
import com.jay.currencyconverter.util.common.Constant.LAST_CHECKED_POSITION
import com.jay.currencyconverter.util.common.StorageManager


class ChartCurrencyAdapter : RecyclerView.Adapter<NbuCurrencyBaseViewHolder>() {

    val chartCurrencyChosen: MutableLiveData<NbuCurrency> = MutableLiveData()

    private var checkBoxList: MutableList<MaterialCheckBox> = mutableListOf()
    private val currencyList: MutableList<NbuCurrency> = mutableListOf()
    private var currentChartCurrency: NbuCurrency? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NbuCurrencyBaseViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return ChartCurrencyVH(view)
    }

    override fun onBindViewHolder(holder: NbuCurrencyBaseViewHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long =  position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<NbuCurrency?>) {
        currencyList.clear()
        currencyList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    private inner class ChartCurrencyVH(itemView: View) : NbuCurrencyBaseViewHolder(itemView) {

        override fun bind(item: NbuCurrency) {
            super.bind(item)

            if (layoutPosition == StorageManager.getVariable(LAST_CHECKED_POSITION, default = 0)) {
                checkBox.isChecked = true
                currentChartCurrency = item
            }
            checkBoxList.add(checkBox)
        }

        init {
            itemView.setOnClickListener {
                StorageManager.saveVariable(LAST_CHECKED_POSITION, layoutPosition)
                setAllCheckboxUnchecked()
                checkBox.isChecked = true

                if (currentCurrencyNotSelected()) {
                    chartCurrencyChosen.postValue(currencyList[layoutPosition])
                    currentChartCurrency = currencyList[layoutPosition]
                }
            }
        }

        private fun currentCurrencyNotSelected(): Boolean{
            return currentChartCurrency != currencyList[layoutPosition]
        }

        private fun setAllCheckboxUnchecked(){
            checkBoxList.forEach { it.isChecked = false }
        }
    }

}