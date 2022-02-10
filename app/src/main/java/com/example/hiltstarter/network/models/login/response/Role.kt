package com.example.hiltstarter.network.models.login.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import com.example.hiltstarter.network.models.login.response.Pivot

@Keep
@JsonClass(generateAdapter = true)
data class Role(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "guard_name")
    val guardName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "pivot")
    val pivot: Pivot,
    @Json(name = "updated_at")
    val updatedAt: String
)