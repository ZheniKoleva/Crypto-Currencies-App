package com.example.examapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examapp.database.dao.CurrencyDao
import com.example.examapp.database.entity.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}