package com.cookandroid.gachon_study_room.data.api

import com.cookandroid.happycup.data.api.BaseRetro
import com.cookandroid.happycup.data.api.RetroService

object RetroInstance {
    val baseUrl = "http://10.0.2.2:8080"
    val client = BaseRetro.getClient(baseUrl).create(RetroService::class.java)
}