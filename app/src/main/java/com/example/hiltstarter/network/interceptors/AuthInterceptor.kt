package com.example.hiltstarter.network.interceptors

import android.content.Context
import android.content.Intent
import com.example.hiltstarter.ui.activities.MainActivity
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val context: Context,
    private val prefs: PreferencesManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldResponse = chain.proceed(oldRequest)
        if (oldResponse.code == 401) {
            prefs.unauthorized = true
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
        return oldResponse
    }
}