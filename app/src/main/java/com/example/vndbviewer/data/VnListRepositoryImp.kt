package com.example.vndbviewer.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.vndbviewer.data.database.AppDatabase
import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel
import com.example.vndbviewer.data.network.api.ApiFactory
import com.example.vndbviewer.data.network.api.ApiService
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.data.network.pojo.VnResults
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VnListRepositoryImp(application: Application) : VnListRepository {

    private val db = AppDatabase.getInstance(application)
    private val mapper = VnMapper()
    private val service = ApiService.create()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getVnList(): LiveData<List<Vn>> = MediatorLiveData<List<Vn>>().apply {
        coroutineScope.launch {
            loadVnList()
            addSource(db.vnDao().getVnList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
    }

    override fun getVnDetails(id: String): LiveData<Vn> = MediatorLiveData<Vn>().apply {
        coroutineScope.launch {
            loadCertainVnInfo(id)
            addSource(db.vnDao().getVnFullInfo(id)) {
                value = mapper.mapFullInfoToEntity(it)
            }
        }
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
                addVnList(mapper.mapListVnResultsToListBasicDbModelInfo(result))
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
            updateVnDetails(mapper.mapVnResultsToAdditionalDbModelInfo(result.first()))
        } catch (e: Exception) {
            e.message?.let { Log.e("loadCertainVnInfo", it) }
        }
    }
}