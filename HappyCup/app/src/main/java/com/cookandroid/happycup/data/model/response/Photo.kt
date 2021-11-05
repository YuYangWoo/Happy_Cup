package com.cookandroid.happycup.data.model.response

import java.io.Serializable

data class Photo(
    var thumbnail: String?,
    var author: String?,
    var createAt: String?,
    var likeCount: Int?
): Serializable {
}