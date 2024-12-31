package com.lifestyle.mrcooper.di

import android.content.Context
import androidx.room.Room
import com.lifestyle.mrcooper.data.db.AppDatabase
import com.lifestyle.mrcooper.data.db.PhoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun providePhoneDao(database: AppDatabase): PhoneDao = database.phoneDao()
}