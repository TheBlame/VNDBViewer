package com.example.vndbviewer.data.network.pojo.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
    val more: Boolean,
    @SerializedName("results")
    val characterListResults: List<VnCharacter>
)