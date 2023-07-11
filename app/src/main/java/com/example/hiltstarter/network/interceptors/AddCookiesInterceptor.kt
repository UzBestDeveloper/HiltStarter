package com.example.hiltstarter.network.interceptors


import com.example.hiltstarter.utils.getLanguage
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor(private val prefs: PreferencesManager) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

       //adding language to every request
        val original = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("lang", getLanguage(prefs.language))
            .build()

        builder.url(url)

        //saving cookies
        if (prefs.loginKey.isNotEmpty()) {
            builder.addHeader("cookie", prefs.loginKey)
        }
        if (prefs.trustedDevice.isNotEmpty()) {
            builder.addHeader("cookie", prefs.trustedDevice)
        }
        if (prefs.requestID.isNotEmpty()) {
            builder.addHeader("cookie", prefs.requestID)
        }
        if (prefs.auth.isNotEmpty()) {
            builder.addHeader("cookie", prefs.auth)
        }
        return chain.proceed(builder.build())
    }
}