package com.example.hiltstarter.ui.fragments.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class OAuthManager(private val context: Context) {

    companion object {
        private const val CLIENT_ID = "ihma_ins_web"
        private const val REDIRECT_URI = "com.example.hiltstarter://callback"
        private const val AUTH_URL = "https://tauth.ihmatest.uz/connect/authorize"
        private const val TOKEN_URL = "https://tauth.ihmatest.uz/connect/token"
    }

    private val client = OkHttpClient()
    private val gson = Gson()

    fun initiateOAuth() {
        val authUri = Uri.parse(AUTH_URL)
            .buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("response_type", "code")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, authUri)
        context.startActivity(intent)
    }

    fun handleAuthorizationCallback(callbackUri: Uri?, callback: AuthorizationCallback) {
        val code = callbackUri?.getQueryParameter("code")
        if (!code.isNullOrBlank()) {
            exchangeCodeForToken(code, callback)
        } else {
            callback.onAuthorizationFailed()
        }
    }

    private fun exchangeCodeForToken(code: String, callback: AuthorizationCallback) {
        val requestBody = FormBody.Builder()
            .add("code", code)
            .add("client_id", CLIENT_ID)
            .add("redirect_uri", REDIRECT_URI)
            .add("grant_type", "authorization_code")
            .build()

        val request = Request.Builder()
            .url(TOKEN_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body.string()
                    val accessToken = gson.fromJson(responseBody, AccessToken::class.java)
                    callback.onAuthorizationSuccess(accessToken)
                } else {
                    callback.onAuthorizationFailed()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onAuthorizationFailed()
            }
        })
    }

    // Define AccessToken class to parse the server response
    data class AccessToken(
        val token: String
    )

    interface AuthorizationCallback {
        fun onAuthorizationSuccess(accessToken: AccessToken)
        fun onAuthorizationFailed()
    }
}
