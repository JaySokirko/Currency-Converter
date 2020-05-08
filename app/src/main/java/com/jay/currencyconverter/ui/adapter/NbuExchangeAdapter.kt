package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import com.jay.currencyconverter.model.currencyExchange.currency.Currencies

class NbuExchangeAdapter : RecyclerView.Adapter<BaseViewHolder<Nbu>>() {

    private val nbuList: MutableList<Nbu> = ArrayList()
    private val currencies: Currencies = Currencies()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Nbu> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_nbu_exchange_rate, parent, false)

        return NbuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Nbu>, position: Int) {
        holder.bind(nbuList[position])
    }

    override fun getItemCount(): Int {
        return nbuList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
       return 0
    }

    fun setItems(currencies: List<Nbu?>) {
        nbuList.clear()
        nbuList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    inner class NbuViewHolder(itemView: View) : BaseViewHolder<Nbu>(itemView) {

        private val image: AppCompatImageView = itemView.findViewById(R.id.currency_image)
        private val abr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
        private val name: AppCompatTextView = itemView.findViewById(R.id.currency_name)
        private val rate: AppCompatTextView = itemView.findViewById(R.id.rate)

        override fun bind(item: Nbu) {
            item.currencyAbbreviation?.let {
                image.setImageDrawable(currencies.getCurrencyImageByAbr(it, itemView.context))
            }
            abr.text = item.currencyAbbreviation
            name.text = item.currencyName
            rate.text = item.rate.toString()
        }
    }
}