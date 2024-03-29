package com.example.hiltstarter.network.interceptors

import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

class ReceivedCookiesInterceptor(private val prefs: PreferencesManager) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        updateCookies(originalResponse.headers,prefs)
        return originalResponse
    }

    private fun updateCookies(headers: Headers, prefs: PreferencesManager) {

        try {

            //Saving login-key
            if (headers.find { (_, value) -> value.contains("login-key") } != null) {
                val loginKey = headers.find { (_, value) -> value.contains("login-key") }!!.second
                prefs.loginKey = loginKey.substring(0, loginKey.indexOf(";"))
            }


            //Saving auth token
            if (headers.find { (_, value) -> value.contains("auth-token-sportmy=ey") } != null) {
                val auth = headers.find { (_, value) -> value.contains("auth-token-sportmy=ey") }!!.second
                prefs.auth = auth.substring(0, auth.indexOf(";"))
            }


            //saving trusted device
            if (headers.find { (_, value) -> value.contains("trusted-device") } != null) {
                val trustedDevice = headers.find { (_, value) -> value.contains("trusted-device") }!!.second

                val json = prefs.trustedDeviceList
                val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
                val gson = Gson()
                val trustedDeviceList = mutableListOf<String>()

                if (json.isNotEmpty()) {
                    @Suppress("UNCHECKED_CAST")
                    trustedDeviceList.addAll(gson.fromJson<Any>(json, type) as ArrayList<String>)
                }

                trustedDeviceList.add(trustedDevice.substring(0, trustedDevice.indexOf(";")))
                val save: String = gson.toJson(trustedDeviceList)
                prefs.trustedDeviceList = save
            }


            //Saving requestId
            if (headers.find { (_, value) -> value.contains("requestId") } != null) {
                val requestId = headers.find { (_, value) -> value.contains("requestId") }!!.second
                prefs.requestID = requestId.substring(0, requestId.indexOf(";"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}