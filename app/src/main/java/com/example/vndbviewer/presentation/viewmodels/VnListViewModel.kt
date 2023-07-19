package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.data.network.api.ApiFactory
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.AddVnListUseCase
import com.example.vndbviewer.domain.usecases.GetVnListUseCase
import kotlinx.coroutines.launch

class VnListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnListUseCase = GetVnListUseCase(repository)
    private val addVnListUseCase = AddVnListUseCase(repository)

    val vnList = getVnListUseCase()

    init {
        loadVnList()
    }

    private fun loadVnList() {
        viewModelScope.launch {
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
                addVnListUseCase(result)
                Log.d("vnlist", vnList.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("loadVnList", it) }
            }
        }
    }

}