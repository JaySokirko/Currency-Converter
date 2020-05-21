package com.jay.currencyconverter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.bankActivity.BankActivity
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity
import kotlinx.android.synthetic.main.activity_choice_service.*

class ChoiceServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_service)

        nbu_card_view.setOnClickListener {
            startActivity(Intent(this, NbuActivity::class.java))
        }

        banks_card_view.setOnClickListener {
            startActivity(Intent(this, BankActivity::class.java))
        }
    }
}