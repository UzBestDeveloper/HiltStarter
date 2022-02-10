package com.example.hiltstarter.utils

import androidx.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToBoolean

class NullToBooleanAdapter {
    @ToJson
    fun toJson(@NullToBoolean value: Boolean?): String? {
        return value?.toString()
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(@Nullable data: String?): Boolean {
        return data.toBoolean()
    }
}

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString

class NullToEmptyStringAdapter {
    @ToJson
    fun toJson(@NullToEmptyString value: String?): String? {
        return value
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(@Nullable data: String?): String {
        return data ?: ""
    }
}