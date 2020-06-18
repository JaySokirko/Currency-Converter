package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CurrencyView
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization
import com.jay.currencyconverter.ui.adapter.diffUtil.OrganizationDiffUtil
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder
import com.jay.currencyconverter.util.common.Filter
import kotlinx.coroutines.*

class OrganizationExchangeRateAdapter : RecyclerView.Adapter<BaseViewHolder<CommonOrganization>>() {

    val clickEvent: MutableLiveData<CommonOrganization> = MutableLiveData()

    private val initialOrganizationList: MutableList<CommonOrganization> = mutableListOf()
    private val filteredOrganizationList: MutableList<CommonOrganization> = mutableListOf()
    private val adapterJob: CompletableJob = Job()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Main + adapterJob)
    private val diffUtil = OrganizationDiffUtil()
    private val filter: Filter<CommonOrganization> = Filter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CommonOrganization> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_organizations, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CommonOrganization>, position: Int) {
        holder.bind(filteredOrganizationList[position])
    }

    override fun getItemCount(): Int = filteredOrganizationList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(organizations: List<CommonOrganization?>) {
        initialOrganizationList.apply { clear(); addAll(organizations.filterNotNull()) }
        filteredOrganizationList.apply { clear(); addAll(organizations.filterNotNull()) }
        notifyDataSetChanged()
    }

    fun filter(search: String) {
        val filteredResult: MutableList<CommonOrganization> =
            filter.getFilteredResult(search, initialOrganizationList)
            { organization: CommonOrganization ->
                organization.title?.toLowerCase()?.contains(search.toLowerCase()) ?: false
            }
        setFilteredResult(filteredResult)
    }

    private fun setFilteredResult(organizations: List<CommonOrganization?>) {
        diffUtil.setData(oldList = filteredOrganizationList, newList = organizations.filterNotNull())
        filteredOrganizationList.apply { clear(); addAll(organizations.filterNotNull()) }
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder<CommonOrganization>(itemView) {

        private val container: LinearLayoutCompat = itemView.findViewById(R.id.container)
        private val bankTitle: AppCompatTextView = itemView.findViewById(R.id.bank_title)
        private val context: Context = itemView.context

        init {
            itemView.setOnClickListener {
                clickEvent.postValue(filteredOrganizationList[layoutPosition])
            }
        }

        override fun bind(item: CommonOrganization) {
            clearViews()

            bankTitle.text = item.title

            item.currencies?.let { currencies ->

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

        private suspend fun create() {

        }
    }

    fun onDestroy(){
        adapterJob.cancel()
        uiScope.cancel()
    }
}