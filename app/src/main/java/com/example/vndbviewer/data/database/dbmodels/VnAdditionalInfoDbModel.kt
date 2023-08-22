package com.example.vndbviewer.data.database.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vndbviewer.data.network.pojo.Tags
import com.example.vndbviewer.domain.ScreenshotList

@Entity(tableName = "vn_additional_info")
data class VnAdditionalInfoDbModel(
    val description: String?,
    val tags: List<Tags>,
    val screenshots: List<ScreenshotList>,
    @PrimaryKey
    val id: String
)
