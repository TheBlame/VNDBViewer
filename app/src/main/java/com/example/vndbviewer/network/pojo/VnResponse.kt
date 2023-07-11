package com.example.vndbviewer.network.pojo

import com.google.gson.annotations.SerializedName

data class VnResponse(
    val more: Boolean,
    @SerializedName("results")
    val vnList: List<VnList>
)