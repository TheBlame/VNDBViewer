package com.example.vndbviewer.data.network.pojo.vn

data class VnRequest(
    val page: Int? = null,
    val results: Int? = null,
    val reverse: Boolean? = null,
    val sort: String? = null,
    val fields: String? = null,
    var filters: List<String>? = null
)