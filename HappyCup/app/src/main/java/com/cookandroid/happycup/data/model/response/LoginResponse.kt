package com.cookandroid.happycup.data.model.response

import java.io.Serializable

data class LoginResponse(
    var success: Boolean,
    var name: String,
    var point: Int
): Serializable {
    constructor() : this(false, "", 0)
}