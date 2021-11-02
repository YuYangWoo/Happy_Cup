package com.cookandroid.happycup.data.api

import com.cookandroid.happycup.data.model.request.LoginRequest
import com.cookandroid.happycup.data.model.response.LoginResponse
import com.cookandroid.happycup.data.model.response.DisposableResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetroService {

    @POST("/login")
    suspend fun requestLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/plastic")
    suspend fun requestPlastic(@Body file: RequestBody): Response<DisposableResponse>

    @POST("/paper")
    suspend fun requestPaper(@Body file: RequestBody): Response<DisposableResponse>

    @POST("/paperinside")
    suspend fun requestPaperTwo(@Body file: RequestBody): Response<DisposableResponse>
}