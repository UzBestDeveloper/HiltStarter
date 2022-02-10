package com.example.elmakonpos.ui.fragments.auth

import com.example.hiltstarter.network.api.ApiService
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService
) : HomeRepository {

    override fun login(phone: String, password: String) = flow {
        val mPhone = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val mPassword = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val response = api.login(mPhone, mPassword)
        emit(response)
    }

}