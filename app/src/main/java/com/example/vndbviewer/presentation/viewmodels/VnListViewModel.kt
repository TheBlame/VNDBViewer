package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.data.network.api.ApiFactory
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.domain.AddVnListUseCase
import com.example.vndbviewer.domain.GetVnListUseCase
import com.example.vndbviewer.domain.Vn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VnListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnListUseCase = GetVnListUseCase(repository)
    private val addVnListUseCase = AddVnListUseCase(repository)

    private val _vnList = MutableLiveData<List<Vn>>()
    val vnList: LiveData<List<Vn>>
        get() = _vnList

    init {
        loadVnList()
    }

    private fun loadVnList() {
        viewModelScope.launch(Dispatchers.IO) {
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
                addVnListUseCase.addVnList(result)
                _vnList.postValue(getVnListUseCase.getVnList())
                Log.d("vnlist", vnList.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("loadVnList", it) }
            }
        }
    }
}