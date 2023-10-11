package com.example.vndbviewer.data.network.pojo.character

import com.example.vndbviewer.data.network.pojo.Image
import com.google.gson.annotations.SerializedName

data class VnCharacter(
    val id: String,
    val name: String,
    val original: String? = null,
    val aliases: List<String> = listOf(),
    val description: String? = null,
    val image: Image? = null,
    @SerializedName("blood_type")
    val bloodType: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val bust: Int? = null,
    val waist: Int? = null,
    val hips: Int? = null,
    val cup: String? = null,
    val age: Int? = null,
    val birthday: List<Int>? = null,
    val sex: List<String>? = null,
    val vns: List<Vns>,
    val traits: List<Traits> = listOf()
)
