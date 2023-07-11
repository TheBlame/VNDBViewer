package com.example.vndbviewer.network.api

import com.example.vndbviewer.network.pojo.VnRequest
import com.example.vndbviewer.network.pojo.VnResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("vn")
    suspend fun getTopList(@Body body: VnRequest): VnResponse
}