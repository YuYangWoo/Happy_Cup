package com.cookandroid.happycup.util

object Constants {
    const val TAG : String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT
}


object API {
    const val BASE_URL : String = "https://api.unsplash.com/"

    // 개인 Access Key
    const val CLIENT_ID : String = "AXJZRQRMczXxLnYoDevpQ1mNphLkcQ3-CO4Z2li6fSs"

    const val SEARCH_PHOTOS : String = "search/photos"
    const val SEARCH_USERS : String = "search/users"

}