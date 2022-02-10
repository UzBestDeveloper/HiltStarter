package com.example.elmakonpos.ui.fragments.auth

import com.example.hiltstarter.network.models.login.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun login(phone: String, password: String): Flow<LoginResponse>


}