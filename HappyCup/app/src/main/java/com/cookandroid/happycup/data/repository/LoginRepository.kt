package com.cookandroid.happycup.data.repository

import com.cookandroid.happycup.data.api.RetroInstance
import com.cookandroid.happycup.data.model.request.LoginRequest

class LoginRepository {
    suspend fun login(loginRequest: LoginRequest) = RetroInstance.client.requestLogin(loginRequest)
}