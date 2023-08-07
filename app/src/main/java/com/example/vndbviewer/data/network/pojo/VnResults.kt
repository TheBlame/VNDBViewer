package com.example.vndbviewer.data.network.pojo

import com.google.gson.annotations.SerializedName

data class VnResults(
    val id: String,
    val image: Image,
    val rating: Double,
    val votecount: Int,
    val title: String,
    val description: String,
    val tags: List<Tags>
)
