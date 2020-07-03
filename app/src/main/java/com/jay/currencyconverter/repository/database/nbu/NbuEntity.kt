package com.jay.currencyconverter.repository.database.nbu

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency

@Entity
data class NbuEntity(@Embedded var currency: NbuCurrency? = null, var isShouldDisplayed: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id = 0L
}