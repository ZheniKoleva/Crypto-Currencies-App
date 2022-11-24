package com.example.examapp.database.dao

import androidx.room.*
import com.example.examapp.database.entity.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM Currencies ORDER By favourite Desc, market_cap Desc")
    suspend fun getAllCurrencies(): List<Currency>

    @Query("SELECT * FROM Currencies WHERE id=:id")
    suspend fun getCurrencyById(id: String): Currency

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Currency>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(currency: Currency)
}