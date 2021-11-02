package com.cookandroid.happycup.data.repository

import com.cookandroid.happycup.data.api.RetroInstance
import okhttp3.RequestBody

class MainRepository {
    suspend fun analsisPlastic(plasticRequest: RequestBody) = RetroInstance.aiClient.requestPlastic(plasticRequest)
    suspend fun analsisPaper(disposableRequest: RequestBody) = RetroInstance.aiClient.requestPlastic(disposableRequest)
    suspend fun analsisPaperTwo(disposableRequest: RequestBody) = RetroInstance.aiClient.requestPlastic(disposableRequest)
}