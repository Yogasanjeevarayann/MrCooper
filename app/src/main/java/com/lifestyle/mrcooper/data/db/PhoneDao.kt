package com.lifestyle.mrcooper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(phones: List<PhoneEntity>)

    @Query("SELECT * FROM phone_table")
    suspend fun getAllPhones(): List<PhoneEntity>

    @Query("DELETE FROM phone_table")
    suspend fun clearAll()
}