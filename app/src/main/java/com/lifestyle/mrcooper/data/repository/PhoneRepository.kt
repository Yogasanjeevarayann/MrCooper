package com.lifestyle.mrcooper.data.repository

import com.lifestyle.mrcooper.data.api.ApiService
import com.lifestyle.mrcooper.data.db.PhoneDao
import com.lifestyle.mrcooper.data.db.PhoneEntity
import javax.inject.Inject


sealed class RepositoryResult<out T> {
    data class Success<T>(val data: T) : RepositoryResult<T>()
    data class Error(val exception: Exception) : RepositoryResult<Nothing>()
}


class PhoneRepository @Inject constructor(
    private val apiService: ApiService,
    private val phoneDao: PhoneDao
) {
    // Fetch phone numbers from the API
    suspend fun fetchPhoneNumbers(): RepositoryResult<List<String>> {
        return try {
            val phoneNumbers = apiService.getPhoneNumbers()
            RepositoryResult.Success(phoneNumbers)
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    // Save phone numbers to the local database
    suspend fun savePhoneNumbers(phones: List<PhoneEntity>) {
        phoneDao.insertAll(phones)
    }

    // Get phone numbers from the local database as a Flow
    suspend fun getSavedPhoneNumbers(): List<PhoneEntity> {
        return phoneDao.getAllPhones()
    }

    // Clear all phone numbers from the local database
    suspend fun clearAllPhones() {
        phoneDao.clearAll()
    }
}
