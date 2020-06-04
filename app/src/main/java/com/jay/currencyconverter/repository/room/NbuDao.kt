package com.jay.currencyconverter.repository.room

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NbuDao {

    @Query("SELECT * FROM NbuEntity")
    fun getAll(): Flowable<List<NbuEntity>>

    @Query("SELECT * FROM NbuEntity")
    fun getAllSingle(): Single<List<NbuEntity>>

    @Query("SELECT * FROM NbuEntity WHERE abbreviation = :abbreviation")
    fun getByAbbr(abbreviation: String): Single<NbuEntity?>

    @Query("SELECT * FROM NbuEntity WHERE abbreviation = :abbreviation AND isShouldShown = :isShouldShown")
    fun getByAbbrAndShouldShown(abbreviation: String, isShouldShown: Boolean): Single<NbuEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nbu: NbuEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(all: List<NbuEntity>) : Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(nbu: NbuEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(all: List<NbuEntity>)

    @Delete
    fun delete(nbu: NbuEntity)

    @Delete
    fun deleteAll(nbu: List<NbuEntity>)
}