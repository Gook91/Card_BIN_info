package com.example.cardbininfo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Класс базы данных приложения
@Database(entities = [HistoryBin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyBinDao(): HistoryBinDao

    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            db ?: createAndSetDB(context).also { db = it }

        private fun createAndSetDB(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}