package com.example.vndbviewer.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.vndb.org/kana/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}