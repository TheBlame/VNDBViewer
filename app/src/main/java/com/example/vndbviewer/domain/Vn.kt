package com.example.vndbviewer.domain

import com.example.vndbviewer.data.network.pojo.Tags

data class Vn(
    val id: String,
    val image: String,
    val rating: Double,
    val votecount: Int,
    val title: String,
    val description: String?,
    val tags: List<Tags>?
)