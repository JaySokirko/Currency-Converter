package com.jay.currencyconverter.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ NbuEntity::class ], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun nbuDao() : NbuDao
}