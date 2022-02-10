package com.example.hiltstarter.network.api

import com.example.hiltstarter.network.models.login.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("auth/login")
    suspend fun login(
        @Part("phone") phone: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse

}