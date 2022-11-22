package com.example.examapp.models

import com.example.examapp.database.entity.Currency

data class CurrencyNetworkResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val market_cap: Double,
    val high_24h: Double,
    val low_24h: Double,
    val price_change_percentage_24h: Double,
    val market_cap_change_percentage_24h: Double,
)

fun CurrencyNetworkResponse.asCurrencyDatabase(): Currency {
    return Currency(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        logo = this.image,
        currentPrice = this.current_price,
        marketCap = this.market_cap,
        high24h = this.high_24h,
        low24h = this.low_24h,
        priceChangePercentage = this.price_change_percentage_24h,
        marketCapChangePercentage = this.market_cap_change_percentage_24h,
        favourite = false
    )
}
