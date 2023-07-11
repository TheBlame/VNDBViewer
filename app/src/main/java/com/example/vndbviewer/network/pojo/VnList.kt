package com.example.vndbviewer.network.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vn")
data class VnList(
    @PrimaryKey
    val id: String,
    @Embedded
    val image: Image,
    val rating: Double,
    val title: String
)