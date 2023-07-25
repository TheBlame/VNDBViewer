package com.example.vndbviewer.data.network.pojo

import com.google.gson.annotations.SerializedName

data class VnResponse(
    val more: Boolean,
    @SerializedName("results")
    val vnListResults: List<VnResults>
)