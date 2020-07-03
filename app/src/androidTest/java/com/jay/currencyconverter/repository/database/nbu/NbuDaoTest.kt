package com.jay.currencyconverter.repository.database.nbu

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.repository.database.DataBase
import io.reactivex.android.schedulers.AndroidSchedulers
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test


class NbuDaoTest {

    companion object {
        private const val INITIAL_DATA_BASE_SIZE = 3

        private val entityList: MutableList<NbuEntity> = mutableListOf()

        private val currency1 = NbuCurrency().apply { currencyAbbreviation = "UAH" }
        private val currency2 = NbuCurrency().apply { currencyAbbreviation = "USD" }

        private val currencyList: MutableList<NbuEntity> =
            mutableListOf(NbuEntity(currency1, true), NbuEntity(currency2, true))

        lateinit var nbuDao: NbuDao
        lateinit var db: DataBase

        @BeforeClass
        @JvmStatic
        fun setup() {
            repeat(INITIAL_DATA_BASE_SIZE) { index ->
                entityList.add(index, NbuEntity(NbuCurrency(), true))
            }
        }

        @AfterClass
        @JvmStatic
        fun clear(){
            db.close()
        }
    }

    @Before
    fun beforeEach() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
            DataBase::class.java).allowMainThreadQueries().build()

        nbuDao = db.nbuDao()
        nbuDao.insertAll(entityList)
    }

    @Test
    fun getAll_returnCorrectSize() {
        nbuDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> assertEquals(INITIAL_DATA_BASE_SIZE, result.size) }
    }

    @Test
    fun getAllSingle_returnCorrectSize() {
        nbuDao.getAllSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> assertEquals(INITIAL_DATA_BASE_SIZE, result.size) }
    }

    @Test
    fun getCurrencyByAbbreviation() {
        nbuDao.insert(NbuEntity(currency1, true))

        nbuDao.getCurrencyByAbbreviation(currency1.currencyAbbreviation!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currency: NbuEntity ->
                currency.currency!!.currencyAbbreviation == currency1.currencyAbbreviation
            }
    }

    @Test
    fun insert() {
        nbuDao.insert(NbuEntity(currency1, true))

        nbuDao.getAllSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> assertEquals(INITIAL_DATA_BASE_SIZE + 1, result.size) }
    }

    @Test
    fun insertAll() {
        nbuDao.insertAll(currencyList)

        nbuDao.getAllSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> assertEquals(INITIAL_DATA_BASE_SIZE + 2, result.size) }
    }

    @Test
    fun update() {
        val nbuEntity = NbuEntity(currency1, false)

        nbuDao.insert(nbuEntity)

        nbuDao.update(nbuEntity.currency!!.currencyAbbreviation!!, true)

        nbuDao.getCurrencyByAbbreviation(nbuEntity.currency!!.currencyAbbreviation!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currency: NbuEntity ->
                assertEquals(currency.isShouldDisplayed, true)
            }
    }

    @Test
    fun updateAll() {
        nbuDao.insertAll(listOf(NbuEntity(currency1, false), NbuEntity(currency2, false)))

        nbuDao.updateAll(true)

        nbuDao.getAllSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { updatedList: List<NbuEntity> ->
                updatedList.forEach { entity: NbuEntity ->
                    assertEquals(entity.isShouldDisplayed, true)
                }
            }
    }
}