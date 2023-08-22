package com.example.vndbviewer.domain

import com.example.vndbviewer.data.network.pojo.Tags

data class Vn(
    val id: String = "",
    val image: String = "",
    val rating: Double = 0.0,
    val votecount: Int = 0,
    val title: String = "",
    val description: String? = "",
    val tags: List<Tags> = listOf(),
    val screenshots: List<ScreenshotList> = listOf()
)