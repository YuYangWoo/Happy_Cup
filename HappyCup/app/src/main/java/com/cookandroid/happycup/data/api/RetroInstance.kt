package com.cookandroid.happycup.data.api

object RetroInstance {
    val baseUrl = "http://15.165.74.74:8080"
    val aiUrl =  "http://39.127.139.44:8000"
    val client = BaseRetro.getClient(baseUrl).create(RetroService::class.java)
    val aiClient = BaseRetro.getClient(aiUrl).create(RetroService::class.java)
}