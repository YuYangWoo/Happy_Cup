package com.cookandroid.happycup.data.api

object RetroInstance {
    val baseUrl = "http://15.165.74.74:8080"
    val client = BaseRetro.getClient(baseUrl).create(RetroService::class.java)
}