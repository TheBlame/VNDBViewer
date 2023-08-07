package com.example.vndbviewer.data.network.pojo

import com.google.gson.annotations.SerializedName

data class Tags(
    val rating: Double,
    val category: String,
    val name: String,
    val id: String,
    val spoiler: Int
)