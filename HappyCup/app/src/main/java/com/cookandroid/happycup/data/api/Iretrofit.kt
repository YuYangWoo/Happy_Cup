package com.cookandroid.happycup.data.api

import com.google.gson.JsonElement
import com.cookandroid.happycup.util.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit { //retrofit 에 대한 interface

    // https://www.unsplash.com/search/photos/?query=""

    @GET(API.SEARCH_PHOTOS) //사용자가 검색하는 부분
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm: String) : Call<JsonElement>

}