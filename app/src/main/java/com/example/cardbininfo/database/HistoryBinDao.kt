package com.example.cardbininfo.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Интерфейс доступа к таблице с историей поиска
@Dao
interface HistoryBinDao {
    @Query("SELECT * FROM historyBin")
    fun getAllStrings(): Flow<List<HistoryBin>>

    @Insert
    suspend fun insert(historyBin: HistoryBin)

    @Delete
    suspend fun delete(historyBin: HistoryBin)
}
