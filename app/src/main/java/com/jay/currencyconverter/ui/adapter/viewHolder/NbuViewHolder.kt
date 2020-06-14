package com.jay.currencyconverter.ui.adapter.viewHolder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu

open class NbuViewHolder(itemView: View) : BaseViewHolder<Nbu>(itemView) {

    protected val currencyIcon: AppCompatImageView = itemView.findViewById(R.id.currency_image)
    protected val currencyAbr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
    protected val currencyName: AppCompatTextView = itemView.findViewById(R.id.currency_name)
    protected val checkBox: MaterialCheckBox = itemView.findViewById(R.id.currency_choice_check_box)

    override fun bind(item: Nbu) {
        item.currencyAbbreviation?.let {
            currencyIcon.setImageDrawable(currencies.getCurrencyImageByAbr(it, itemView.context))
        }

        currencyAbr.text = item.currencyAbbreviation
        currencyName.text = item.currencyName
    }

    companion object {
        private val currencies: Currencies = Currencies()
    }
}