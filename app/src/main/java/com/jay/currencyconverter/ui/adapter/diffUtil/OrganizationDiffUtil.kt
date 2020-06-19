package com.jay.currencyconverter.ui.adapter.diffUtil

import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization

class OrganizationDiffUtil: BaseDiffUtil<CommonOrganization>() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}