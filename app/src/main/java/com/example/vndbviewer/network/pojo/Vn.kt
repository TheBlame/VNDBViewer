package com.example.vndbviewer.network.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vn")
data class Vn(
    @PrimaryKey
    val id: String,
    @Embedded
    val image: Image? = null,
    val rating: Double? = null,
    val votecount: Int? = null,
    val title: String? = null,
    val description: String? = null
)