package com.example.vndbviewer.data.network.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vn_basic_info")
data class VnBasicInfoDbModel(
    @Embedded
    val image: Image,
    val rating: Double,
    val votecount: Int,
    val title: String,
    @PrimaryKey
    val id: String
)

