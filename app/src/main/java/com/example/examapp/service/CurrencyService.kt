package com.example.examapp.service

import com.example.examapp.models.CurrencyNetworkResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("markets?vs_currency=usd")
    fun getCurrencies(): Call<List<CurrencyNetworkResponse>>

}