package com.cookandroid.happycup.data.api

object RetroInstance {
    val baseUrl = "http://172.16.17.49:8080"
    val client = BaseRetro.getClient(baseUrl).create(RetroService::class.java)
}