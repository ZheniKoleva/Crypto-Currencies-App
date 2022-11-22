package com.example.examapp.models

import android.content.Context
import com.example.examapp.database.entity.Currency

data class CurrencyDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val logo: String,
    val currentPrice: String,
    val marketCap: String,
    val high24h: String,
    val low24h: String,
    val priceChangePercentage: String,
    val marketCapChangePercentage: String,
    var favourite: Boolean
)

fun CurrencyDetails.asCurrency(context: Context): Currency {
    return Currency(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        logo = this.logo,
        currentPrice = this.currentPrice.toDouble(),
        marketCap = this.marketCap.toDouble(),
        high24h = this.high24h.toDouble(),
        low24h = this.low24h.toDouble(),
        priceChangePercentage = this.priceChangePercentage.toDouble(),
        marketCapChangePercentage = this.marketCapChangePercentage.toDouble(),
        favourite = this.favourite
    )
}


