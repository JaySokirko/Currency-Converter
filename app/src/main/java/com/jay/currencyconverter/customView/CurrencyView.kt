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

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private lateinit var rootLayout: ViewGroup
    private lateinit var title: AppCompatTextView
    private lateinit var image: AppCompatImageView
    private lateinit var bid: AppCompatTextView
    private lateinit var ask: AppCompatTextView

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setImage(image: Drawable) {
        this.image.setImageDrawable(image)
    }

    fun setBid(text: String) {
        bid.text = text
    }

    fun setAsk(text: String) {
        ask.text = text
    }

    private fun init() {
        rootLayout = inflater.inflate(R.layout.view_currency_layout, this, false) as ViewGroup
        addView(rootLayout)

        title = rootLayout.findViewById(R.id.currency_title)
        image = rootLayout.findViewById(R.id.currency_image)
        bid = rootLayout.findViewById(R.id.bid)
        ask = rootLayout.findViewById(R.id.ask)
    }
}