package com.example.vndbviewer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.database.AppDatabase
import com.example.vndbviewer.network.api.ApiFactory
import com.example.vndbviewer.network.pojo.Vn
import com.example.vndbviewer.network.pojo.VnRequest
import kotlinx.coroutines.launch

class VnItemViewModel(application: Application): AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

//    init {
//        TODO("load vn info")
//    }

    fun getVnDetails(id: String): LiveData<Vn> {
        loadCertainVnInfo(id)
        return db.vnDao().getVnDetails(id)
    }

    private fun loadCertainVnInfo(id: String) {
        viewModelScope.launch {
            try {
                val result: List<Vn> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            filters = listOf("id", "=", id),
                            fields = "title, image.url, rating, votecount, description"
                        )
                    ).vnListResults
                db.vnDao().updateVn(result)
                Log.d("loadCertainVnInfo", result.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("loadCertainVnInfo", it) }
            }
        }
    }
}