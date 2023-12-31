package com.example.vndbviewer.data.database.dbmodels

import androidx.room.Embedded
import androidx.room.Relation

data class VnFullInfoDbModel(
    @Embedded
    val vnBasicInfoDbModel: VnBasicInfoDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val vnAdditionalInfoDbModel: VnAdditionalInfoDbModel
)
