package com.jay.currencyconverter.repository.room

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NbuDao {

    @Query("SELECT * FROM NbuEntity")
    fun getAll(): Flowable<List<NbuEntity>>

    @Query("SELECT * FROM NbuEntity")
    fun getAllSingle(): Single<List<NbuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nbu: NbuEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(all: MutableList<NbuEntity>)

    @Query("UPDATE NbuEntity SET isShouldDisplayed = :isShouldDisplayed WHERE abbr =:abbr")
    fun update(abbr: String, isShouldDisplayed: Boolean)

    @Query("UPDATE NbuEntity SET isShouldDisplayed = :isShouldDisplayed")
    fun updateAll(isShouldDisplayed: Boolean)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(nbu: List<NbuEntity>)

    @Delete
    fun delete(nbu: NbuEntity)

    @Delete
    fun deleteAll(nbu: List<NbuEntity>)
}