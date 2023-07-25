package com.example.vndbviewer.data.network.pojo

import androidx.room.Embedded
import androidx.room.Relation

data class VnFullInfo(
    @Embedded
    val vnBasicInfoDbModel: VnBasicInfoDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val vnAdditionalInfoDbModel: VnAdditionalInfoDbModel
)
