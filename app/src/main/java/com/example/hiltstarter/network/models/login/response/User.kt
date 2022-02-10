package com.example.hiltstarter.network.models.login.response


import androidx.annotation.Keep
import com.example.hiltstarter.network.models.login.response.Role
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "birthday")
    val birthday: String?,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "deleted_at")
    val deletedAt: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "middle_name")
    val middleName: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "phone_confirmed")
    val phoneConfirmed: Boolean,
    @Json(name = "phone_confirmed_at")
    val phoneConfirmedAt: String,
    @Json(name = "roles")
    val roles: List<Role>,
    @Json(name = "updated_at")
    val updatedAt: String
)