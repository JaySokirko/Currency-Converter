package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CurrencyView
import com.jay.currencyconverter.model.currencyExchange.bank.Organization
import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter.ViewHolder

class BankExchangeRateAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val organizationList: MutableList<Organization> = ArrayList()
    val clickEvent: MutableLiveData<Organization> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_bank_exhange_rate, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(organizationList[position])
    }

    override fun getItemCount(): Int {
        return organizationList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setItems(organizations: List<Organization?>) {
        organizationList.clear()
        organizationList.addAll(organizations.filterNotNull())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val container: LinearLayoutCompat = itemView.findViewById(R.id.container)
        private val bankTitle: AppCompatTextView = itemView.findViewById(R.id.bank_title)
        private val context: Context = itemView.context

        init {
            itemView.setOnClickListener {
                clickEvent.postValue(organizationList[layoutPosition])
            }
        }

        fun bind(organization: Organization) {
            clearViews()

            bankTitle.text = organization.title

            organization.currencies?.let { currencies ->

                currencies.getAllNotNullCurrencies().forEach { currency ->

                    val currencyView = CurrencyView(context)
                        currencyView.apply {
                        currency.getName(context)?.let { name -> setTitle(name) }
                        currency.getImage(context)?.let { image -> setImage(image) }
                        currency.bid?.let { bid -> setBid(bid) }
                        currency.ask?.let { ask -> setAsk(ask) }
                    }
                    container.addView(currencyView)
                }
            }
        }

        private fun clearViews() {
            bankTitle.text = null
            container.removeAllViews()
        }
    }
}