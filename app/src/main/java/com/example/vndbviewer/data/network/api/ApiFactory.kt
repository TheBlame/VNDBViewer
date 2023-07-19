package com.example.vndbviewer.data.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val BASE_URL = "https://api.vndb.org/kana/"

    private val interceptor = HttpLoggingInterceptor();

    private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
    .connectTimeout(2, TimeUnit.MINUTES)
    .writeTimeout(2, TimeUnit.MINUTES)
    .readTimeout(2, TimeUnit.MINUTES)
    .build();

    private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build();

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}