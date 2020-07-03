package com.jay.currencyconverter.repository.database.nbu

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NbuDao {

    @Query("SELECT * FROM NbuEntity")
    fun getAll(): Flowable<List<NbuEntity>>

    @Query("SELECT * FROM NbuEntity")
    fun getAllSingle(): Single<List<NbuEntity>>

    @Query("SELECT * FROM NbuEntity WHERE abbr = :abbr")
    fun getCurrencyByAbbreviation(abbr: String): Single<NbuEntity>

    @Query("SELECT * FROM NbuEntity WHERE abbr IN (:abbr)")
    fun getCurrenciesByAbbreviationList(abbr: List<String>): Single<List<NbuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nbu: NbuEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(all: List<NbuEntity>)

    @Query("UPDATE NbuEntity SET isShouldDisplayed = :isShouldDisplayed WHERE abbr =:abbr")
    fun update(abbr: String, isShouldDisplayed: Boolean)

    @Query("UPDATE NbuEntity SET isShouldDisplayed = :isShouldDisplayed")
    fun updateAll(isShouldDisplayed: Boolean)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(nbu: List<NbuEntity>)
}