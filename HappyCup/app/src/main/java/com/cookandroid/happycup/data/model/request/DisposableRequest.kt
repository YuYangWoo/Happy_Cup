package com.cookandroid.happycup.data.model.request

import okhttp3.MultipartBody
import java.io.File

data class DisposableRequest(val file: MultipartBody.Part)