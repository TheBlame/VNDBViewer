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
import com.example.vndbviewer.domain.GetVnDetailsUseCase
import com.example.vndbviewer.domain.UpdateVnDetailsUseCase
import com.example.vndbviewer.domain.Vn
import kotlinx.coroutines.launch

class VnItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnDetailsUSeCase = GetVnDetailsUseCase(repository)
    private val updateVnDetailsUseCase = UpdateVnDetailsUseCase(repository)

    private val _vnDetails = MutableLiveData<Vn>()
    val vnDetails: LiveData<Vn>
        get() = _vnDetails

    fun loadCertainVnInfo(id: String) {
        viewModelScope.launch {
            try {
                val result: List<Vn> =
                    ApiFactory.apiService.postToVnEndpoint(
                        VnRequest(
                            filters = listOf("id", "=", id),
                            fields = "title, image.url, rating, votecount, description"
                        )
                    ).vnListResults
                updateVnDetailsUseCase.updateVnDetails(result)
                _vnDetails.postValue(getVnDetailsUSeCase.getVnDetails(id))
                Log.d("loadCertainVnInfo", result.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("loadCertainVnInfo", it) }
            }
        }
    }
}