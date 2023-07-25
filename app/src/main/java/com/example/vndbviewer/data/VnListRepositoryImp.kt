package com.example.vndbviewer.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.database.AppDatabase
import com.example.vndbviewer.data.network.api.ApiFactory
import com.example.vndbviewer.data.network.pojo.VnMapper
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VnListRepositoryImp(application: Application) : VnListRepository {

    private val db = AppDatabase.getInstance(application)
    private val mapper = VnMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getVnList(): LiveData<List<Vn>> = MediatorLiveData<List<Vn>>().apply {
        loadVnList()
        addSource(db.vnDao().getVnList()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getVnDetails(id: String): Vn {
        loadCertainVnInfo(id)
        delay(2000)
        return mapper.mapFullInfoToEntity(db.vnDao().getVnFullInfo(id))
    }

    override suspend fun addVnList(list: List<Vn>) {
        db.vnDao().insertVnList(mapper.mapListEntityToListDbModel(list))
    }

    override suspend fun updateVnDetails(vn: Vn) {
        db.vnDao().insertVnAdditionalInfo(mapper.mapEntityToAdditionalDbModelInfo(vn))
    }

    private fun loadVnList() {
        coroutineScope.launch {
            try {
                val result: List<Vn> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            page = 1,
                            results = 50,
                            reverse = true,
                            sort = "rating",
                            fields = "title, image.url, rating, votecount"
                        )
                    ).vnListResults
                Log.d("loadVnList", result.toString())
                addVnList(result)
            } catch (e: Exception) {
                e.message?.let { Log.e("loadVnList", it) }
            }
        }
    }

    private fun loadCertainVnInfo(id: String) {
        coroutineScope.launch {
            try {
                val result: List<Vn> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            filters = listOf("id", "=", id),
                            fields = "title, image.url, rating, votecount, description"
                        )
                    ).vnListResults
                updateVnDetails(result.first())
                Log.d("loadCertainVnInfo", result.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("loadCertainVnInfo", it) }
            }
        }
    }
}