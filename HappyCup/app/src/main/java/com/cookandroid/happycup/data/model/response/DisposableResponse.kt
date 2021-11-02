package com.cookandroid.happycup.data.model.response

import java.io.Serializable

data class DisposableResponse(
    var class_name: String,
    var communication_success: Boolean
) : Serializable {
    constructor() : this("", false)
}