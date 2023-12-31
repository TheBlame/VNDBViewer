package com.example.vndbviewer.data.network.pojo.vn

import com.example.vndbviewer.data.network.pojo.Image

data class VnResults(
    val id: String,
    val image: Image,
    val rating: Double,
    val votecount: Int,
    val title: String,
    val description: String,
    val tags: List<Tags>,
    val screenshots: List<Screenshot>
)
