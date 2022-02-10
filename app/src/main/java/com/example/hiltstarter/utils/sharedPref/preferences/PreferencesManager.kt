package com.example.hiltstarter.utils.sharedPref.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.hiltstarter.utils.Constants.DEFAULT_STRING

class PreferencesManager(
    private val context: Context
) {

    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            context
        )
    }

    var token by PreferencesDelegate(preferences, ACCESS_TOKEN, DEFAULT_STRING)
    var login by PreferencesDelegate(preferences, LOGIN, DEFAULT_STRING)
    var password by PreferencesDelegate(preferences, PASSWORD, DEFAULT_STRING)
    var language by PreferencesDelegate(preferences, LANGUAGE, "uz")
    var fingerPrint by PreferencesDelegate(preferences, FINGERPRINT, false)
    var pinCode by PreferencesDelegate(preferences, PINCODE, DEFAULT_STRING)

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val LANGUAGE = "language"
        private const val FINGERPRINT = "fingerPrint"
        private const val PINCODE = "pinCode"
    }
}