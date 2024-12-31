package com.lifestyle.mrcooper.util

import android.util.Log
import com.lifestyle.mrcooper.BuildConfig

object AppLogger {

    // Common tag used for all log messages
    private const val TAG = AppConstants.LOG_TAG

    fun logDebug(message: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, message)
    }

    fun logError(message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.e(TAG, message, throwable)
            } else {
                Log.e(TAG, message)
            }
        }
    }

    fun logInfo(message: String) {
        if (BuildConfig.DEBUG) Log.i(TAG, message)
    }


    fun logWarn(message: String) {
        if (BuildConfig.DEBUG) Log.w(TAG, message)
    }
}
