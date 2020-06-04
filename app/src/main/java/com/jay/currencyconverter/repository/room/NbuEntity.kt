package com.jay.currencyconverter.repository.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu

@Entity
class NbuEntity(@Embedded var currency: Nbu, var isShouldDisplayed: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id = 0L
}