package com.cookandroid.happycup.data.model.request

import java.io.Serializable

data class LoginRequest(
    var id: String,
    var pw: String,
): Serializable {
    constructor(): this("","")
}