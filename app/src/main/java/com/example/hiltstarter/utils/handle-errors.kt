package com.example.hiltstarter.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import okio.IOException
import retrofit2.HttpException

fun Throwable.handle(): ErrorData? {
    var errorData: ErrorData? = null
    if (this is HttpException) {
        val body = this.response()?.errorBody()
        val gson = Gson()
        val adapter: TypeAdapter<ErrorData> = gson.getAdapter(ErrorData::class.java)
        try {
            errorData = adapter.fromJson(body?.string())
        } catch (e: IOException) {
            println("error ${this.javaClass.name} -> ${e.message ?: e.localizedMessage}")
        }
    }
    return errorData
}

data class ErrorData(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any
)