package com.lifestyle.mrcooper.di

import com.lifestyle.mrcooper.data.api.ApiClient
import com.lifestyle.mrcooper.data.api.ApiService
import com.lifestyle.mrcooper.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = AppConstants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = ApiClient.createOkHttpClient()

    @Provides
    @Singleton
    fun provideApiService(
        baseUrl: String,
        client: OkHttpClient
    ): ApiService = ApiClient.create(baseUrl, client)
}
