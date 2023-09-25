package com.example.hiltstarter.network.interceptors


import com.example.hiltstarter.utils.getLanguage
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

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
        if (prefs.requestID.isNotEmpty()) {
            builder.addHeader("cookie", prefs.requestID)
        }
        if (prefs.auth.isNotEmpty()) {
            builder.addHeader("cookie", prefs.auth)
        }
        if (prefs.trustedDeviceList.isNotEmpty()) {
            val json = prefs.trustedDeviceList
            val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
            val gson = Gson()
            val list = gson.fromJson<Any>(json, type) as ArrayList<*>
            list.forEach { item ->
                builder.addHeader("cookie", item as String)
            }
        }
        return chain.proceed(builder.build())
    }
}