package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.model.exchangeRate.Currencies
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder

class NbuExchangeRateAdapter : RecyclerView.Adapter<BaseViewHolder<NbuCurrency>>() {

    private val nbuList: MutableList<NbuCurrency> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NbuCurrency> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_nbu_exchange_rate, parent, false)

        return NbuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NbuCurrency>, position: Int) {
        holder.bind(nbuList[position])
    }

    override fun getItemCount(): Int = nbuList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<NbuCurrency?>) {
        nbuList.clear()
        nbuList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    inner class NbuViewHolder(itemView: View) : BaseViewHolder<NbuCurrency>(itemView) {

        private val image: AppCompatImageView = itemView.findViewById(R.id.currency_image)
        private val abr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
        private val name: AppCompatTextView = itemView.findViewById(R.id.currency_name)
        private val rate: AppCompatTextView = itemView.findViewById(R.id.rate)

        override fun bind(item: NbuCurrency) {

            image.setImageDrawable(item.getImage(itemView.context))
            abr.text = item.currencyAbbreviation
            name.text = item.getName(itemView.context)
            rate.text = item.rate.toString()
        }
    }
}