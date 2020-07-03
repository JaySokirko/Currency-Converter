package com.jay.currencyconverter.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jay.currencyconverter.repository.database.nbu.NbuDao
import com.jay.currencyconverter.repository.database.nbu.NbuEntity

@Database(entities = [ NbuEntity::class ], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun nbuDao() : NbuDao
}