package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.util.Constant.LAST_CHECKED_POSITION
import com.jay.currencyconverter.util.StorageManager


class ChartCurrencyAdapter : RecyclerView.Adapter<BaseViewHolder<Nbu>>() {

    val chartCurrencyChosen: MutableLiveData<Nbu> = MutableLiveData()

    private var checkBoxList: MutableList<MaterialCheckBox> = mutableListOf()
    private val currencyList: MutableList<Nbu> = mutableListOf()
    private val currencies: Currencies = Currencies()
    private var currentChartCurrency: Nbu? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Nbu> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Nbu>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long =  position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<Nbu?>) {
        currencyList.clear()
        currencyList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    private inner class CurrencyVH(itemView: View) : BaseViewHolder<Nbu>(itemView) {

        private val currencyIcon: AppCompatImageView = itemView.findViewById(R.id.currency_image)
        private val currencyAbr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
        private val currencyName: AppCompatTextView = itemView.findViewById(R.id.currency_name)
        private val checkBox: MaterialCheckBox = itemView.findViewById(R.id.currency_choice_check_box)

        override fun bind(item: Nbu) {

            item.currencyAbbreviation?.let {
                currencyIcon.setImageDrawable(currencies.getCurrencyImageByAbr(it, itemView.context))
            }
            currencyAbr.text = item.currencyAbbreviation
            currencyName.text = item.currencyName

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
                if (currentChartCurrency != currencyList[layoutPosition]) {
                    chartCurrencyChosen.postValue(currencyList[layoutPosition])
                }
                currentChartCurrency = currencyList[layoutPosition]
            }
        }
    }

    private fun setAllCheckboxUnchecked(){
        checkBoxList.forEach { it.isChecked = false }
    }
}