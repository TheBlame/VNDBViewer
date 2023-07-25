package com.example.vndbviewer.data.network.api

import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.data.network.pojo.VnResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("vn")
    suspend fun postToVnEndpoint(@Body body: VnRequest): VnResponse

    companion object {

        private const val BASE_URL = "https://api.vndb.org/kana/"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

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