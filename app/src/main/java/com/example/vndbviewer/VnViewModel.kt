package com.example.vndbviewer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.database.AppDatabase
import com.example.vndbviewer.network.api.ApiFactory
import com.example.vndbviewer.network.pojo.VnList
import com.example.vndbviewer.network.pojo.VnRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VnViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    var vnList = db.vnDao().getVnList()

    fun getVnDetails(id: String): LiveData<VnList> {
        loadCertainVnInfo(id)
        return db.vnDao().getVnDetails(id)
    }

    init {
        loadVnList()
        Log.d("tag1", "VIEWMODEL START")
    }

    private fun loadVnList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: List<VnList> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            page = 1,
                            results = 50,
                            reverse = true,
                            sort = "rating",
                            fields = "title, image.url, rating"
                        )
                    ).vnList
                Log.d("loadVnList", result.toString())
                db.vnDao().insertVnList(result)
            } catch (e: Exception) {
                e.message?.let { Log.e("loadVnList", it) }
            }
        }
    }

    private fun loadCertainVnInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: List<VnList> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            filters = listOf("id", "=", id),
                            fields = "title, image.url, rating, description"
                        )
                    ).vnList
                Log.d("loadCertainVnInfo", result.toString())
                db.vnDao().insertVnList(result)
            } catch (e: Exception) {
                e.message?.let { Log.e("loadCertainVnInfo", it) }
            }
        }
    }
}