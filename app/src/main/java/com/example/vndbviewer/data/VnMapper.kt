package com.example.vndbviewer.data

import com.example.vndbviewer.data.database.dbmodels.UserDbModel
import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnFullInfoDbModel
import com.example.vndbviewer.data.network.pojo.Authinfo
import com.example.vndbviewer.data.network.pojo.vn.VnResults
import com.example.vndbviewer.domain.ScreenshotList
import com.example.vndbviewer.domain.User
import com.example.vndbviewer.domain.Vn
import javax.inject.Inject

class VnMapper @Inject constructor() {

    fun mapBasicDbModelInfoToEntity(vnBasicInfoDbModel: VnBasicInfoDbModel) = Vn(
        id = vnBasicInfoDbModel.id,
        image = vnBasicInfoDbModel.image,
        rating = vnBasicInfoDbModel.rating,
        votecount = vnBasicInfoDbModel.votecount,
        title = vnBasicInfoDbModel.title,
        description = "",
        tags = listOf(),
        screenshots = listOf()
    )

    fun mapFullInfoToEntity(fullInfo: VnFullInfoDbModel) = Vn(
        id = fullInfo.vnBasicInfoDbModel.id,
        image = fullInfo.vnBasicInfoDbModel.image,
        rating = fullInfo.vnBasicInfoDbModel.rating,
        votecount = fullInfo.vnBasicInfoDbModel.votecount,
        title = fullInfo.vnBasicInfoDbModel.title,
        description = fullInfo.vnAdditionalInfoDbModel.description,
        tags = fullInfo.vnAdditionalInfoDbModel.tags,
        screenshots = fullInfo.vnAdditionalInfoDbModel.screenshots
    )

    fun mapEntityToBasicDbModelInfo(vn: Vn) = VnBasicInfoDbModel(
        id = vn.id,
        image = vn.image,
        rating = vn.rating,
        votecount = vn.votecount,
        title = vn.title
    )

    fun mapFullInfoToBasicDbModelInfo(fullInfo: VnFullInfoDbModel) = VnBasicInfoDbModel(
        id = fullInfo.vnBasicInfoDbModel.id,
        image = fullInfo.vnBasicInfoDbModel.image,
        rating = fullInfo.vnBasicInfoDbModel.rating,
        votecount = fullInfo.vnBasicInfoDbModel.votecount,
        title = fullInfo.vnBasicInfoDbModel.title
    )

    fun mapAuthInfoToUserDbModel(authinfo: Authinfo) = UserDbModel(
        id = authinfo.id,
        username = authinfo.username
    )

    fun mapUserDbModelToUser(userDbModel: UserDbModel?) = if (userDbModel == null) null else User(
        id = userDbModel.id,
        username = userDbModel.username
    )

//    fun mapFullInfoToAdditionalDbModelInfo(fullInfo: VnFullInfo) = fullInfo.vnAdditionalInfoDbModel?.description?.let {
//        VnAdditionalInfoDbModel(
//        id = it,
//        description = fullInfo.vnAdditionalInfoDbModel.description
//    )
//    }

    fun mapListFullInfoToListBasicDbModelInfo(list: List<VnFullInfoDbModel>) = list.map {
        mapFullInfoToBasicDbModelInfo(it)
    }

//    fun mapEntityToAdditionalDbModelInfo(vn: Vn) = VnAdditionalInfoDbModel(
//        id = vn.id,
//        description = vn.description,
//    )

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
        description = vnResults.description,
        tags = vnResults.tags,
        screenshots = buildList {
            vnResults.screenshots.groupBy { it.release.id }.forEach { s, screenshots ->
                add(ScreenshotList(
                    title = screenshots.first().release.title,
                    releaseId = screenshots.first().release.id,
                    screenshotList = buildList {
                        screenshots.forEach {
                            add(Pair(it.thumbnail, it.sexual))
                        }
                    }
                ))
            }
        }
    )
}