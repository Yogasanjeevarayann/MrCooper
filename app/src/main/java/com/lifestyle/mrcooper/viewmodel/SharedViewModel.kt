package com.lifestyle.mrcooper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifestyle.mrcooper.data.db.PhoneEntity
import com.lifestyle.mrcooper.data.repository.PhoneRepository
import com.lifestyle.mrcooper.data.repository.RepositoryResult
import com.lifestyle.mrcooper.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: PhoneRepository
) : ViewModel() {

    // StateFlow for fetched phone numbers
    private val _phoneNumbers = MutableStateFlow<List<String>>(emptyList())
    val phoneNumbers: StateFlow<List<String>> = _phoneNumbers.asStateFlow()

    // StateFlow for loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow for database phones
    private val _phones = MutableStateFlow<List<PhoneEntity>>(emptyList())
    val phones: StateFlow<List<PhoneEntity>> = _phones.asStateFlow()

    //For longer splash screen time
    private val _isSplashLoading = MutableStateFlow(true)
    val isSplashLoading get() = _isSplashLoading.asStateFlow()


    init {
        viewModelScope.launch {
            _isSplashLoading.value = false
        }
    }


    /**
     * Fetch phone numbers from the API in batches and update StateFlow.
     */
    fun fetchPhoneNumbers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                AppLogger.logDebug("Starting phone number fetch...")

                // Create a list of deferred results for concurrent API calls
                val deferredResults = withContext(Dispatchers.IO) {
                    List(4) { iteration ->
                        async {
                            try {
                                val result = repository.fetchPhoneNumbers()
                                when (result) {
                                    is RepositoryResult.Success -> {
                                        AppLogger.logDebug("Batch $iteration fetched: ${result.data}")
                                        result.data
                                    }

                                    is RepositoryResult.Error -> {
                                        AppLogger.logError("Error fetching batch $iteration", result.exception)
                                        emptyList()
                                    }
                                }
                            } catch (e: Exception) {
                                AppLogger.logError("Error during batch $iteration", e)
                                emptyList()
                            }
                        }
                    }
                }

                // Await all deferred results and flatten the list
                val fetchedNumbers = deferredResults.awaitAll().flatten()

                // Emit the final fetched list
                _phoneNumbers.emit(fetchedNumbers)
                AppLogger.logDebug("Phone numbers fetched successfully: $fetchedNumbers")
            } catch (e: Exception) {
                AppLogger.logError("Error during phone number fetch", e)
                _phoneNumbers.emit(emptyList()) // Emit empty list on failure
            } finally {
                _isLoading.value = false
            }
        }
    }


    /**
     * Save phone numbers to the local database.
     * @param phones List of PhoneEntity to save.
     */
    fun savePhones(phones: List<PhoneEntity>) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.savePhoneNumbers(phones)
                }
                AppLogger.logDebug("Phone numbers saved to database: $phones")
            } catch (e: Exception) {
                AppLogger.logError("Error saving phone numbers", e)
            }
        }
    }




    /**
     * Load phone numbers from the database into StateFlow.
     */
    fun loadPhones() {
        viewModelScope.launch {
            try {
                val savedPhones = withContext(Dispatchers.IO) {
                    repository.getSavedPhoneNumbers()
                }
                _phones.value = savedPhones
                AppLogger.logDebug("Phone numbers loaded from database: $savedPhones")
            } catch (e: Exception) {
                AppLogger.logError("Error loading phone numbers from database", e)
            }
        }
    }

    /**
     * Clear all phone numbers from the database asynchronously.
     * @param onCleared Callback to notify after clearing.
     */
    fun clearDatabase(onCleared: () -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.clearAllPhones() // Ensure this runs on the IO thread
                }
                _phones.emit(emptyList()) // Clear StateFlow to reflect database state
                onCleared()
                AppLogger.logDebug("Database cleared successfully")
            } catch (e: Exception) {
                AppLogger.logError("Error clearing database", e)
            }
        }
    }

    /**
     * Clear all phone numbers from the database asynchronously using runBlocking on background.
     */
    fun clearDatabaseBlocking() {
            try {
                runBlocking {
                    repository.clearAllPhones()
                    AppLogger.logDebug("Database cleared in blocking mode")
                }
            } catch (e: Exception) {
                AppLogger.logError("Error clearing database", e)
            }
    }


}
