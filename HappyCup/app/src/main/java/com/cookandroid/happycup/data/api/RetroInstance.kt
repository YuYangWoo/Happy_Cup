package com.cookandroid.happycup.data.api

object RetroInstance {
    val baseUrl = "http://192.168.106.166:8080"
    val client = BaseRetro.getClient(baseUrl).create(RetroService::class.java)
}