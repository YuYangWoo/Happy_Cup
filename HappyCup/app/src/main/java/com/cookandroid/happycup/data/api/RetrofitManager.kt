package com.cookandroid.happycup.data.api

import android.util.Log
import com.google.gson.JsonElement
import com.cookandroid.happycup.data.model.response.Photo
import com.cookandroid.happycup.util.API
import com.cookandroid.happycup.util.Constants.TAG
import com.cookandroid.happycup.util.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATUS, ArrayList<Photo>?) -> Unit){

        val term = searchTerm.let {
            it
        }?: ""

//        val term = searchTerm ?: ""

        val call = iRetrofit?.searchPhotos(searchTerm = term).let {
            it
        }?: return
//        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{

            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(com.cookandroid.happycup.util.RESPONSE_STATUS.FAIL, null)

            }

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                when(response.code()){
                    200 -> {

                        response.body()?.let {

                            var parsedPhotoDataArray = ArrayList<Photo>()

                            val body : JsonObject = it.asJsonObject

                            val results : JsonArray = body.getAsJsonArray("results")

                            val total : Int = body.get("total").asInt

                            Log.d(TAG, "RetrofitManager - onResponse() called / total: $total")

                            // 데이터가 없으면 no_content 로 보낸다.
                            if(total == 0) {
                                completion(com.cookandroid.happycup.util.RESPONSE_STATUS.NO_CONTENT, null)

                            } else { // 데이터가 있다면

                                results.forEach { resultItem ->
                                    val resultItemObject : JsonObject = resultItem.asJsonObject
                                    val user : JsonObject = resultItemObject.get("user").asJsonObject
                                    val username : String = user.get("username").asString
                                    val likesCount : Int = resultItemObject.get("likes").asInt
                                    val thumbnailLink : String = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                    val createdAt : String = resultItemObject.get("created_at").asString
                                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")

                                    val outputDateString = formatter.format(parser.parse(createdAt))

                                    //                                Log.d(TAG, "RetrofitManager - outputDateString : $outputDateString")

                                    val photoItem = Photo(
                                        author = username,
                                        likeCount = likesCount,
                                        thumbnail = thumbnailLink,
                                        createAt = outputDateString // 이 부분에 가격이 들어가야함
                                    )
                                    parsedPhotoDataArray.add(photoItem)

                                }

                                completion(com.cookandroid.happycup.util.RESPONSE_STATUS.OKAY, parsedPhotoDataArray)
                            }
                        }
                    }
                }
            }
        })
    }
}