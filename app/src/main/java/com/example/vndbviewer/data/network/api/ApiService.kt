package com.example.vndbviewer.data.network.api

import com.example.vndbviewer.data.network.pojo.Authinfo
import com.example.vndbviewer.data.network.pojo.vn.VnRequest
import com.example.vndbviewer.data.network.pojo.vn.VnResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("vn")
    suspend fun postToVnEndpoint(@Body body: VnRequest): VnResponse

    @GET("authinfo")
    suspend fun getUserInfo(@Header("Authorization") token: String): Authinfo

    companion object {

        private const val BASE_URL = "https://api.vndb.org/kana/"

        const val AUTH_TOKEN = "token"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}