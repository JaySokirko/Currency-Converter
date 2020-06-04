package com.jay.currencyconverter.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NbuEntity {

    @PrimaryKey(autoGenerate = true)
    var id = 0L

    @ColumnInfo(name = "abbreviation")
    var abbreviation: String? = null

    var name: String? = null
    var rate = 0.0
    var isShouldShown = true

    constructor(abbreviation: String?, name: String?, rate: Double, isShouldShown: Boolean) {
        this.abbreviation = abbreviation
        this.name = name
        this.rate = rate
        this.isShouldShown = isShouldShown
    }
}