package com.example.vndbviewer.data.network.pojo.character

import com.google.gson.annotations.SerializedName

data class Traits(
    val id: String,
    val name: String,
    @SerializedName("group_name")
    val groupName: String,
    val spoiler: Int,
    val lie: Boolean
)
