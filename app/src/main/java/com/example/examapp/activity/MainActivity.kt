package com.example.examapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examapp.R
import com.example.examapp.adapter.CurrencyAdapter
import com.example.examapp.database.CurrencyDatabase
import com.example.examapp.databinding.ActivityMainBinding
import com.example.examapp.factory.CurrencyViewModelFactory
import com.example.examapp.repository.CurrencyRepository
import com.example.examapp.service.CurrencyService
import com.example.examapp.utility.Network
import com.example.examapp.viewmodel.CurrencyViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var currencyService: CurrencyService

    private lateinit var currencyRepository: CurrencyRepository

    lateinit var currencyViewModel: CurrencyViewModel

    lateinit var db: RoomDatabase

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/coins/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
        observeData()

        if (!Network.isInternetAvailable(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            currencyViewModel.getCurrencies()
        }
    }

    private fun init() {
        db = Room.databaseBuilder(
            applicationContext,
            CurrencyDatabase::class.java,
            "currencies_database"
        ).build()

        val currencyDao = (db as CurrencyDatabase).currencyDao()
        currencyService = retrofit.create(CurrencyService::class.java)
        currencyRepository = CurrencyRepository(this, currencyService, currencyDao)
        val currencyViewModelFactory = CurrencyViewModelFactory(currencyRepository)
        currencyViewModel =
            ViewModelProvider(this, currencyViewModelFactory)[CurrencyViewModel::class.java]
    }

    private fun observeData() {
        GlobalScope.launch {
            currencyViewModel.currenciesList.collect {
                runOnUiThread {
                    var currencies = it
                    currencies = currencies.sortedByDescending { it.marketCap }
                    val adapter = CurrencyAdapter(currencies)
                    binding.currenciesList.adapter = adapter
                    binding.tvCurrenciesCount.text =
                        binding.root.context.getString(R.string.currencies_count, it.size)
                }
            }
        }
    }
}