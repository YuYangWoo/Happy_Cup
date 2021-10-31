package com.cookandroid.happycup.data.repository

import com.cookandroid.happycup.data.api.RetroInstance
import com.cookandroid.happycup.data.model.request.PlasticRequest
import okhttp3.MultipartBody
import java.io.File

class MainRepository {
    suspend fun plastic(plasticRequest: MultipartBody.Part) = RetroInstance.aiClient.requestPlastic(plasticRequest)
}