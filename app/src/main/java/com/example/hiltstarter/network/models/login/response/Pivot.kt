package com.example.hiltstarter.network.models.login.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Pivot(
    @Json(name = "model_id")
    val modelId: Int,
    @Json(name = "model_type")
    val modelType: String,
    @Json(name = "role_id")
    val roleId: Int
)