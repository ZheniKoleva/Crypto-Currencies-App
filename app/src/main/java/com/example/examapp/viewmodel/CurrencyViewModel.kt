package com.example.examapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examapp.database.entity.Currency
import com.example.examapp.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val currenciesListStateFlow = MutableStateFlow<List<Currency>>(arrayListOf())

    private val selectedCurrencyStateFlow = MutableStateFlow<Currency?>(null)

    val currenciesList: StateFlow<List<Currency>> = currenciesListStateFlow.asStateFlow()

    val selectCurrency: StateFlow<Currency?> = selectedCurrencyStateFlow.asStateFlow()

    suspend fun getCurrencies() {
        var currencies = currencyRepository.getCurrencies()
        currenciesListStateFlow.value = currencies
    }

    suspend fun getCurrencyById(id: String) {
        val currency = currencyRepository.getCurrencyById(id)
        selectedCurrencyStateFlow.value = currency ?: return
    }

    suspend fun setAsFavorite(currency: Currency) {
        currencyRepository.setFavorite(currency)
        selectedCurrencyStateFlow.update {
            it?.copy(favourite = currency.favourite)
        }
    }
}