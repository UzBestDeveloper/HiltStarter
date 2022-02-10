package com.example.hiltstarter.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

private const val TEXT_PLAIN = "text/plain"

fun String.convertRequestBody(): RequestBody {
    return toRequestBody(TEXT_PLAIN.toMediaTypeOrNull())
}