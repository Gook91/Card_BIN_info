package com.example.cardbininfo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Класс хранящегося в таблице запроса Bin
@Entity(tableName = "historyBin")
data class HistoryBin(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "number")
    val number: String
)