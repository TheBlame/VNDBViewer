package com.example.vndbviewer.data.network.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vn_additional_info")
data class VnAdditionalInfoDbModel(
    val description: String,
    @PrimaryKey
    val id: String
)
