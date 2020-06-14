package com.jay.currencyconverter.ui.adapter.viewHolder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency

open class NbuViewHolder(itemView: View) : BaseViewHolder<NbuCurrency>(itemView) {

    protected val currencyIcon: AppCompatImageView = itemView.findViewById(R.id.currency_image)
    protected val currencyAbr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
    protected val currencyName: AppCompatTextView = itemView.findViewById(R.id.currency_name)
    protected val checkBox: MaterialCheckBox = itemView.findViewById(R.id.currency_choice_check_box)

    override fun bind(item: NbuCurrency) {

        currencyIcon.setImageDrawable(item.getImage(itemView.context))
        currencyAbr.text = item.currencyAbbreviation
        currencyName.text = item.getName(itemView.context)
    }
}