package com.example.vndbviewer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

    init {
        loadVnList()
        Log.d("tag", "VIEWMODEL START")
    }

    private fun loadVnList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: List<VnList> =
                    ApiFactory.apiService.getTopList(
                        VnRequest(
                            1,
                            50,
                            true,
                            "rating",
                            "title, image.url, rating"
                        )
                    ).vnList
                Log.d("tag", result.toString())
                db.vnDao().insertVnList(result)

            } catch (e: java.lang.Exception) {
                Log.e("tag", e.message.toString())
            }
        }
    }
}