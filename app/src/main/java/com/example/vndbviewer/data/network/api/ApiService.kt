package com.example.vndbviewer.data.network.api

import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.data.network.pojo.VnResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("vn")
    suspend fun postToVnEndpoint(@Body body: VnRequest): VnResponse

}