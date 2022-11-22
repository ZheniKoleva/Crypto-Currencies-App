package com.example.examapp.database.entity

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examapp.R
import com.example.examapp.models.CurrencyDetails

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "logo") var logo: String,
    @ColumnInfo(name = "current_price") var currentPrice: Double,
    @ColumnInfo(name = "market_cap") var marketCap: Double,
    @ColumnInfo(name = "high_24h") var high24h: Double,
    @ColumnInfo(name = "low_24h") var low24h: Double,
    @ColumnInfo(name = "price_change_percentage_24h") var priceChangePercentage: Double,
    @ColumnInfo(name = "market_cap_change_percentage_24h") var marketCapChangePercentage: Double,
    @ColumnInfo(name = "favourite") var favourite: Boolean
)

fun Currency.asCurrencyDetails(context: Context): CurrencyDetails {
    return CurrencyDetails(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        logo = this.logo,
        currentPrice = context.getString(R.string.price_formatted, this.currentPrice),
        marketCap = context.getString(R.string.market_cap_formatted, this.marketCap),
        high24h = context.getString(R.string.highest_price_formatted, this.high24h),
        low24h = context.getString(R.string.lowest_price_formatted, this.low24h),
        priceChangePercentage = context.getString(
            R.string.price_change_percentage_formatted,
            this.priceChangePercentage
        ),
        marketCapChangePercentage = context.getString(
            R.string.market_cap_change_percentage_formatted,
            this.marketCapChangePercentage
        ),
        favourite = this.favourite
    )
}