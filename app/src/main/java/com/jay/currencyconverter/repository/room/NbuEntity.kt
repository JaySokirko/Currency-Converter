package com.jay.currencyconverter.repository.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency

@Entity
class NbuEntity(@Embedded var currency: NbuCurrency, var isShouldDisplayed: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id = 0L
}