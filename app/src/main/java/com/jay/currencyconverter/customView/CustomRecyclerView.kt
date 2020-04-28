package com.jay.currencyconverter.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.util.ViewState

class CustomRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, attrs = null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, defStyleAttr = 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
    }

    fun getAllParentView(): MutableList<View> {
        val parentViewList: MutableList<View> = mutableListOf()
        for (i: Int in 0 until childCount) {
            val view: View = getChildAt(i)
            parentViewList.add(view)
        }
        return parentViewList
    }

    fun displayItemAtPositionByState(list: MutableList<View>, position: Int, state: ViewState.Visibility) {
        makeAllItemsVisible(list)

        when (state) {
            ViewState.Visibility.INVISIBLE -> {
                showItemAtPosition(list, position)
            }
            ViewState.Visibility.VISIBLE -> {
                hideItemAtPosition(list, position)
            }
        }
    }

    private fun makeAllItemsVisible(list: MutableList<View>) {
        list.forEach { view ->
            view.animate().alpha(0f).setDuration(500).start()
        }
    }

    private fun hideItemAtPosition(list: MutableList<View>, position: Int) {
        list[position].animate().alpha(0f).setDuration(500).start()
    }

    private fun showItemAtPosition(list: MutableList<View>, position: Int) {
        list[position].animate().alpha(1f).setDuration(500).start()
    }

}