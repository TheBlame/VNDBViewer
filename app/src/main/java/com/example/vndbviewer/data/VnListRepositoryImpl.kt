package com.example.vndbviewer.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.vndbviewer.data.database.AppDatabase
import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel
import com.example.vndbviewer.data.network.api.ApiService
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.data.network.pojo.VnResults
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VnListRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val service: ApiService,
    private val mapper: VnMapper,
    private val remoteMediator: VnListRemoteMediator
) : VnListRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @OptIn(ExperimentalPagingApi::class)
    override fun getVnList(): Flow<PagingData<Vn>> {
        val pagingSourceFactory = { db.vnDao().getVnList() }
        val flow = Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator
            ,
            pagingSourceFactory = pagingSourceFactory
        ).flow
        Log.d("Singleton test", db.toString() )
        Log.d("Singleton test", service.toString())
        return flow.map { pagingData ->
            pagingData.map { mapper.mapBasicDbModelInfoToEntity(it) }
        }
    }

    override fun getVnDetails(id: String): Flow<Vn> = flow {
        loadCertainVnInfo(id)
        Log.d("Singleton test", db.toString() )
        Log.d("Singleton test", service.toString())
        emit(mapper.mapFullInfoToEntity(db.vnDao().getVnFullInfo(id)))
    }

    private suspend fun addVnList(list: List<VnBasicInfoDbModel>) {
        db.vnDao().insertVnList(list)
    }

    private suspend fun updateVnDetails(vn: VnAdditionalInfoDbModel) {
        db.vnDao().insertVnAdditionalInfo(vn)
    }

    private suspend fun loadVnList() {
        try {
            val result: List<VnResults> =
                service.postToVnEndpoint(
                    VnRequest(
                        page = 1,
                        results = 100,
                        reverse = true,
                        sort = "rating",
                        fields = "title, image.url, rating, votecount"
                    )
                ).vnListResults
            addVnList(mapper.mapListVnResponseToListBasicDbModelInfo(result))
        } catch (e: Exception) {
            e.message?.let { Log.e("loadVnList", it) }
        }
    }

    private suspend fun loadCertainVnInfo(id: String) {
        try {
            val result: List<VnResults> =
                service.postToVnEndpoint(
                    VnRequest(
                        filters = listOf("id", "=", id),
                        fields = "title, image.url, rating, votecount, description"
                    )
                ).vnListResults
            updateVnDetails(mapper.mapVnResponseToAdditionalDbModelInfo(result.first()))
        } catch (e: Exception) {
            e.message?.let { Log.e("loadCertainVnInfo", it) }
        }
    }
}