package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.ui.adapter.BankExchangeAdapter.BankViewHolder
import com.jay.currencyconverter.ui.view.CurrencyView
import java.util.*

class BankExchangeAdapter(private val context: Context) : RecyclerView.Adapter<BankViewHolder>() {

    private val organizationList: MutableList<Organization> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_bank_list, parent, false)

        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
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

    fun setItems(organizations: Collection<Organization>?) {
        organizationList.addAll(organizations!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        organizationList.clear()
        notifyDataSetChanged()
    }

    inner class BankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val container: LinearLayoutCompat = itemView.findViewById(R.id.container)
        private val bankTitle: AppCompatTextView = itemView.findViewById(R.id.bank_title)

        fun bind(organization: Organization) {
            clearViews()
            bankTitle.text = organization.title

            for (currency in organization.currencies!!.allAvailableCurrencies) {

                if (currency != null) {
                    val currencyView = CurrencyView(context)
                    currencyView.setTitle(currency.getName(context)!!)
                    currencyView.setImage(currency.getImage(context)!!)
                    currencyView.setBid(currency.bid!!)
                    currencyView.setAsk(currency.ask!!)
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