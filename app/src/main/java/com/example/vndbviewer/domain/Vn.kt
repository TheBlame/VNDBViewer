package com.example.vndbviewer.domain

data class Vn(
    val id: String,
    val image: String,
    val rating: Double,
    val votecount: Int,
    val title: String,
    val description: String?
)