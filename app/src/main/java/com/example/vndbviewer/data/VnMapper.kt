package com.example.vndbviewer.data

import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnFullInfo
import com.example.vndbviewer.data.network.pojo.VnResults
import com.example.vndbviewer.domain.Vn
import javax.inject.Inject

class VnMapper @Inject constructor() {

    fun mapBasicDbModelInfoToEntity(vnBasicInfoDbModel: VnBasicInfoDbModel) = Vn(
        id = vnBasicInfoDbModel.id,
        image = vnBasicInfoDbModel.image,
        rating = vnBasicInfoDbModel.rating,
        votecount = vnBasicInfoDbModel.votecount,
        title = vnBasicInfoDbModel.title,
        description = ""
    )

    fun mapFullInfoToEntity(fullInfo: VnFullInfo) = Vn(
        id = fullInfo.vnBasicInfoDbModel.id,
        image = fullInfo.vnBasicInfoDbModel.image,
        rating = fullInfo.vnBasicInfoDbModel.rating,
        votecount = fullInfo.vnBasicInfoDbModel.votecount,
        title = fullInfo.vnBasicInfoDbModel.title,
        description = fullInfo.vnAdditionalInfoDbModel?.description
    )

    fun mapEntityToBasicDbModelInfo(vn: Vn) = VnBasicInfoDbModel(
        id = vn.id,
        image = vn.image,
        rating = vn.rating,
        votecount = vn.votecount,
        title = vn.title
    )

    fun mapFullInfoToBasicDbModelInfo(fullInfo: VnFullInfo) = VnBasicInfoDbModel(
        id = fullInfo.vnBasicInfoDbModel.id,
        image = fullInfo.vnBasicInfoDbModel.image,
        rating = fullInfo.vnBasicInfoDbModel.rating,
        votecount = fullInfo.vnBasicInfoDbModel.votecount,
        title = fullInfo.vnBasicInfoDbModel.title
    )

    fun mapFullInfoToAdditionalDbModelInfo(fullInfo: VnFullInfo) = fullInfo.vnAdditionalInfoDbModel?.description?.let {
        VnAdditionalInfoDbModel(
        id = it,
        description = fullInfo.vnAdditionalInfoDbModel.description
    )
    }

    fun mapListFullInfoToListBasicDbModelInfo(list: List<VnFullInfo>) = list.map {
        mapFullInfoToBasicDbModelInfo(it)
    }

    fun mapEntityToAdditionalDbModelInfo(vn: Vn) = VnAdditionalInfoDbModel(
        id = vn.id,
        description = vn.description
    )

    fun mapListDbModelToListEntity(list: List<VnBasicInfoDbModel>) = list.map {
        mapBasicDbModelInfoToEntity(it)
    }

    fun mapListEntityToListDbModel(list: List<Vn>) = list.map {
        mapEntityToBasicDbModelInfo(it)
    }

    fun mapVnResponseToBasicDbModelInfo(vnResults: VnResults) = VnBasicInfoDbModel(
        id = vnResults.id,
        image = vnResults.image.url,
        rating = vnResults.rating,
        votecount = vnResults.votecount,
        title = vnResults.title
    )

    fun mapListVnResponseToListBasicDbModelInfo(list: List<VnResults>) = list.map {
        mapVnResponseToBasicDbModelInfo(it)
    }

    fun mapVnResponseToAdditionalDbModelInfo(vnResults: VnResults) = VnAdditionalInfoDbModel(
        id = vnResults.id,
        description = vnResults.description
    )
}