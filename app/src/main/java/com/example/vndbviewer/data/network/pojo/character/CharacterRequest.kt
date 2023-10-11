package com.example.vndbviewer.data.network.pojo.character

data class CharacterRequest(
    val filters: List<Any>,
    val fields: String,
    val results: Int,
    val page: Int
)
