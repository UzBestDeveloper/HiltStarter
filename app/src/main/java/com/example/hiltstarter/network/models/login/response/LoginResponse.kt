package com.example.hiltstarter.network.models.login.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import com.example.hiltstarter.network.models.login.response.Data

@Keep
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "message")
    val message: String
)