package com.example.hiltstarter.ui.fragments.home

import com.example.hiltstarter.network.models.login.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun login(phone: String, password: String): Flow<LoginResponse>

}