package com.example.examapp.repository

import android.content.Context
import com.example.examapp.database.dao.CurrencyDao
import com.example.examapp.database.entity.Currency
import com.example.examapp.models.asCurrencyDatabase
import com.example.examapp.service.CurrencyService
import com.example.examapp.utility.Network


class CurrencyRepository constructor(
    private val context: Context,
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getCurrencies(): List<Currency> {
        return try {
            if (Network.isInternetAvailable(context)) {
                val currencies = currencyService.getCurrencies().execute().body()
                val mappedCurrencies = currencies?.map { it.asCurrencyDatabase() }
                currencyDao.insertCurrencies(mappedCurrencies ?: return arrayListOf())
            }

            currencyDao.getAllCurrencies()
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun getCurrencyById(id: String): Currency? {
        return try {
            currencyDao.getCurrencyById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setFavorite(currency: Currency) {
        currencyDao.update(currency)
    }

}