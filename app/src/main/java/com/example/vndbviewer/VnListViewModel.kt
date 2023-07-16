package com.example.vndbviewer

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.database.AppDatabase
import com.example.vndbviewer.network.api.ApiFactory
import com.example.vndbviewer.network.pojo.Vn
import com.example.vndbviewer.network.pojo.VnRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VnListViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    var vnList = db.vnDao().getVnList()

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        Log.d("net info", netInfo.toString())
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    init {
        loadVnList()
        Log.d("tag1", "VIEWMODEL START")
    }

    private fun loadVnList() {
        viewModelScope.launch(Dispatchers.IO) {
            isNetworkAvailable(getApplication())
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
                db.vnDao().insertVnList(result)
            } catch (e: Exception) {
                e.message?.let { Log.e("loadVnList", it) }
            }
        }
    }
}