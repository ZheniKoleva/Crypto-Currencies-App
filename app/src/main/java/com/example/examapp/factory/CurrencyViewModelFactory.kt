package com.example.examapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.examapp.repository.CurrencyRepository
import com.example.examapp.viewmodel.CurrencyViewModel

class CurrencyViewModelFactory(
    private val currencyRepository: CurrencyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyRepository) as T
    }
}