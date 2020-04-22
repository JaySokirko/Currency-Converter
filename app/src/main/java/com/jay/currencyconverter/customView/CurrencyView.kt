package com.jay.currencyconverter.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.jay.currencyconverter.R

class CurrencyView : LinearLayoutCompat {

    private lateinit var rootLayout: ViewGroup
    private lateinit var currencyTitle: AppCompatTextView
    private lateinit var currencyImage: AppCompatImageView
    private lateinit var bid: AppCompatTextView
    private lateinit var ask: AppCompatTextView

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    fun setTitle(text: String) {
        currencyTitle.text = text
    }

    fun setImage(image: Drawable) {
        currencyImage.setImageDrawable(image)
    }

    fun setBid(text: String) {
        bid.text = text
    }

    fun setAsk(text: String) {
        ask.text = text
    }

    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        rootLayout = inflater.inflate(R.layout.view_currency_layout, this, false) as ViewGroup
        addView(rootLayout)

        currencyTitle = rootLayout.findViewById(R.id.currency_title)
        currencyImage = rootLayout.findViewById(R.id.currency_image)
        bid = rootLayout.findViewById(R.id.bid)
        ask = rootLayout.findViewById(R.id.ask)
    }
}