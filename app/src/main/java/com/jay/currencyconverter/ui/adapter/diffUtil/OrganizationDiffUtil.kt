package com.jay.currencyconverter.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization

class OrganizationDiffUtil: DiffUtil.Callback() {

    private val oldList: MutableList<CommonOrganization> = mutableListOf()
    private val newList: MutableList<CommonOrganization> = mutableListOf()

    fun setData(oldList: List<CommonOrganization>, newList: List<CommonOrganization>) {
        this.oldList.apply { clear(); addAll(oldList) }
        this.newList.apply { clear(); addAll(newList) }
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}