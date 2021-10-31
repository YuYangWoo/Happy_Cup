package com.cookandroid.happycup.data.api

import com.cookandroid.happycup.data.model.request.LoginRequest
import com.cookandroid.happycup.data.model.request.PlasticRequest
import com.cookandroid.happycup.data.model.response.LoginResponse
import com.cookandroid.happycup.data.model.response.PlasticResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetroService {

    @POST("/login")
    suspend fun requestLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Multipart
    @POST("/plastic")
    suspend fun requestPlastic(@Part file: MultipartBody.Part) : Response<PlasticResponse>
}