package com.example.vndbviewer.data.network.pojo

import com.example.vndbviewer.domain.Vn

class VnMapper {

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
        description = fullInfo.vnAdditionalInfoDbModel.description
    )

    fun mapEntityToBasicDbModelInfo(vn: Vn) = VnBasicInfoDbModel(
        id = vn.id,
        image = vn.image,
        rating = vn.rating,
        votecount = vn.votecount,
        title = vn.title
    )

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
}