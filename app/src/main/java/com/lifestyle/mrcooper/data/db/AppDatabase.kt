package com.lifestyle.mrcooper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhoneEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun phoneDao(): PhoneDao
}