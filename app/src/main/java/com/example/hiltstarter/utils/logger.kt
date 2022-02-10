package com.example.hiltstarter.utils

import android.util.Log
import com.example.hiltstarter.BuildConfig

fun <T> T.debug(message: String) {
    if (BuildConfig.DEBUG)
        Log.d(DEBUG + (this ?: return)::class.java.simpleName, message)
}

fun <T> T.info(message: String) {
    if (BuildConfig.DEBUG)
        Log.i(INFO + (this ?: return)::class.java.simpleName, message)
}

fun Throwable.exception(message: String = "") {
    if (BuildConfig.DEBUG)
        Log.e(EXCEPTION + this::class.java.simpleName, message, this)
}

private const val DEBUG = "DEBUG"
private const val INFO = "INFO"
private const val EXCEPTION = "EXCEPTION"