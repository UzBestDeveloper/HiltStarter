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
    var language by PreferencesDelegate(preferences, LANGUAGE, "uz")
    var loginKey by PreferencesDelegate(preferences, LOGIN_KEY, DEFAULT_STRING)
    var trustedDeviceList by PreferencesDelegate(preferences, TRUSTED_DEVICE_LIST, DEFAULT_STRING)
    var requestID by PreferencesDelegate(preferences, REQUEST_ID, DEFAULT_STRING)
    var auth by PreferencesDelegate(preferences, AUTH, DEFAULT_STRING)
    var unauthorized by PreferencesDelegate(preferences, UNAUTHORIZED, true)

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val LANGUAGE = "language"
        private const val LOGIN_KEY = "login-key"
        private const val TRUSTED_DEVICE_LIST = "trusted-device-list"
        private const val REQUEST_ID = "requestId"
        private const val AUTH = "auth"
        private const val UNAUTHORIZED = "unauthorized"
    }
}