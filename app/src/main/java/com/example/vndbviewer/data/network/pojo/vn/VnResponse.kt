package com.example.vndbviewer.data.network.pojo.vn

import com.google.gson.annotations.SerializedName

data class VnResponse(
    val more: Boolean,
    @SerializedName("results")
    val vnListResults: List<VnResults>
)