package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.ui.adapter.diffUtil.CurrencyDiffUtil
import com.jay.currencyconverter.ui.adapter.viewHolder.AnimatedViewHolder

class NbuExchangeRateAdapter : AnimatedRecyclerAdapter<NbuCurrency>(), LifecycleObserver {

    private val nbuList: MutableList<NbuCurrency> = ArrayList()
    private val oldList: MutableList<NbuCurrency> = ArrayList()
    private val diffUtil = CurrencyDiffUtil()

    init {
        onAdapterDataChangeObserver()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimatedViewHolder<NbuCurrency> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_nbu_exchange_rate, parent, false)

        return NbuViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimatedViewHolder<NbuCurrency>, position: Int) {
        holder.bind(nbuList[position])
    }

    override fun getItemCount(): Int = nbuList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<NbuCurrency?>) {
        diffUtil.setData(oldList = nbuList, newList = currencies.filterNotNull())
        nbuList.apply { clear(); addAll(currencies.filterNotNull()) }
        oldList.addAll(currencies.filterNotNull())
        DiffUtil.calculateDiff(diffUtil, true).dispatchUpdatesTo(this)
    }

    private fun onAdapterDataChangeObserver() {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                smoothScrollToTop()
            }
        })
    }

    inner class NbuViewHolder(itemView: View) : AnimatedViewHolder<NbuCurrency>(itemView) {

        private val image: AppCompatImageView = itemView.findViewById(R.id.currency_image)
        private val abr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
        private val name: AppCompatTextView = itemView.findViewById(R.id.currency_name)
        private val rate: AppCompatTextView = itemView.findViewById(R.id.rate)
        override val rootView: View = itemView.findViewById(R.id.root_view)

        override fun bind(item: NbuCurrency) {

            image.setImageDrawable(item.getImage(itemView.context))
            abr.text = item.currencyAbbreviation
            name.text = item.getName(itemView.context)
            rate.text = item.rate.toString()

            setAnimation(item.getAbr(itemView.context))
        }
    }
}